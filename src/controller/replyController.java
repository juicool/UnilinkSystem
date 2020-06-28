package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainGUI;
import model.*;

import java.io.IOException;

public class replyController {
    @FXML private Label replyLabel,errorLabel;
    @FXML private TextField replyTextField;
    @FXML private Button backWindowButton,replyButton;
    String username;
    int attendance;
    float offerPrice,paymentOffer;

    /**
     * This method dynamically sets the reply label for the required post type
     * @param post
     */
    @FXML
    public void initialize(Post post){
        username = post.getCreator_id();
        if(post instanceof Event){
                replyLabel.setText("Attend (1/0):");
        }
        else{
            replyLabel.setText("Offer price:");
        }

        /**
         * This block of code validates the enterred replies
         */
        replyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                errorLabel.setText("");
                    //check if the value enterred is non-negative
                    if (replyTextField.getText().matches("^-\\d+$")) {
                        errorLabel.setText("Reply cannot be negative.");

                    } else {
                        if (post instanceof Event) {
                            //check if the value enterred is integer
                            try {
                                attendance = Integer.parseInt(replyTextField.getText());
                            } catch (NumberFormatException e) {
                                errorLabel.setText("Invalid input, only specify 1 or 0.");

                            }
                            //check if the reply is either 1 or 0
                            if (errorLabel.getText().isEmpty()) {
                                if(attendance==0||attendance==1) {
                                    MainGUI.replies.add(new Reply(post.getId(), attendance, LoginController.username));
                                    //increment attendee count and setting it
                                    ((Event) post).attendeeCount++;
                                    ((Event) post).setAttendeeCount(((Event) post).attendeeCount);

                                    //check if the capacity is reached, if yes status is set to closed
                                    if((((Event) post).getCapacity())==(((Event) post).getAttendeeCount())){
                                        post.setStatus("Closed");
                                    }
                                    newPostController.information.setTitle("Changes saved!");
                                    newPostController.information.setHeaderText("Changes have been saved!");
                                    newPostController.information.showAndWait();

                                }
                                else {
                                    newPostController.error.setTitle("Changes cannot be saved!");
                                    newPostController.error.setHeaderText("Invalid reply!");
                                    newPostController.error.showAndWait();
                                }
                            }
                        } else if (post instanceof Sale) {
                            try {
                                //check if the value enterred is float
                                offerPrice = Float.parseFloat(replyTextField.getText());
                            } catch (NumberFormatException e) {
                                errorLabel.setText("Offer price cannot be string.");
                            }

                            if (errorLabel.getText().isEmpty()) {
                                //check if the appropriate offer price is enterred
                                try{
                                    if ((offerPrice - ((Sale) post).getHighestOffer()) < ((Sale) post).getMinRaise()) {
                                        throw new InvalidOfferException("Offer price does not exceed minimum raise");
                                    }
                                } catch(InvalidOfferException e) {
                                    newPostController.error.setTitle("Changes cannot be saved!");
                                    newPostController.error.setHeaderText(e.getMessage());
                                    newPostController.error.showAndWait();
                                }
                                if ((offerPrice - ((Sale) post).getHighestOffer()) >= ((Sale) post).getMinRaise()) {
                                    MainGUI.replies.add(new Reply(post.getId(), offerPrice, LoginController.username));
                                    newPostController.information.setTitle("Changes saved!");
                                    newPostController.information.setHeaderText("Changes have been saved!");
                                    newPostController.information.showAndWait();
                                    //setting the highest offer
                                    ((Sale) post).setHighestOffer(offerPrice);
                                }
                            }
                        }
                         else {
                             //check if value enterred is float
                            try {
                                paymentOffer = Float.parseFloat(replyTextField.getText());
                            } catch (NumberFormatException e) {
                                errorLabel.setText("Offer price cannot be string.");
                            }

                            if(errorLabel.getText().isEmpty()) {
                                //check if appropriate offer price is enterred
                                try{
                                    if((paymentOffer > ((Job) post).getProposedPrice())){
                                        throw new InvalidOfferException("Current offer price is greater than proposed price!");
                                    }
                                }catch(InvalidOfferException e){
                                    newPostController.error.setTitle("Changes cannot be saved!");
                                    newPostController.error.setHeaderText(e.getMessage());
                                    newPostController.error.showAndWait();
                                }
                                if (paymentOffer <= ((Job) post).getProposedPrice()) {
                                    MainGUI.replies.add(new Reply(post.getId(), paymentOffer, LoginController.username));
                                    newPostController.information.setTitle("Changes saved!");
                                    newPostController.information.setHeaderText("Changes have been saved!");
                                    newPostController.information.showAndWait();
                                    //setting the lowest offer
                                    ((Job) post).setLowestOffer(paymentOffer);
                                }
                            }
                        }
                    }
            }
        });
    }

    /**
     * This method allows to jump back to main window
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
        controller.initialize(LoginController.username);

        Stage window = new Stage();
        window.setResizable(false);
        window.setScene(mainWindowScene);
        window.show();
    }


}
