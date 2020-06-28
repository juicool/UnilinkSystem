package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.*;
import main.MainGUI;
import model.Event;
import model.Post;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;


public class NewEventViewController extends newPostController implements Serializable {
@FXML private TextField titleTextField,venueTextField,capacityTextField;
@FXML private TextArea descriptionTextArea;
@FXML private DatePicker datePicker;
@FXML private Button selectImageButton,createEventPost, close;
@FXML private ImageView imageView;
@FXML private Label errorLabel;
private String venue,date,name;
private int capacity;
private static String eventId = "EVE000";
private FileChooser fileChooser;
private File filepath;
private Date formattedDate;
private Image image;
private BufferedImage bufferedImage;



    /**
     * Getters and setters
     * @return
     */
    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     *This method sets the default image, username
     * @param username
     * @throws IOException
     */
    @FXML public void initialize(String username) throws IOException {
        bufferedImage = ImageIO.read(new File("images/No-image-available.png"));
        image = SwingFXUtils.toFXImage(bufferedImage, null);
        imageView.setImage(image);
        name=username;
        this.createEventPost.setDisable(true);
        filepath = new File("images/No-image-available.png");
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
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }

    }

    /**
     * this method disables create new event button unless all the fields are filled
     */
    public void userEnterredDetailsInEveryTextField(){
        this.createEventPost.disableProperty().bind( titleTextField.textProperty().isEmpty());
        this.createEventPost.disableProperty().bind( descriptionTextArea.textProperty().isEmpty());
        this.createEventPost.disableProperty().bind(venueTextField.textProperty().isEmpty());
        this.createEventPost.disableProperty().bind(datePicker.valueProperty().isNull());
        this.createEventPost.disableProperty().bind (capacityTextField.textProperty().isEmpty());
    }

    /**This method allows to go back to main menu window
     *
     * @param actionEvent
     */
    public void onCloseButtonHandler(ActionEvent actionEvent){
        close.getScene().getWindow().hide();
    }

    /**
     * This method takes values enterred in textfield and adds them into the collectPosts arraylist
     * @param actionEvent
     */
    @FXML private void onCreateButtonHandler(ActionEvent actionEvent) throws IOException {
        errorLabel.setText("");
        super.setTitle(titleTextField.getText());
        super.setDescription(descriptionTextArea.getText().replaceAll("\n",System.getProperty("line.separator")));
        venue = venueTextField.getText();
        //check if the date falls after today and if no date is selected
        try{
            date= datePicker.valueProperty().get().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             formattedDate = sdf.parse(date);

        }catch (Exception e){
            errorLabel.setText("Date cannot be empty!\n");
            }
        if(formattedDate.before(new Date())){
            errorLabel.setText("Date should fall after today.");
        }
        else {
            //check if the value enterred is non-negative
            if (capacityTextField.getText().matches("^-\\d+$")) {
                newPostController.error.setTitle("Error");
                newPostController.error.setHeaderText("Capacity cannot be less than 0.");
                newPostController.error.showAndWait();
            } else {
                //check if the value enterred is int
                try {
                    capacity = Integer.parseInt(capacityTextField.getText());
                } catch (NumberFormatException e) {
                    errorLabel.setText("Capacity cannot be string.");
                }
                if (errorLabel.getText().isEmpty()) {
                    //setting the event id
                    int totalEvents = 0;
                    for (Post post: MainGUI.collectPosts) {
                        if (post instanceof Event) {
                            ++totalEvents;
                        }
                    }
                    eventId = "EVE" + String.format("%03d", (totalEvents + 1));
                    setEventId(eventId);
                    setId(eventId);

                    createEventPost.getScene().getWindow().hide();

                    newPostController.information.setTitle("Event Post creation successful");
                    newPostController.information.setHeaderText("Event Post was created successfully with ID " + eventId + "!");
                    newPostController.information.showAndWait();

                    //adding image to /images folder
                    ImageIO.write(bufferedImage, "png", new File("C:/Users/juilee/IdeaProjects/MyFirstProject/images/" + filepath.getName()));

                    //adding event to arraylist
                    MainGUI.collectPosts.add(new Event(eventId, super.getTitle(), super.getDescription(), name, venue, date, capacity,"images/"+filepath.getName()));

                }
            }
        }
    }

}
