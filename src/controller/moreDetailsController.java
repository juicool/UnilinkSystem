package controller;

import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.MainGUI;
import model.*;
import view.PostListViewCell;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class moreDetailsController implements Serializable {

    @FXML private BufferedImage bufferedImage;
    @FXML private ImageView imageView;
    @FXML private Image image;
    @FXML private Button backWindowButton,uploadButton,close,save,delete;
    @FXML private Label statusValue,extra1,extra2,extra3,idValue,creatorIdValue,lowestOfferValue,errorLabel,highestOfferValue,attendeeListLabel,attendeeListValue;
    @FXML private TextField titleTextField,extra1Value,extra2Value;
    @FXML private TextArea descriptionTextArea;
    @FXML private DatePicker extra3Value;
    @FXML private TableView<Reply> tableview;
    @FXML private TableColumn<Reply, String> responderIdColumn ;
    @FXML private TableColumn<Reply, Float> responseValueColumn ;

    private Date formattedDate;
    String title,description,status,venue,date;
    int capacity;
    float proposedPrice,lowestOffer, askingPrice,minimumRaise;
    private BufferedImage newBufferedImage;
    private Image newImage;
    private FileChooser fileChooser;
    private File filepath;
    String username;
    String eventResponderID="";
    Reply temp;

    /**
     * This method is used dynamically set the labels and textfields depending on the post type
     * @param post
     */
    @FXML
    public void initialize(Post post){

    //getting the common details of the post

        idValue.setText(post.getId());

        creatorIdValue.setText(post.getCreator_id());
        username = post.getCreator_id();

        titleTextField.setText(post.getTitle());
        descriptionTextArea.setText(post.getDescription());
        statusValue.setText(post.getStatus());


//check if the post is of type Event and setting the labels, textfields, image, attendee list and date picker
        if(post instanceof Event){
            tableview.setVisible(false);
            attendeeListLabel.setText("Attendee List:");
            for (Reply r: getReplies(post)){

                eventResponderID +=r.getResponderId()+ ",";
                attendeeListValue.setText(eventResponderID);}
            extra1.setText("Venue:");
            extra1Value.setText(((Event) post).getVenue());
            extra2.setText("Capacity:");
            extra2Value.setText(String.valueOf(((Event)post).getCapacity()));
            extra3.setText("Date:");
            extra3Value.setValue(LocalDate.parse(((Event) post).getDate()));

            try {
                bufferedImage= ImageIO.read(new File(((Event)post).getPhoto()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            image = SwingFXUtils.toFXImage(bufferedImage,null);
            imageView.setImage(image);
        }
        //check if the post is of type Sale and setting the labels, textfields, image and table rows
        // containing replies of offer prices
        else if(post instanceof Sale){
            attendeeListLabel.setText("");
            attendeeListValue.setText("");
            tableview.setVisible(true);
            responderIdColumn.setCellValueFactory(new PropertyValueFactory<Reply, String>("responderId"));
            responseValueColumn.setCellValueFactory(new PropertyValueFactory<Reply, Float>("responseDoubleValue"));

            for (int x = 0; x < getReplies(post).size(); x++) // bubble sort (descending order) outer loop
            {

                for (int i = 0; i < getReplies(post).size() - x - 1; i++) {
                    if (getReplies(post).get(i).getResponseValue() < (getReplies(post).get(i+1).getResponseValue())) {
                        temp = getReplies(post).get(i);
                        getReplies(post).set(i, getReplies(post).get(i + 1));
                        getReplies(post).set(i + 1, temp);
                    }
                }
            }

            tableview.setItems(getReplies(post));

            extra1.setText("Asking Price:");
            extra1Value.setText(String.valueOf(((Sale) post).getAskingPrice()));
            extra2.setText("Minimum Raise:");
            extra2Value.setText(String.valueOf(((Sale) post).getMinRaise()));
            extra3.setText("Highest Offer:");
            highestOfferValue.setText(String.valueOf(((Sale) post).getHighestOffer()));
            extra3Value.setVisible(false);

            try {
                bufferedImage= ImageIO.read(new File(((Sale)post).getPhoto()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            image = SwingFXUtils.toFXImage(bufferedImage,null);
            imageView.setImage(image);
        }
        //check if the post is of type Sale and setting the labels, textfields, image and table rows
        // containing replies of offer prices
        else{
            attendeeListLabel.setText("");
            attendeeListValue.setText("");
            tableview.setVisible(true);
            responderIdColumn.setCellValueFactory(new PropertyValueFactory<Reply, String>("responderId"));
            responseValueColumn.setCellValueFactory(new PropertyValueFactory<Reply, Float>("responseDoubleValue"));

            for (int x = 0; x < getReplies(post).size(); x++) // bubble sort (ascending order) outer loop
            {

                for (int i = 0; i < getReplies(post).size() - x - 1; i++) {
                    if (getReplies(post).get(i).getResponseValue() > (getReplies(post).get(i+1).getResponseValue())) {
                        temp = getReplies(post).get(i);
                        getReplies(post).set(i, getReplies(post).get(i + 1));
                        getReplies(post).set(i + 1, temp);
                    }
                }
            }
            tableview.setItems(getReplies(post));
            extra1.setText("Proposed Price:");
            extra1Value.setText(String.valueOf(((Job) post).getProposedPrice()));
            extra2.setText("Lowest Offer:");
            lowestOfferValue.setText(String.valueOf(((Job) post).getLowestOffer()));
            extra2Value.setVisible(false);
            extra3.setText("");
            extra3Value.setVisible(false);
            try {
                bufferedImage= ImageIO.read(new File(((Job)post).getPhoto()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            image = SwingFXUtils.toFXImage(bufferedImage,null);
            imageView.setImage(image);
        }

        /**
         * this action function is used to close the status of selected post
         */
        close.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                statusValue.setText("Closed");
                post.setStatus("Closed");
                newPostController.information.setTitle("Status set to closed.");
                newPostController.information.setHeaderText("Status of the post is set to close.");
                newPostController.information.showAndWait();
            }
        });

        /**
         * this action function is used to delete the selected post and its replies
         */
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newPostController.confirm.setTitle("Confirm delete");
                newPostController.confirm.setHeaderText("Are you sure you want to delete?");
                Optional<ButtonType> confirm = newPostController.confirm.showAndWait();
                if (confirm.get() == ButtonType.OK) {
                    MainGUI.collectPosts.remove(post);
                    for (int i = 0; i < MainGUI.replies.size(); i++) {
                        if (post.getId().compareToIgnoreCase(MainGUI.replies.get(i).getPostid()) == 0)
                            MainGUI.replies.remove(i);
                    }

                newPostController.information.setTitle("Post deleted");
                newPostController.information.setHeaderText("Post is deleted");
                newPostController.information.showAndWait();

            }}

        });

        save.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                errorLabel.setText("");
                //getting previously enterred values
               title = titleTextField.getText();
               description = descriptionTextArea.getText().replaceAll("\n",System.getProperty("line.separator"));
               status = statusValue.getText();

               //if there are replies associated to that post then the creator cannot edit details of that post.
               for(Reply r: MainGUI.replies) {
                   if (post.getId().compareToIgnoreCase(r.getPostid())==0)
                   {
                       errorLabel.setText("There are replies associated to this post, therefore post cannot be edited!");
                   }
               }
                //check if the post is of type Event to dynamically load certain labels or textfields
               if(post instanceof Event){
                   filepath = new File(((Event) post).getPhoto());
                   venue = extra1Value.getText();
                    //check if the date falls after today and if no date is selected
                   try {
                       date = extra3Value.valueProperty().get().toString();
                       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                       formattedDate = sdf.parse(date);



                   } catch (Exception e) {
                       errorLabel.setText("Date cannot be empty.");

                   }
                   if (formattedDate.before(new Date())) {
                       errorLabel.setText("Date should fall after today.");

                   } else {
                       //check if the value enterred is non-negative
                       if (extra2Value.getText().matches("^-\\d+$")) {
                           errorLabel.setText("Capacity cannot be less than 0.");

                       } else {
                           //check if the value enterred is int
                           try {
                               capacity = Integer.parseInt(extra2Value.getText());
                           } catch (NumberFormatException e) {
                               errorLabel.setText("Capacity cannot be string.");

                           }
                           if (errorLabel.getText().isEmpty()) {
                               //find the post with the id and remove it
                               for (Post p : MainGUI.collectPosts) {
                                   if (p.getId().compareTo(idValue.getText()) == 0) {
                                       MainGUI.collectPosts.remove(p);
                                       break;
                                   }
                               }
                               //add post with same id but different values
                               MainGUI.collectPosts.add(new Event(idValue.getText(), title, description, creatorIdValue.getText(), venue, date, capacity, "images/" + filepath.getName()));
                               newPostController.information.setTitle("Changes saved!");
                               newPostController.information.setHeaderText("Changes have been saved!");
                               newPostController.information.showAndWait();

                           }
                       }
                   }

               }
               //check if the post is of type Sale to dynamically load certain labels or textfields
               else if(post instanceof Sale){
                   filepath = new File(((Sale) post).getPhoto());
                   //check if the value enterred is non-negative
                   if (extra1Value.getText().matches("^-\\d+$")) {
                        errorLabel.setText("Asking price cannot be less than 0.");
                   }
                   else {
                       //check if the value enterred is float
                       try {
                           askingPrice = Float.parseFloat(extra1Value.getText());
                       }catch (NumberFormatException e){
                           errorLabel.setText("Asking price cannot be string.");
                       }
                   }
                   //check if the value enterred is non-negative
                   if (extra2Value.getText().matches("^-\\d+$")) {
                        errorLabel.setText("Minimum raise cannot be less than 0.");
                   }
                   else {
                       //check if the value enterred is float
                       try {
                           minimumRaise = Float.parseFloat(extra2Value.getText());
                       }catch (NumberFormatException e){
                           errorLabel.setText("Minimum raise cannot be string.");
                       }

                       if (errorLabel.getText().isEmpty()) {
                           if(minimumRaise>askingPrice){
                               errorLabel.setText("Minimum raise should be less than asking price.");
                       }
                           else {
                                //find the post with the id and remove it
                               for (Post p : MainGUI.collectPosts) {
                                   if (p.getId().compareTo(idValue.getText()) == 0) {
                                       MainGUI.collectPosts.remove(p);
                                       break;
                                   }
                               }
                               //add post with same id but different values
                               MainGUI.collectPosts.add(new Sale(idValue.getText(), title, description, creatorIdValue.getText(), askingPrice, minimumRaise, "images/" + filepath.getName()));
                               newPostController.information.setTitle("Changes saved!");
                               newPostController.information.setHeaderText("Changes have been saved!");
                               newPostController.information.showAndWait();

                           }
                       }
                   }
               }
               else{
                   //check if the post is of type Job to dynamically load certain labels or textfields
                   lowestOffer = Float.parseFloat(lowestOfferValue.getText());
                   filepath = new File(((Job) post).getPhoto());
                   //check if the value enterred is non-negative
                   if (extra1Value.getText().matches("^-\\d+$")) {
                        errorLabel.setText("Proposed price cannot be less than 0.");
                   }
                   else {
                       //check if the value enterred is float
                       try {
                           proposedPrice = Float.parseFloat(extra1Value.getText());
                       }catch (NumberFormatException e){
                           errorLabel.setText("Proposed price cannot be string.");
                       }
                       if(errorLabel.getText().isEmpty()) {
                           //find the post with the id and remove it
                           for(Post p: MainGUI.collectPosts){
                               if(p.getId().compareTo(idValue.getText())==0){
                                   MainGUI.collectPosts.remove(p);
                                   break;
                               }
                           }
                           //add post with same id but different values
                           MainGUI.collectPosts.add(new Job(idValue.getText(),title, description,creatorIdValue.getText(),proposedPrice,"images/"+filepath.getName()));
                           newPostController.information.setTitle("Changes saved!");
                           newPostController.information.setHeaderText("Changes have been saved!");
                           newPostController.information.showAndWait();

                       }
                   }
               }

            }
        });
    }

    /**
     * This method is used to update the replies displayed in table view
     * @param post
     * @return
     */
    private ObservableList<Reply> getReplies(Post post) {
        ArrayList<Reply> newReplies = new ArrayList<>();
        for(Reply r: MainGUI.replies){
            if(post.getId().compareToIgnoreCase(r.getPostid())==0){
                newReplies.add(r);
            }
        }
        //making the replies as observable arraylist
        ObservableList<Reply> replies= FXCollections.observableArrayList(newReplies);

        return replies;

    }

    /**
     * This method is used to go back to main window after the user pushes the back button
     * @throws IOException
     */

    @FXML
    public void onBackButtonPushed() throws IOException {
        backWindowButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/main_menu_view.fxml"));
        Parent welcomeView = loader.load();

        Scene mainWindowScene = new Scene(welcomeView);
        MainMenuController controller = loader.getController();
        controller.initialize(username);

        Stage window = new Stage();
        window.setResizable(false);
        window.setScene(mainWindowScene);
        window.show();
    }

    /**
     * This method is used when the user wants to select a different image than the previous one in more details window
     * @param actionEvent
     */

    public void onUploadImagePushed(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        fileChooser=new FileChooser();
        fileChooser.setTitle("Open Image");


        String userDirectoryString = System.getProperty("user.home")+"\\Pictures";
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fileChooser.setInitialDirectory(userDirectory);

        this.filepath = fileChooser.showOpenDialog(stage);

        try{
             newBufferedImage= ImageIO.read(filepath);
            newImage = SwingFXUtils.toFXImage(newBufferedImage,null);
            imageView.setImage(newImage);
            ImageIO.write(SwingFXUtils.fromFXImage(newImage, newBufferedImage),"png" ,new File("C:/Users/juilee/IdeaProjects/MyFirstProject/images/"+filepath.getName()));


        }catch(IOException e){
            System.err.println(e.getMessage());
        }

    }


}
