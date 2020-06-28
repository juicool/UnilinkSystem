package model;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    /**
     * This method is used for creating tables into the testDB database
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        final String DB_NAME = "testDB";

        /**
         *This is the code for creating post table
         * */
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            int postResult = stmt.executeUpdate("CREATE TABLE POST ("
                    + "id VARCHAR(8) NOT NULL,"
                    + "title VARCHAR(20) NOT NULL,"
                    + "description VARCHAR(20) NOT NULL,"
                    + "creatorId VARCHAR(20) NOT NULL,"
                    + "status VARCHAR(20) NOT NULL,"
                    + "photo VARCHAR(100) NOT NULL,"
                    + "venue VARCHAR(20),"
                    + "date VARCHAR(15) ,"
                    + "capacity INT ,"
                    + "attendeeCount INT,"
                    + "askingPrice FLOAT ,"
                    + "minimumRaise FLOAT ,"
                    + "highestOffer FLOAT ,"
                    + "proposedPrice FLOAT ,"
                    + "lowestOffer FLOAT ,"
                    + "PRIMARY KEY (id))");
            if(postResult == 0) {
                System.out.println("Table POST has been created successfully");
            } else {
                System.out.println("Table POST is not created");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        /**
         *This is the code for creating reply table
         * */
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            int replyResult = stmt.executeUpdate("CREATE TABLE REPLY ("
                    + "postId VARCHAR(8) NOT NULL,"
                    + "responseValue FLOAT NOT NULL,"
                    + "responderId VARCHAR(8) NOT NULL)");

            if(replyResult == 0) {
                System.out.println("Table REPLY has been created successfully");
            } else {
                System.out.println("Table REPLY is not created");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
