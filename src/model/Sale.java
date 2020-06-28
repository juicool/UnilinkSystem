package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Sale extends Post implements Serializable {
    private double askingPrice;
    private double minRaise;
    private double highestOffer;
    String photo;


    /**
     * constructor overloading
     */
    public Sale() { super(); }

    public Sale(String id, String title, String description, String username, double askingPrice, double minRaise) {
        super(id, title, description, username);
        this.askingPrice = askingPrice;
        this.minRaise = minRaise;
        highestOffer = 0.0;

    }

    public Sale(String id, String title, String description, String username, double askingPrice, double minRaise,String photo) {
        super(id, title, description, username);
        this.askingPrice = askingPrice;
        this.minRaise = minRaise;
        this.highestOffer = 0.0;
        this.photo = photo;
    }

    public Sale(String id, String title, String description, String username, String status, double askingPrice, double minRaise,double highestOffer,String photo) {
        super(id, title, description, username,status);
        this.askingPrice = askingPrice;
        this.minRaise = minRaise;
        this.highestOffer = highestOffer;
        this.photo = photo;
    }

    /**
     *  getters and setters to access private variables
     */
    public double getAskingPrice() {
        return askingPrice;
    }

    public double getMinRaise() {
        return minRaise;
    }

    public double getHighestOffer() {
        return highestOffer;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setHighestOffer(double responseValue) {
        this.highestOffer=responseValue;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


//
//    public String createSalePost() {
//        System.out.println("Enter details of the item of sale below:\nName:");
//        title = s.next();
//        title += s.nextLine();
//        System.out.println("Description: ");
//        description = s.next();
//        description += s.nextLine();
//
//        // to check if asking price entered is double
//        do {
//            try {
//                System.out.println("Asking price: ");
//                askingPrice = Double.parseDouble(s.next());
//                askPriceErr = false;
//
//            } catch (NumberFormatException e) {
//                System.err.println("enter a double");
//            }
//        } while (askPriceErr == true || askingPrice < 0.0);
//
//        // to check if minimum raise entered is double
//        do {
//            try {
//                System.out.println("Minimum raise:");
//                minRaise = Double.parseDouble(s.next());
//                minRaiseErr = false;
//            } catch (NumberFormatException e) {
//                System.err.println("enter a double");
//            }
//        } while (minRaiseErr == true || minRaise < 0.0);
//
//        // forming the sale id beginning with SAL and padding extra 0's and
//        // incrementing the last digit
//        saleId = "SAL" + String.format("%03d", (Integer.parseInt(getSaleId().substring(3, getSaleId().length())) + 1));
//        setSaleId(saleId);
//        return saleId;
//
//    }

    // asking price visible to creator id
//    public String getPostDetails() {
//        return (super.getPostDetails() + "\nMinimum raise: $" + minRaise + "\nHighest Offer: $" + highestOffer+"\nAsking price: $"+askingPrice+"\n--------------------------------------");
//    }

    @Override
    public boolean handleReply(Reply reply) {
        for (Reply r : replies) {
            if (r.getResponderId() == reply.getResponderId() && r.getPostid() == reply.getPostid()) {
                System.out.println("You have responded already!");
                return false;
            }
        }
        // latest/highest offer claimed should be greater than the minimum raise
        if ((reply.getResponseDoubleValue() - (this.highestOffer)) >= minRaise) {
            replies.add(new Reply(reply.getPostid(), reply.getResponseDoubleValue(), reply.getResponderId()));
            setHighestOffer(reply.getResponseDoubleValue());
            return true;
        } else {
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
        return ("Asking price: $" + askingPrice + "\n\n-- Offer History --\n" + offerHistory);
    }
}
