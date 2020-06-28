package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.stage.Stage;
import model.CreateTable;
import model.SelectQuery;

import java.io.IOException;
import java.io.Serializable;
import java.util.regex.Pattern;

public class LoginController implements Serializable {
    @FXML private Label outputLabel;
    @FXML public TextField nameTextField;
    public static String username;
    @FXML private Button submit;


    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    /**
     * This method is used to initialize values when the window opens and retrieve data from the database
     */
    @FXML
    private void initialize() {
        SelectQuery.selectQuery();
        outputLabel.setText(" ");
    }

    /**
     * this method is used when the user submits the username
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void submitButtonHandler(ActionEvent actionEvent) throws IOException {

        username = nameTextField.getText().trim(); //to trim out blank spaces
        setUsername(username);

        //check the username conditions like length should not exceed 8, etc.
        if (Pattern.matches("[sS][0-9]{1,7}", username)) {
            //if all the conditions are satisfied then main window opens
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


            nameTextField.setText("");
            outputLabel.setText("");


        } else {
            outputLabel.setText("Student id entered is not valid.");

        }
    }
}








































