package com.company.Model;

import java.sql.*;

public class Backhand extends DatabaseConnection{

    static final String QUERY = "SELECT \"CoachName\", \"CoachSurname\" From public.\"Coach\"";

    private int BackhendID;
    private String Backhand;

    public int getBackhendID() {
        return BackhendID;
    }

    public void setBackhendID(int backhendID) {
        BackhendID = backhendID;
    }

    public String getBackhand() {
        return Backhand;
    }

    public void setBackhand(String backhand) {
        Backhand = backhand;
    }


//    private Connection conn;
//
//    private boolean open(){
//        try {
//            conn = DriverManager.getConnection(url,user,password);
//            return true;
//        }
//        catch (SQLException e){
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
    public void QueryBackhand(){
        open();
        try(Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery(QUERY);
            while (resultSet.next()) {
                // Retrieve by column name
                System.out.println(resultSet.getString("CoachName") + " " + resultSet.getString("CoachSurname") );
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
