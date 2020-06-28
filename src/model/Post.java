package model;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Post implements Serializable {

    public String id;
    public String title;
    public String description;
    public String creator_id;
    public String status = "Open";

    public static ArrayList<Reply> replies=new ArrayList<Reply>();

    /**
     * constructor overloading
     */
    public Post(){ }

    public Post(String id, String title, String description, String username) {
        this.id = id;
        this.title = title;
        this.description = description;
        creator_id = username;
        status = "Open";

    }
    public Post(String id, String title, String description, String username, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        creator_id = username;
        this.status = status;

    }

    /**
     * Getters and setters
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

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * this method returns all the details of a particular post
     * @return
     */
//    public String getPostDetails() {
//        return ("--------------------------------\nID:\t\t\t\t" + id + "\nTitle:\t\t\t" + title + "\nDescription:\t"
//                + description + "\nCreator ID:\t\t" + creator_id + "\nStatus:\t\t\t" + status);
//    }


    public abstract boolean handleReply(Reply reply);

    public abstract String getReplyDetails();
}
