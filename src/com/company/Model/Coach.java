package com.company.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Coach extends DatabaseConnection {
    private int CoachID;
    private String CoachName;
    private String CoachSurname;

    private static final String QUERY = "SELECT \"CoachID\", \"CoachName\",\"CoachSurname\" FROM \"Coach\"";


    @Override
    public String toString() {
        return "Coach{" +
                "CoachName='" + CoachName + '\'' +
                ", CoachSurname='" + CoachSurname + '\'' +
                '}';
    }

    public void AddCoach() {
        open();

        if (checkIfExistsInDatabase()) {
            System.out.println("Please write again name.");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.next();
            System.out.println("Please write again surname");
            String surname = scanner.next();
            int iD = checkCoachID() + 1;
            name = String.format("'" +name + "'");
            surname = String.format("'" + surname + "'");
            String finalValues = String.format("(" + iD + "," + name + "," +surname + ")" );
            try (Statement statement = conn.createStatement()) {
                int inserted = statement.executeUpdate("INSERT  INTO \"Coach\"(\"CoachID\",\"CoachName\", \"CoachSurname\") Values " + finalValues);
                if(inserted == 1 ){
                    System.out.println("You added coach correctly :)");
                }
                else{
                    System.out.println("Unfortunately , please once again.");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    public void deleteCoach() {
        open();
        try(Statement statement = conn.createStatement()){
            ResultSet  resultSet = statement.executeQuery(QUERY);
            while (resultSet.next()){
                System.out.println(resultSet.getString("CoachID") + " " + resultSet.getString("CoachName") + " " + resultSet.getString("CoachSurname"));
            }

        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        System.out.println("Choose CoachID");
        Scanner coachID = new Scanner(System.in);
        while (!coachID.hasNextInt()) {
            System.out.println("int, please!");
            coachID.nextLine();
        }
        coachID.nextInt();

        try(Statement statementDelete = conn.createStatement()) {
            ResultSet rs = statementDelete.executeQuery("DELETE FROM \"Coach\" WHERE \"CoachID\"=   " + coachID);
            //ResultSet rs = statementDelete.executeQuery("DELETE  FROM \"Coach\" WHERE  \"CoachID\" =    " + String.format(coachID));

        }
        catch (SQLException e){
                System.out.println(e.getMessage());
        }


    }
    private boolean checkIfExistsInDatabase() {
        open();
        System.out.println("Which coach do you want to add?");
        System.out.println("Write  a name ");
        Scanner name = new Scanner(System.in);

        while (name.hasNextInt()) {
            System.out.println("string, please!");
            name.nextLine();
        }
        name.nextLine();

        System.out.println("Write  a surnname ");
        Scanner surname = new Scanner(System.in);

        while (surname.hasNextInt()) {
            System.out.println("string, please!");
            surname.nextLine();
        }
        surname.nextLine();


        try (Statement statement = conn.createStatement()) {

            ResultSet rs = statement.executeQuery("SELECT COUNT(*)  FROM \"Coach\" WHERE \"CoachName\" =   " + String.format("'" + name + "'") + "AND  \"CoachSurname\" = " + String.format("'" + surname + "'") );
            if(rs.next()) {
                //assign a variable to query and check if coach exists or not in DB
                int resultOfQuery = Integer.parseInt(rs.getString("count"));
                if(resultOfQuery == 0){
                    System.out.println("The coach doesnt exist in database, so you can add to a database ");
                    return true;
                }
                else{
                    System.out.println("The coach exist in database, you cant add to a database");
                    return false;
                }
            }
            return  false;
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        }
    }

    private int checkCoachID(){
        int countCoach = 0;
        open();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT (*) FROM \"Coach\"");
            while (resultSet.next()) {
                countCoach += Integer.parseInt(resultSet.getString("count"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return countCoach;
    }


    public void AllCoaches() {
        open();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY);
            while (resultSet.next()) {
                // Retrieve by column name
                System.out.println(resultSet.getString("CoachName") + " " + resultSet.getString("CoachSurname"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public int getCoachID() {
        return CoachID;
    }

    public void setCoachID(int coachID) {
        CoachID = coachID;
    }

    public String getCoachName() {
        return CoachName;
    }

    public void setCoachName(String coachName) {
        CoachName = coachName;
    }

    public String getCoachSurname() {
        return CoachSurname;
    }

    public void setCoachSurname(String coachSurname) {
        CoachSurname = coachSurname;
    }
}
