package main;

import controller.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Post;
import model.Reply;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class MainGUI extends Application implements Serializable {
    @FXML public static ArrayList<Post> collectPosts = new ArrayList<Post>(); // holding different posts
    @FXML public static ArrayList<Reply> replies = new ArrayList<>(); //holding replies

    @Override
    public void start(Stage stage)  {

     //TODO Data generation

      try {
            Parent root= FXMLLoader.load(getClass().getResource("/view/main_view.fxml"));
            Scene s = new Scene(root,379,223);
            stage.setTitle("Unilink System");
            stage.setScene(s);
            stage.show();
        } catch (IOException e) {
            System.out.println("Failure to load the view file!");
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
