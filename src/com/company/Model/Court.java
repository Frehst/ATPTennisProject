package com.company.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Court extends DatabaseConnection{
    private int CourtID;
    private String CourtName;


    private static final String QUERY = "SELECT \"CourtID\", \"CourtName\" FROM \"Court\"";

    public int getCourtID() {
        return CourtID;
    }

    public void AllCourts(){
        open();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY);
            while (resultSet.next()) {
                // Retrieve by column name
                System.out.println(resultSet.getString("CourtID") + " " + resultSet.getString("CourtName"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setCourtID(int courtID) {
        CourtID = courtID;
    }

    public String getCourtName() {
        return CourtName;
    }

    public void setCourtName(String courtName) {
        CourtName = courtName;
    }
}
