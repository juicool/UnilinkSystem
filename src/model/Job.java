package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Job extends Post implements Serializable {
    private double proposedPrice;
    private double lowestOffer ;
    String photo;

    /**
     * constructor overloading
     */
    public Job() { super(); }

    public Job(String id, String title, String description, String username, double proposedPrice) {
        super(id, title, description, username);
        this.proposedPrice = proposedPrice;
        lowestOffer = 0.0;
    }

    public Job(String id, String title, String description, String username, double proposedPrice, String photo) {
        super(id, title, description, username);
        this.proposedPrice = proposedPrice;
        lowestOffer = 0.0;
        this.photo = photo;
    }

    public Job(String id, String title, String description, String username,String status, double proposedPrice,double lowestOffer, String photo) {
        super(id, title, description, username,status);
        this.proposedPrice = proposedPrice;
        this.lowestOffer = lowestOffer;
        this.photo = photo;
    }


    /**
     *  getters and setters to access private variables
     */
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getProposedPrice() {
        return proposedPrice;
    }

//    public void setProposedPrice(double proposedPrice) {
//        this.proposedPrice = proposedPrice;
//    }

    public double getLowestOffer() {
        return lowestOffer;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setLowestOffer(double lowestOffer) {
        if(this.lowestOffer ==0)
        {
            this.lowestOffer = lowestOffer;
        }
        else if(this.lowestOffer > lowestOffer){
            this.lowestOffer = lowestOffer;
        }

    }

//    public String createJobPost() {
//        System.out.println("Enter details of the job below:\nName: ");
//
//        title = s.next();
//        title += s.nextLine();
//
//        System.out.println("Description: ");
//
//        description = s.next();
//        description += s.nextLine();
//
//        // to check if proposed price entered is double
//        do {
//            try {
//                System.out.println("Proposed price: ");
//                proposedPrice = Double.parseDouble(s.next());
//                proposedPriceErr = false;
//            } catch (NumberFormatException e) {
//                System.err.println("enter a double");
//            }
//        } while (proposedPriceErr == true || proposedPrice < 0.0);
//
//        // forming the job id beginning with JOB and padding extra 0's and
//        // incrementing the last digit
//        jobId = "JOB" + String.format("%03d", (Integer.parseInt(getJobId().substring(3, getJobId().length())) + 1));
//        setJobId(jobId);
//        return jobId;
//
//    }

//    public String getPostDetails() {
//        return (super.getPostDetails() + "\nProposed Price: $" + proposedPrice + "\nLowest Offer:   $" + lowestOffer+"\n--------------------------------------");
//    }

    @Override
    public boolean handleReply(Reply reply) {
        for (Reply r : replies) {
            if (r.getResponderId() == reply.getResponderId() && r.getPostid() == reply.getPostid()) {
                System.out.println("You have responded already!");
                return false;
            }
        }
        // if no price was offered earlier i.e. lowestOffer =0 then taking the condition
        // of response < proposed price as the working expression else considering the
        // previously offered lowest price
        if (lowestOffer == 0 ? reply.getResponseDoubleValue() < proposedPrice
                : reply.getResponseDoubleValue() < this.lowestOffer) {
            replies.add(new Reply(reply.getPostid(), reply.getResponseDoubleValue(), reply.getResponderId()));
            setLowestOffer(reply.getResponseDoubleValue());
            return true;
        }
        else {
            System.out.println("Offer not accepted!");
            return false;
        }
    }

    @Override
    public String getReplyDetails() {
        // use of hashmap for ordering purposes
        Map<String, Double> offerHistory = new HashMap<String, Double>();
        for (Reply reply : replies) {
            if (reply.getPostid().toUpperCase().compareTo(super.getId()) == 0) {
                ((Map<String, Double>) offerHistory).put(reply.getResponderId(), reply.getResponseDoubleValue());
            }
        }
        offerHistory.toString();
        return ("-- Offer History --\n" + offerHistory + "\n");
    }

}
