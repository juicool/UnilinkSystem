package model;
import main.MainGUI;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectQuery {
    public static void main(String[] args) {
        selectQuery();
    }
    public static void selectQuery(){
        final String DB_NAME = "testDB";

        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            String query = "SELECT * FROM POST "; //getting tuples from POST table

            try (ResultSet resultSet = stmt.executeQuery(query)) //adding results from above query to resultSet
            {
                while(resultSet.next()) {
                    //if the resultSet has event post ass its tuple then add it to the arraylist
                    if(resultSet.getString("id").substring(0,3).compareToIgnoreCase("EVE")==0) {
                        MainGUI.collectPosts.add(new Event(resultSet.getString("id"),resultSet.getString("title"),resultSet.getString("description"),resultSet.getString("creatorId"),resultSet.getString("status"),resultSet.getString("venue"),resultSet.getString("date"),resultSet.getInt("capacity"),resultSet.getString("photo"),resultSet.getInt("attendeeCount")));
                    }
                    //if the resultSet has sale post ass its tuple then add it to the arraylist
                    else if(resultSet.getString("id").substring(0,3).compareToIgnoreCase("SAL")==0){
                        MainGUI.collectPosts.add(new Sale(resultSet.getString("id"),resultSet.getString("title"),resultSet.getString("description"),resultSet.getString("creatorId"),resultSet.getString("status"),resultSet.getFloat("askingPrice"),resultSet.getFloat("minimumRaise"),resultSet.getFloat("highestOffer"),resultSet.getString("photo")));
                    }
                    //if the resultSet has job post ass its tuple then add it to the arraylist
                    else{
                        MainGUI.collectPosts.add(new Job(resultSet.getString("id"),resultSet.getString("title"),resultSet.getString("description"),resultSet.getString("creatorId"),resultSet.getString("status"),resultSet.getFloat("proposedPrice"),resultSet.getFloat("lowestOffer"),resultSet.getString("photo")));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            String query1 = "SELECT * FROM REPLY "; //getting tuples from REPLY table

            try (ResultSet resultSet = stmt.executeQuery(query1)) //adding results from above query to resultSet
            {
                while(resultSet.next()) {

                    if(resultSet.getString("postId").substring(0,3).compareToIgnoreCase("EVE")==0) {
                        MainGUI.replies.add(new Reply(resultSet.getString("postId"), resultSet.getInt("responseValue"),resultSet.getString("responderId")));
                    }
                    else{
                        MainGUI.replies.add(new Reply(resultSet.getString("postId"), resultSet.getFloat("responseValue"),resultSet.getString("responderId")));
                    }

                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
