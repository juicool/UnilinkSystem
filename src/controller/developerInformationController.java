package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.Serializable;


public class developerInformationController implements Serializable {
    /**
     * This class is used to show the developer information
     */
    @FXML private Button close;

    /**
     * This method is used to go back to main window
     * @param actionEvent
     */
    @FXML
    public void onCloseButtonHandler(ActionEvent actionEvent){
        close.getScene().getWindow().hide();
    }
}
