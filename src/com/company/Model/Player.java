package com.company.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Player extends DatabaseConnection{
    private int PlayerID;
    private String PlayerName;
    private String PlayerSurname;
    private Country CountryID;
    private Court CourtID;
    private Coach CoachID;
    private Date Age;
    private int Points;
    private Forehand ForehandID;
    private Backhand BackhandID;

    static final String VIEW =  "SELECT \"PlayerName\",\"PlayerSurname\", \"CountryName\", \"CourtName\", \"CoachName\", \"CoachSurname\", \"Age\", \"Points\", \"Forehand\", \"Backhand\" From \"Player\"" +
                                "INNER JOIN \"Country\" ON  \"Player\".\"CountryID\" = \"Country\".\"CountryID\"" +
                                "INNER JOIN \"Court\" ON \"Player\".\"CourtID\" = \"Court\".\"CourtID\"" +
                                "INNER JOIN \"Coach\" ON \"Player\".\"CoachID\" = \"Coach\".\"CoachID\""+
                                "INNER JOIN \"Forehand\" ON \"Player\".\"ForehandID\" = \"Forehand\".\"ForehandID\""+
                                "INNER JOIN \"Backhand\" ON \"Player\".\"BackhandID\" = \"Backhand\".\"BackhandID\"";

    static final String RANK_BY_Points = "SELECT \"PlayerSurname\", \"PlayerName\", \"Points\" FROM \"Player\" ORDER BY \"Points\" ";


    public int getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(int playerID) {
        PlayerID = playerID;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public String getPlayerSurname() {
        return PlayerSurname;
    }

    public void setPlayerSurname(String playerSurname) {
        PlayerSurname = playerSurname;
    }

    public Country getCountryID() {
        return CountryID;
    }

    public void setCountryID(Country countryID) {
        CountryID = countryID;
    }

    public Court getCourtID() {
        return CourtID;
    }

    public void setCourtID(Court courtID) {
        CourtID = courtID;
    }

    public Coach getCoachID() {
        return CoachID;
    }

    public void setCoachID(Coach coachID) {
        CoachID = coachID;
    }

    public Date getAge() {
        return Age;
    }

    public void setAge(Date age) {
        Age = age;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public Forehand getForehandID() {
        return ForehandID;
    }

    public void setForehandID(Forehand forehandID) {
        ForehandID = forehandID;
    }

    public Backhand getBackhandID() {
        return BackhandID;
    }

    public void setBackhandID(Backhand backhandID) {
        BackhandID = backhandID;
    }

    public void rankPlayers(){
        open();
        int i = 1;
        try(Statement statement = conn.createStatement()){
            ResultSet resultSet = statement.executeQuery(RANK_BY_Points);

            while (resultSet.next()){
                System.out.println(i+" " + resultSet.getString("PlayerSurname")
                        + " " + resultSet.getString("PlayerName") + " " + resultSet.getString("Points"));

                i += 1;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void searchPlayer(){
        System.out.println("Which option\n1-Search by forehand" +
                "\n2-Search by backhand\n3-search by name\n4-search by surname\n5-search by country\n6-search by favourite surface?");
        Scanner scanner = new Scanner(System.in);
        int decision = scanner.nextInt();
        switch (decision) {
            case 1 -> searchPlayerByForehand();
            case 2 -> searchPlayerByBackhand();
            case 3 -> searchPlayerByName();
            case 4 -> searchPlayerBySurname();
            case 5 -> searchPlayerByCountry();
            case 6 -> searchPlayerByFavouriteSurface();
        }
    }

    private void searchPlayerByFavouriteSurface() {
        open();

        try (Statement statement = conn.createStatement()){

            Statement decision = conn.createStatement();
            ResultSet resultSetCourt = decision.executeQuery("Select \"CourtID\", \"CourtName\" From \"Court\"");
            while (resultSetCourt.next()){
                System.out.println(resultSetCourt.getString("CourtName"));
            }

            System.out.println("Which surface do you choose to find players in Atp Ranking?");
            Scanner scanner = new Scanner(System.in);

            int option = scanner.nextInt();
            ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                    "AGE(\"Age\"), \"Points\"" +
                    "From \"Player\" WHERE \"CourtID\" = " + String.format("'" + option + "'"));
            while(resultSet.next()){
                System.out.println(resultSet.getString("PlayerName") +" " + resultSet.getString("PlayerSurname") + " " +
                        resultSet.getString("Age") + " " + resultSet.getString("Points")

                );
            }
        }
        catch (SQLException e){
            System.out.println("e.getMessage()");
        }
    }

    private void searchPlayerByCountry() {
        open();
        try (Statement statement = conn.createStatement()){

            Statement decision = conn.createStatement();
            ResultSet resultSetCountry = decision.executeQuery("Select  \"Country\" From \"Country\"");
            while (resultSetCountry.next()){
                System.out.println(resultSetCountry.getString("Country"));
            }

            System.out.println("Which country do you choose to find players in Atp Ranking?");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                    "AGE(\"Age\"), \"Points\"" +
                    "From \"Player\" WHERE \"CountryID\" = " + String.format("'" + option + "'"));
            while(resultSet.next()){
                System.out.println(resultSet.getString("PlayerName") +" " + resultSet.getString("PlayerSurname") + " " +
                        resultSet.getString("Age") + " " + resultSet.getString("Points")

                );
            }
        }
        catch (SQLException e){
            System.out.println("e.getMessage()");
        }
    }

    private void searchPlayerBySurname() {
        open();
        try (Statement statement = conn.createStatement()){

            System.out.println("Which surname do you choose to find players in Atp Ranking?");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();
            ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                    "AGE(\"Age\"), \"Points\"" +
                    "From \"Player\" WHERE \"PlayerSurname\" = " + String.format("'" + option + "'"));
            while(resultSet.next()){
                System.out.println(resultSet.getString("PlayerName") +" " + resultSet.getString("PlayerSurname") + " " +
                        resultSet.getString("Age") + " " + resultSet.getString("Points")

                );
            }
        }
        catch (SQLException e){
            System.out.println("e.getMessage()");
        }
    }

    private void searchPlayerByName() {
        open();
        try (Statement statement = conn.createStatement()){

            System.out.println("Which name do you choose to find players in Atp Ranking?");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();
            ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                    "AGE(\"Age\"), \"Points\"" +
                    "From \"Player\" WHERE \"PlayerName\" = " + String.format("'" + option + "'"));
            while(resultSet.next()){
                System.out.println(resultSet.getString("PlayerName") +" " + resultSet.getString("PlayerSurname") + " " +
                        resultSet.getString("Age") + " " + resultSet.getString("Points")

                );
            }
        }
        catch (SQLException e){
            System.out.println("e.getMessage()");
        }
    }

    private void searchPlayerByBackhand() {
        open();
        try (Statement statement = conn.createStatement()){
            Statement decision = conn.createStatement();
            ResultSet resultSetForehand = decision.executeQuery("Select \"BackhandID\", \"Backhand\" From \"Backhand\"");
            while (resultSetForehand.next()){
                System.out.println(resultSetForehand.getString("BackhandID") + " " + resultSetForehand.getString("Backhand"));
            }
            System.out.println("Which option do you choose?");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                    "AGE(\"Age\"), \"Points\"" +
                    "From \"Player\" WHERE \"BackhandID\" = " + String.format("'" + option + "'"));
            while(resultSet.next()){
                System.out.println(resultSet.getString("PlayerName") +" " + resultSet.getString("PlayerSurname") + " " +
                        resultSet.getString("Age") + " " + resultSet.getString("Points")

                );
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private void searchPlayerByForehand() {
        open();
        try (Statement statement = conn.createStatement()){
            Statement decision = conn.createStatement();
            ResultSet resultSetForehand = decision.executeQuery("Select \"ForehandID\", \"Forehand\" From \"Forehand\"");
            while (resultSetForehand.next()){
                System.out.println(resultSetForehand.getString("ForehandID") + " " + resultSetForehand.getString("Forehand"));
            }
            System.out.println("Which option do you choose?");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                                                        "AGE(\"Age\"), \"Points\"" +
                                                        "From \"Player\" WHERE \"ForehandID\" = " + String.format("'" + option + "'"));
            while(resultSet.next()){
                System.out.println(resultSet.getString("PlayerName") +" " + resultSet.getString("PlayerSurname") + " " +
                                    resultSet.getString("Age") + " " + resultSet.getString("Points")

                                  );
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void  viewWithAllForeignKeys() {
        open();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(VIEW);
            System.out.println("Name  Surname   Surface  CoachName CoachSurname  Date Points Fh BH");
            while (resultSet.next()) {

                System.out.println(resultSet.getString("PlayerName") + " " +
                         resultSet.getString("PlayerSurname") + " "+ resultSet.getString("CourtName")
                         + " "+ resultSet.getString("CoachName")+" " + resultSet.getString("CoachSurname")
                         + " " +resultSet.getString("Age")+ " " + resultSet.getString("Points")
                         + " " +resultSet.getString("Forehand")+ " " + resultSet.getString("Backhand"));

                System.out.println("______________________________________________________________________");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
