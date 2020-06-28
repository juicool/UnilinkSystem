package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.MainGUI;
import model.Event;
import model.Job;
import model.Post;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class NewJobViewController extends newPostController implements Serializable {
    @FXML private TextField titleTextField,proposedPriceTextField;
    @FXML private TextArea descriptionTextArea;
    @FXML private Button selectImageButton,createJobPost, close;
    @FXML private ImageView imageView;
    @FXML private Label errorLabel;
    private String name;
    private float proposedPrice;
    private static String jobId = "JOB000";
    private FileChooser fileChooser;
    private File filepath;
    private BufferedImage bufferedImage;
    private Image image;

    /**
     * Getters and setters
     * @return
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobId() {
        return jobId;
    }

    /**
     * This method sets the default image, username
     * @param username
     * @throws IOException
     */

    public void initialize(String username) throws IOException{
        bufferedImage = ImageIO.read(new File("images/No-image-available.png"));
        image = SwingFXUtils.toFXImage(bufferedImage, null);
        imageView.setImage(image);
        name=username;
        this.createJobPost.setDisable(true);
        filepath = new File("images/No-image-available.png");

    }

    /**This method allows to go back to main menu window
     *
     * @param actionEvent
     */
    public void onCloseButtonHandler(ActionEvent actionEvent){
        close.getScene().getWindow().hide();
    }


    /**
     * this method allows user to change the image
     */
    public void selectImageButtonPushed(ActionEvent actionEvent){
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        fileChooser=new FileChooser();
        fileChooser.setTitle("Open Image");


        String userDirectoryString = System.getProperty("user.home")+"\\Pictures";
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fileChooser.setInitialDirectory(userDirectory);

        this.filepath=fileChooser.showOpenDialog(stage);

        try{
            bufferedImage= ImageIO.read(filepath);
            image = SwingFXUtils.toFXImage(bufferedImage,null);
            imageView.setImage(image);


        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * this method disables create new event button unless all the fields are filled
     */
    public void userEnterredDetailsInEveryTextField(){
        this.createJobPost.disableProperty().bind( titleTextField.textProperty().isEmpty());
        this.createJobPost.disableProperty().bind( descriptionTextArea.textProperty().isEmpty());
        this.createJobPost.disableProperty().bind(proposedPriceTextField.textProperty().isEmpty());
    }

    /**
     * This method takes values enterred in textfield and adds them into the collectPosts arraylist
     * @param actionEvent
     */
    @FXML private void onCreateButtonHandler(ActionEvent actionEvent) throws IOException {
        errorLabel.setText("");
        super.setTitle(titleTextField.getText());
        super.setDescription(descriptionTextArea.getText().replaceAll("\n", System.getProperty("line.separator")));

        //check if the value enterred is non-negative
        if (proposedPriceTextField.getText().matches("^-\\d+$")) {

            newPostController.error.setTitle("Error");
            newPostController.error.setHeaderText("Proposed price cannot be less than 0.");
            newPostController.error.showAndWait();
        }
        else {
            //check if the value enterred is float
            try {
                proposedPrice = Float.parseFloat(proposedPriceTextField.getText());
            } catch (NumberFormatException e) {
                errorLabel.setText("Proposed price cannot be string.");
            }
            if(errorLabel.getText().isEmpty()) {

               //setting jobid
                int totalJob = 0;
                for (Post post: MainGUI.collectPosts) {
                    if (post instanceof Job) {
                        ++totalJob;
                    }
                }
                jobId  = "JOB" + String.format("%03d", (totalJob + 1));
                setJobId(jobId);
                setId(jobId);

                createJobPost.getScene().getWindow().hide();

                newPostController.information.setTitle("Job Post creation successful");
                newPostController.information.setHeaderText("Job Post was created successfully with ID " + jobId + "!");
                newPostController.information.showAndWait();

                //add image to the /images folder
                ImageIO.write(SwingFXUtils.fromFXImage(image, bufferedImage),"png" ,new File("C:/Users/juilee/IdeaProjects/MyFirstProject/images/"+filepath.getName()));

                //adding job to arraylist
                MainGUI.collectPosts.add(new Job(jobId, super.getTitle(), super.getDescription(), name, proposedPrice,"images/"+filepath.getName()));

            }
        }
    }



}
