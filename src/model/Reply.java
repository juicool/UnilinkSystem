package model;

import java.io.Serializable;
import java.util.Scanner;

public class Reply implements Serializable {
    private String postid;
    private int responseValue;
    public double responseDoubleValue;
    public String responderId;



    /**
     * Constructor overloading
     * @param postid
     * @param responseValue
     * @param responderId
     */
    public Reply(String postid, int responseValue, String responderId) {
        this.postid = postid;
        this.responseValue = responseValue;
        this.responderId = responderId;
    }


    public Reply(String postid, double responseDoubleValue, String responderId) {
        this.postid = postid;
        this.responseDoubleValue = responseDoubleValue;
        this.responderId = responderId;
    }

    /**
     * getters and setters
     * @return
     */

    public String getPostid() {
        return postid;
    }

    public int getResponseValue() {
        return responseValue;
    }

    public double getResponseDoubleValue() {
        return responseDoubleValue;
    }

    public String getResponderId() {
        return responderId;
    }

}
