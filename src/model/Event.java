package model;
import java.io.Serializable;
import java.util.Scanner;

public class Event extends Post implements Serializable {
    private String venue;
    protected String date;
    private int capacity;
    public int attendeeCount;
    String photo;

    /**
     * constructor overloading
      */
    public Event() {
       super();
    }

    public Event(String id, String title, String description, String username, String venue, String date,
                 int capacity) {
        super(id, title, description, username);
        this.venue = venue;
        this.date = date;
        this.capacity = capacity;


    }

    public Event(String id, String title, String description, String username, String venue, String date,
                 int capacity, String photo) {
        super(id, title, description, username);
        this.venue = venue;
        this.date = date;
        this.capacity = capacity;
        this.photo = photo;

    }

    public Event(String id, String title, String description, String username,String status, String venue, String date,
                 int capacity, String photo,int attendeeCount) {
        super(id, title, description, username,status);
        this.venue = venue;
        this.date = date;
        this.capacity = capacity;
        this.photo = photo;
        this.attendeeCount=attendeeCount;

    }

    /**
     *  getters and setters to access private variables
      */
    public String getVenue() {
        return venue;
    }

    public void setDate(String changedDate) {
        this.date = changedDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDate() {
        return this.date;
    }

    public void setAttendeeCount(int attendeeCount2) {
        this.attendeeCount = attendeeCount2;
    }

    public int getAttendeeCount() {
        return this.attendeeCount;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


//    public void createEventPost() {
//        // TODO Auto-generated method stub
//        System.out.println("Enter details of the event below:\nName: ");
//        title = s.next();
//        title += s.nextLine();
//        System.out.println("Description: ");
//        description = s.next();
//        description += s.nextLine();
//        System.out.println("Venue: ");
//        venue = s.next();
//        venue += s.nextLine();
//
//        // to check whether date enterred is in required format
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
//
//        boolean isdate = false;
//        do {
//            try {
//                System.out.println("Date: ");
//                date = s.next();
//                Date dt = sdf.parse(date);
//                date = new SimpleDateFormat("dd/MM/yyyy").format(dt);
//                setDate(date);
//                // System.out.println("Date :" + date);
//                isdate = true;
//
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                System.err.println("Date cannot be parsed.");
//
//            } catch (NullPointerException e) {
//
//                e.printStackTrace();
//
//            }
//        } while (!isdate);
//        // to check if capacity entered is integer
//        do {
//            try {
//                System.out.println("Capacity: ");
//                capacity = Integer.parseInt(s.next());
//                capErr = false;
//            } catch (NumberFormatException e) {
//                System.err.println("enter an integer");
//            }
//
//        } while (capErr == true || capacity < 0);
//
//        // forming the event id beginning with EVE and padding extra 0's and
//        // incrementing the last digit
//    }

//    public String getPostDetails() {
//        return (super.getPostDetails() + "\nVenue:\t\t" + venue + "\nCapacity:\t\t" + capacity + "\nAttendees:\t"
//                + this.attendeeCount + "\nDate:\t\t" + this.date+"\nPhoto path:\t\t"+photo+"\n--------------------------------------");
//    }


    @Override
    public boolean handleReply(Reply reply) {
        // owner of the post is assumed to be going to the event
        for (Reply r : replies) {
            if (r.getResponderId() == reply.getResponderId() && r.getPostid() == reply.getPostid()) {
                System.out.println("You have responded already!");
                return false;

            }
        }
        replies.add(new Reply(reply.getPostid(), reply.getResponseValue(), reply.getResponderId()));
        this.attendeeCount += 1;
        if (attendeeCount == capacity) {
            status = "CLOSED";
        }
        setAttendeeCount(this.attendeeCount);
        return true;
    }

    @Override
    public String getReplyDetails() {
        String attendeeList = "";
        if (attendeeCount == 0) {
            return ("Attendee list:\t Empty");
        } else {
            for (Reply reply : replies) {
                if (reply.getPostid().toUpperCase().compareTo(super.getId()) == 0) {
                    attendeeList += reply.getResponderId() + ",";
                }
            }
            return ("Attendee: " + attendeeList);
        }
    }
}
