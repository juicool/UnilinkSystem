package model;
import main.MainGUI;

import java.sql.Connection;
import java.sql.Statement;

public class InsertRow {

    public static void main(String[] args) {
        insertRow();


    }

    /**
     * This method is used to insert values into respective tables in the testDB database
     *
     */
    public static void insertRow() {
        final String DB_NAME = "testDB";
        String query = null;
        int result=0;
        /**
         * This block of code inserts data into the POST table
         */
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            String postDeleteQuery = "TRUNCATE TABLE POST ";
            stmt.executeUpdate(postDeleteQuery);
            System.out.println("Executed deleting post table!");
            for (Post p : MainGUI.collectPosts) {
                System.out.println(p.getStatus());
                //before inserting data into POST table, a check for EVENT,SALE or JOB post is done so that other values can be set to null
                if (p instanceof Event) {

                    query = "INSERT INTO POST VALUES ('" + p.getId() + "','" + p.getTitle() + "','" + p.getDescription() + "','" + p.getCreator_id() + "','" + p.getStatus() + "','" + ((Event) p).getPhoto() + "','" + ((Event) p).getVenue() + "','" + ((Event) p).getDate() + "','" + ((Event) p).getCapacity() + "','" + ((Event) p).getAttendeeCount() + "','0.0','0.0','0.0','0.0','0.0')";
                    System.out.println(query);
                } else if (p instanceof Sale) {

                    query = "INSERT INTO POST VALUES ('" + p.getId() + "','" + p.getTitle() + "','" + p.getDescription() + "','" + p.getCreator_id() + "','" + p.getStatus() + "','" + ((Sale) p).getPhoto() + "','null','null','0','0','" + ((Sale) p).getAskingPrice() + "','" + ((Sale) p).getMinRaise() + "','" + ((Sale) p).getHighestOffer() + "','0.0','0.0')";
                    System.out.println(query);
                } else {
                    query = "INSERT INTO POST VALUES ('" + p.getId() + "','" + p.getTitle() + "','" + p.getDescription() + "','" + p.getCreator_id() + "','" + p.getStatus() + "','" + ((Job) p).getPhoto() + "','null','null','0','0','0.0','0.0','0.0','" + ((Job) p).getProposedPrice() + "','" + ((Job) p).getLowestOffer() + "')";
                    System.out.println(query);
                }
                result = stmt.executeUpdate(query);
                System.out.println(result + " row(s) affected");

            }

            System.out.println("Insert into table POST executed successfully");
            con.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertReplyRow(){

        final String DB_NAME = "testDB";
        String  query1 = null;

        /**
         * This block of code inserts data into the REPLY table
         */
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            String query = "TRUNCATE TABLE REPLY";
            stmt.executeUpdate(query);
            for(Reply r: MainGUI.replies){
                if(r.getPostid().substring(0,3).compareToIgnoreCase("EVE")==0) {
                    query1 = "INSERT INTO REPLY VALUES('" + r.getPostid() + "','" + r.getResponseValue() + "','" + r.getResponderId() + "')";
                }
                else{
                    query1 = "INSERT INTO REPLY VALUES('" + r.getPostid() + "','" + r.getResponseDoubleValue() + "','" + r.getResponderId() + "')";
                }
                int result1 = stmt.executeUpdate(query1);
                System.out.println(result1 + " row(s) affected");
            }

            System.out.println("Insert into table REPLY executed successfully");
            con.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
