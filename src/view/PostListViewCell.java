package view;

import controller.LoginController;
import controller.MainMenuController;
import controller.moreDetailsController;
import controller.replyController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.MainGUI;
import model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PostListViewCell extends ListCell<Post>  {
@FXML private Label id,idValue,title,titleValue,description,descriptionValue, creatorId,creatorIdValue,status,statusValue,extra1,extra1Value,extra2,extra2Value,extra3,extra3Value,extra4,extra4Value;
@FXML private ImageView imageView;
@FXML private Button moreDetails,reply;
private FXMLLoader mLLoader;
private BufferedImage bufferedImage;
private Image image;
private String postId;

@FXML private AnchorPane anchorPane;
    static Alert error = new Alert(Alert.AlertType.ERROR);

    public PostListViewCell(){super();};

    /**
     * this method is used to update the list view cell
     * @param post
     * @param empty
     */

    @Override
    public void updateItem(Post post, boolean empty) {
        super.updateItem(post, empty);
        if (empty || post == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/view/ListCell.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //setting the list cell values dynamically
            postId = post.getId();
            idValue.setText(post.getId());
            titleValue.setText(post.getTitle());
            descriptionValue.setText(post.getDescription());
            statusValue.setText(post.getStatus());
            creatorIdValue.setText(post.getCreator_id());

            if (post instanceof Event) {
                //setting background color to event list cell ,image and labels
                anchorPane.setBackground(new Background(new BackgroundFill(Color.web("#7ee8f8"), CornerRadii.EMPTY, Insets.EMPTY)));
                extra1.setText("Venue:");
                extra1Value.setText(((Event) post).getVenue());
                extra2.setText("Date:");
                extra2Value.setText(((Event) post).getDate());
                extra3.setText("Capacity:");
                extra3Value.setText(String.valueOf(((Event) post).getCapacity()));
                extra4.setText("Attendee Count:");
                extra4Value.setText(String.valueOf(((Event) post).getAttendeeCount()));
                try {
                    bufferedImage = ImageIO.read(new File(((Event) post).getPhoto()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);

            } else if (post instanceof Sale) {
                //setting background color to sale list cell ,image and labels
                anchorPane.setBackground(new Background(new BackgroundFill(Color.web("#f8b1f4"), CornerRadii.EMPTY, Insets.EMPTY)));
                extra1.setText("Highest Offer:");
                extra1Value.setText(String.valueOf(((Sale) post).getHighestOffer()));
                extra2.setText("Minimum Raise:");
                extra2Value.setText(String.valueOf(((Sale) post).getMinRaise()));
                if(post.getCreator_id().compareToIgnoreCase(LoginController.username)==0){
                    extra3.setText("Asking Price:");
                    extra3Value.setText(String.valueOf(((Sale) post).getAskingPrice()));
                }
                else{
                    extra3.setText("");
                    extra3Value.setText("");
                }


                extra4.setText("");
                extra4Value.setText("");

                try {
                    bufferedImage = ImageIO.read(new File(((Sale) post).getPhoto()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
            } else {
                //setting background color to job list cell ,image and labels
                anchorPane.setBackground(new Background(new BackgroundFill(Color.web("#f7ff91"), CornerRadii.EMPTY, Insets.EMPTY)));
                extra1.setText("Proposed Price:");
                extra1Value.setText(String.valueOf(((Job) post).getProposedPrice()));
                extra2.setText("Lowest Offer:");
                extra2Value.setText(String.valueOf(((Job) post).getLowestOffer()));

                extra3.setText("");
                extra3Value.setText("");

                extra4.setText("");
                extra4Value.setText("");
                try {
                    bufferedImage = ImageIO.read(new File(((Job) post).getPhoto()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
            }

/**
 * This block of code allows you to jump to more details of the post
 */
            moreDetails.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {

                        //check if the username is creator, if yes then navigate to more details, else not allowed
                        if (post.getCreator_id().compareTo(LoginController.username) != 0) {
                            error.setTitle("Error");
                            error.setHeaderText("You cannot check for more details as you are not the creator of the post!");
                            error.showAndWait();
                        } else {
                            moreDetails.getScene().getWindow().hide();

                            FXMLLoader moreDetailsLoader = new FXMLLoader();
                            moreDetailsLoader.setLocation(getClass().getResource("/view/MoreDetails.fxml"));
                            Parent moreDetails = moreDetailsLoader.load();

                            Scene moreDetailsWindowScene = new Scene(moreDetails);
                            moreDetailsController moreDetailsController = moreDetailsLoader.getController();
                            moreDetailsController.initialize(post);

                            Stage window = new Stage();
                            window.setScene(moreDetailsWindowScene);

                            window.show();
                        }
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            });
/**
 * This block of code allows users to reply to the post
 */

            reply.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        error.setContentText("");
                        //check if the post is closed , if yes then cannot reply, else navigate to reply window
                        try{
                            if(post.getStatus().compareToIgnoreCase("closed")==0){
                                throw new PostClosedException("You cannot reply as the post is closed!");
                            }
                        }catch (PostClosedException e){
                           // error.setTitle("Error");
                            error.setContentText(e.getMessage());
                            error.showAndWait();
                        }
                        boolean errorFound = false;
                        if(error.getContentText().isEmpty()) {

                            for (Reply r : MainGUI.replies) {
                                if (r.getPostid().compareToIgnoreCase(post.getId()) == 0) {
                                    if (r.getResponderId().compareToIgnoreCase(LoginController.username) == 0) {
                                        try {
                                            throw new AlreadyRespondedException("You have already responded to this post");
                                        } catch (AlreadyRespondedException e) {
                                            errorFound = true;
                                           // error.setTitle("Error");
                                            error.setContentText(e.getMessage());
                                            error.showAndWait();
                                        }
                                    }
                                }
                            }
                        }
                        System.out.println("Error found: "+errorFound);
                        System.out.println("Error heder: "+error.getHeaderText());

                            if(!errorFound && error.getContentText().isEmpty()) {
                                //check if the username is creator, if yes then cannot reply to own post, else navigate to reply window
                                if (post.getCreator_id().compareTo(LoginController.username) == 0) {
                                    error.setTitle("Error");
                                    error.setContentText("You cannot reply to your own post!");
                                    error.showAndWait();
                                } else {
                                    reply.getScene().getWindow().hide();
                                    FXMLLoader replyLoader = new FXMLLoader();
                                    replyLoader.setLocation(getClass().getResource("/view/Reply.fxml"));
                                    Parent moreDetails = replyLoader.load();

                                    Scene moreDetailsWindowScene = new Scene(moreDetails);
                                    replyController replyController = replyLoader.getController();
                                    replyController.initialize(post);

                                    Stage window = new Stage();
                                    window.setScene(moreDetailsWindowScene);

                                    window.show();
                                }
                            }

                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }
            });
            //adding anchorpane to the scrollpane in mainwindow
            setGraphic(anchorPane);

        }


    }




}
