package controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class newPostController implements Serializable {
    /**
     * This is a superclass controller to hold common details like id,title,description and alert box types
     */
    private String id;
    private String title;
    private String description;
    //private Image defaultImage;
    static Alert information = new Alert(Alert.AlertType.INFORMATION);
    static Alert error = new Alert(Alert.AlertType.ERROR);
    static Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
   // BufferedImage defaultBufferedImage = ImageIO.read(new File("images/noImageAvailable.jpg"));

    /**
     * getters and setters
     * @return
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Image getDefaultImage() {
//        return defaultImage;
//    }
//
//    public void setDefaultImage(Image defaultImage) {
//        this.defaultImage = defaultImage;
//    }


}
