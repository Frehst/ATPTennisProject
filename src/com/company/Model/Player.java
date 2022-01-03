package com.company.Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;
import java.util.InputMismatchException;

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


    private static final String MAX_LENGTH_NAME = "SELECT MAX(LENGTH(\"PlayerName\")) FROM \"Player\"";

    private static final String MAX_LENGTH_SURNAME = "SELECT MAX(LENGTH(\"PlayerSurname\")) FROM \"Player\"";

    private static final String MAX_LENGTH_COURT = "SELECT MAX(LENGTH(\"CourtName\")) FROM \"Court\"";

    private static final String MAX_LENGTH_COACH_NAME = "SELECT MAX(LENGTH(\"CoachName\")) FROM \"Coach\"";

    private static final String MAX_LENGTH_COACH_SURNAME = "SELECT MAX(LENGTH(\"CoachSurname\")) FROM \"Coach\"";

    private static final String MAX_LENGTH_POINTS = "SELECT LENGTH(MAX(points)::text) FROM \"Player\"";

    private static final String MAX_LENGTH_FH = "SELECT MAX(LENGTH(\"Forehand\")) FROM \"Forehand\"";

    private static final String MAX_LENGTH_BH = "SELECT MAX(LENGTH(\"Backhand\")) FROM \"Backhand\"";


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

        while(true) {
            try (Statement statement = conn.createStatement()) {

                Statement decision = conn.createStatement();
                ResultSet resultSetCourt = decision.executeQuery("Select \"CourtID\", \"CourtName\" From \"Court\"");
                while (resultSetCourt.next()) {
                    System.out.println(resultSetCourt.getString("CourtName"));
                }

                System.out.println("Which surface do you choose to find players in Atp Ranking?");
                Scanner scanner = new Scanner(System.in);
                try{
                int option = scanner.nextInt();
                if (option <= 3) {


                    ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                            "AGE(\"Age\"), \"Points\"" +
                            "From \"Player\" WHERE \"CourtID\" = " + String.format("'" + option + "'"));
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("PlayerName") + " " + resultSet.getString("PlayerSurname") + " " +
                                resultSet.getString("Age") + " " + resultSet.getString("Points")

                        );
                    }
                    break;
                }
                } catch (InputMismatchException e){
                    System.out.println("That’s not an integer. Try again: ");
                }
            } catch (SQLException e) {
                System.out.println("e.getMessage()");
            }
        }
    }

    private void searchPlayerByCountry() {
        open();
        while(true) {
            try (Statement statement = conn.createStatement()) {

                Statement decision = conn.createStatement();
                ResultSet resultSetCountry = decision.executeQuery("Select  \"Country\" From \"Country\"");
                while (resultSetCountry.next()) {
                    System.out.println(resultSetCountry.getString("Country"));
                }

                System.out.println("Which country do you choose to find players in Atp Ranking?");
                Scanner scanner = new Scanner(System.in);
                try {
                    int option = scanner.nextInt();
                    ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                            "AGE(\"Age\"), \"Points\"" +
                            "From \"Player\" WHERE \"CountryID\" = " + String.format("'" + option + "'"));
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("PlayerName") + " " + resultSet.getString("PlayerSurname") + " " +
                                resultSet.getString("Age") + " " + resultSet.getString("Points")

                        );
                    }
                    break;
                }
                catch (InputMismatchException e)
                {
                    System.out.println("That’s not an integer. Try again: ");
                }
            } catch (SQLException e) {
                System.out.println("e.getMessage()");
            }
        }
    }

    private void searchPlayerBySurname() {
        open();
        while(true) {
            try (Statement statement = conn.createStatement()) {

                System.out.println("Which surname do you choose to find players in Atp Ranking?");
                Scanner scanner = new Scanner(System.in);
                String option = scanner.next();
                ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                        "AGE(\"Age\"), \"Points\"" +
                        "From \"Player\" WHERE \"PlayerSurname\" = " + String.format("'" + option + "'"));
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("PlayerName") + " " + resultSet.getString("PlayerSurname") + " " +
                            resultSet.getString("Age") + " " + resultSet.getString("Points")

                    );
                }
                break;
            }catch (SQLException e) {
                    System.out.println("e.getMessage()");
                }
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

        while (true) {
            try (Statement statement = conn.createStatement()) {
                Statement decision = conn.createStatement();
                ResultSet resultSetForehand = decision.executeQuery("Select \"BackhandID\", \"Backhand\" From \"Backhand\"");
                while (resultSetForehand.next()) {
                    System.out.println(resultSetForehand.getString("BackhandID") + " " + resultSetForehand.getString("Backhand"));
                }
                System.out.println("Which option do you choose?");
                Scanner scanner = new Scanner(System.in);
                try {
                    int option = scanner.nextInt();
                    ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                            "AGE(\"Age\"), \"Points\"" +
                            "From \"Player\" WHERE \"BackhandID\" = " + String.format("'" + option + "'"));
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("PlayerName") + " " + resultSet.getString("PlayerSurname") + " " +
                                resultSet.getString("Age") + " " + resultSet.getString("Points")

                        );
                    }
                }
                catch (InputMismatchException e)
                {
                    System.out.println("That’s not an integer. Try again: ");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private void searchPlayerByForehand() {
        open();
        while (true) {
            try (Statement statement = conn.createStatement()) {
                Statement decision = conn.createStatement();
                ResultSet resultSetForehand = decision.executeQuery("Select \"ForehandID\", \"Forehand\" From \"Forehand\"");
                while (resultSetForehand.next()) {
                    System.out.println(resultSetForehand.getString("ForehandID") + " " + resultSetForehand.getString("Forehand"));
                }
                System.out.println("Which option do you choose?");
                Scanner scanner = new Scanner(System.in);
                try {
                    int option = scanner.nextInt();
                    ResultSet resultSet = statement.executeQuery("SELECT \"PlayerName\",\"PlayerSurname\"," +
                            "AGE(\"Age\"), \"Points\"" +
                            "From \"Player\" WHERE \"ForehandID\" = " + String.format("'" + option + "'"));
                    while (resultSet.next()) {
                        System.out.println(resultSet.getString("PlayerName") + " " + resultSet.getString("PlayerSurname") + " " +
                                resultSet.getString("Age") + " " + resultSet.getString("Points")

                        );
                    }
                }
                catch (InputMismatchException e)
                {
                    System.out.println("That’s not an integer. Try again: ");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int checkingMaxLengthPlayersName(){
        open();
        int maxPlayersNameLength = 0;
        try (Statement statement1 = conn.createStatement()){
            ResultSet maxPlayerNameLength = statement1.executeQuery(MAX_LENGTH_NAME);
            while(maxPlayerNameLength.next()){
                maxPlayersNameLength = Integer.parseInt(maxPlayerNameLength.getString("max"));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return maxPlayersNameLength;

    }

    private int checkingMaxLengthPlayerSurface(){
        open();
        int maxSurfacesLength = 0;
        try (Statement statement1 = conn.createStatement()){
            ResultSet maxPlayerNameLength = statement1.executeQuery(MAX_LENGTH_COURT);
            while(maxPlayerNameLength.next()){
                maxSurfacesLength = Integer.parseInt(maxPlayerNameLength.getString("max"));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return maxSurfacesLength;

    }

    private int checkingMaxLengthCoachName(){
        open();
        int maxLengthCoachName = 0;
        try (Statement statement1 = conn.createStatement()){
            ResultSet maxPlayerNameLength = statement1.executeQuery(MAX_LENGTH_COACH_NAME);
            while(maxPlayerNameLength.next()){
                maxLengthCoachName = Integer.parseInt(maxPlayerNameLength.getString("max"));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return maxLengthCoachName;

    }

    private int checkingMaxLengthCoachSurname(){
        open();
        int maxLengthCoachName = 0;
        try (Statement statement1 = conn.createStatement()){
            ResultSet maxPlayerNameLength = statement1.executeQuery(MAX_LENGTH_COACH_SURNAME);
            while(maxPlayerNameLength.next()){
                maxLengthCoachName = Integer.parseInt(maxPlayerNameLength.getString("max"));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return maxLengthCoachName;

    }

    private int checkingMaxLengthFH(){
        open();
        int maxLengthFH = 0;
        try (Statement statement1 = conn.createStatement()){
            ResultSet maxPlayerNameLength = statement1.executeQuery(MAX_LENGTH_FH);
            while(maxPlayerNameLength.next()){
                maxLengthFH = Integer.parseInt(maxPlayerNameLength.getString("max"));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return maxLengthFH;

    }

    private int checkingMaxLengthBH(){
        open();
        int maxLengthBH = 0;
        try (Statement statement1 = conn.createStatement()){
            ResultSet maxPlayerNameLength = statement1.executeQuery(MAX_LENGTH_BH);
            while(maxPlayerNameLength.next()){
                maxLengthBH = Integer.parseInt(maxPlayerNameLength.getString("max"));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return maxLengthBH;

    }
    private int checkingMaxLengthPlayersSurname(){
        open();
        int maxPlayersSurnameLength = 0;
        try (Statement statement = conn.createStatement()){
            ResultSet maxPlayerNameLength = statement.executeQuery(MAX_LENGTH_SURNAME);
            while(maxPlayerNameLength.next()){
                maxPlayersSurnameLength = Integer.parseInt(maxPlayerNameLength.getString("max"));
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return maxPlayersSurnameLength;

    }
    public void  viewWithAllForeignKeys() {
        open();
        try (Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(VIEW);

            StringBuilder table = new StringBuilder("|" + "Name");
            int nameLength = checkingMaxLengthPlayersName() - 4;
            table.append(" ".repeat(Math.max(0, nameLength)));

            table.append("|Surname");
            int surnameLength = checkingMaxLengthPlayersSurname() - 7;
            table.append(" ".repeat(Math.max(0, surnameLength)));

            table.append("|Surface");

            table.append("|C Name");
            int coachNameLength = checkingMaxLengthCoachName() - 6;
            table.append(" ".repeat(Math.max(0, coachNameLength)));

            table.append("|C Surname");
            int coachSurnameLength = checkingMaxLengthCoachSurname() - 9;
            table.append(" ".repeat(Math.max(0, coachSurnameLength)));

            table.append("|Birth Date ");

            table.append("|Points");

            table.append("|Forehand");
            int fhLength = checkingMaxLengthFH() - 8;
            table.append(" ".repeat(Math.max(0, fhLength)));

            table.append("|Backhand       |");




            System.out.println(table);

            while (resultSet.next()) {
                StringBuilder values = new StringBuilder("|");

                values.append(resultSet.getString("PlayerName"));
                int playerNamelength =  checkingMaxLengthPlayersName() - resultSet.getString("PlayerName").length() ;
                values.append(" ".repeat(Math.max(0, playerNamelength)));
                values.append("|");

                values.append(resultSet.getString("PlayerSurname"));
                int playerSuramelength =  checkingMaxLengthPlayersSurname() - resultSet.getString("PlayerSurname").length() ;
                values.append(" ".repeat(Math.max(0, playerSuramelength)));
                values.append("|");

                values.append(resultSet.getString("CourtName"));
                // 7 beacuse length of "Surface" is 7
                int surfaceLength =   7  - resultSet.getString("CourtName").length()  ;
                values.append(" ".repeat(Math.max(0, surfaceLength)));
                values.append("|");

                values.append(resultSet.getString("CoachName"));
                int coachNamesLength =  checkingMaxLengthCoachName() - resultSet.getString("CoachName").length() ;
                values.append(" ".repeat(Math.max(0, coachNamesLength)));
                values.append("|");

                values.append(resultSet.getString("CoachSurname"));
                int coachSurnamesLength =  checkingMaxLengthCoachSurname() - resultSet.getString("CoachSurname").length();
                values.append(" ".repeat(Math.max(0, coachSurnamesLength)));
                values.append("|");

                values.append(resultSet.getString("Age"));
                values.append(" |");

                values.append(resultSet.getString("Points"));
                int pointsLength =  6 - resultSet.getString("points").length() ;
                values.append(" ".repeat(Math.max(0, pointsLength)));
                values.append("|");

                values.append(resultSet.getString("Forehand"));
                int forehandLength =  checkingMaxLengthFH() - resultSet.getString("Forehand").length();
                values.append(" ".repeat(Math.max(0, forehandLength)));
                values.append("|");

                values.append(resultSet.getString("Backhand"));
                int bachkandLength =  checkingMaxLengthFH() - resultSet.getString("Backhand").length();
                values.append(" ".repeat(Math.max(0, bachkandLength)));
                values.append("|");

                System.out.println(values);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void AddPlayer(){
        open();

        if (checkIfExistsInDatabase()) {
            System.out.println("Please write again name.");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.next();
            System.out.println("Please write again surname");
            String surname = scanner.next();
            int iD = checkPlayerID() + 1;

            Country countries = new Country();
            countries.ALlCountries();

            System.out.println("Which country?");
            Scanner country = new Scanner(System.in);

            System.out.println("If this country doesnt exist input 0");
            int countryId = country.nextInt();

            if(countryId == 0){
                countries.AddCountry();

                countryId = country.nextInt();
            }

            Court courts = new Court();
            courts.AllCourts();
            System.out.println("Which court is the most favourite?");
            Scanner court = new Scanner(System.in);
            int courtID = court.nextInt();

            Coach coaches = new Coach();
            coaches.AllCoaches();
            Scanner decision = new Scanner(System.in);

            Scanner coach= new Scanner(System.in);

            System.out.println("Which decision?\n1-add");

            if(decision.nextInt() == 1){
                coaches.AddCoach();
                coaches.AllCoaches();

            }
            System.out.println("CoachID ?");
            int coachID = coach.nextInt();


            Scanner age = new Scanner(System.in);
            System.out.println("When you born?\n Format: 'YYYY-MM-DD");
            LocalDate date = LocalDate.parse(age.next());

            System.out.println("How many points do you have?");
            Scanner point = new Scanner(System.in);
            int points = point.nextInt();

            System.out.println("Which hand do yo play?");
            Scanner hand = new Scanner(System.in);
            int forehand = hand.nextInt();

            System.out.println("Your backhand is:\n1-double-handed\n2-one-handed?");
            Scanner bh = new Scanner(System.in);
            int backhand = bh.nextInt();

            String finalValues = String.format("(" + iD + "," + "'" + name + "'" + "," + "'" + surname + "'"  + "," + countryId + "," + courtID + ","
            + coachID + "," + "'" + date + "'" +  "," + points + "," + forehand + "," + backhand + ")");

            System.out.println(finalValues);
            try (Statement statement = conn.createStatement()) {
                int inserted = statement.executeUpdate("INSERT INTO public.\"Player\"(\n" +
                        "\t\"PlayerID\", \"PlayerName\", \"PlayerSurname\", \"CountryID\", \"CourtID\", \"CoachID\", \"Age\", \"Points\", \"ForehandID\", \"BackhandID\")\n" +
                        "\tVALUES" + finalValues);
                if(inserted == 1 ){
                    System.out.println("You added player correctly :)");
                }
                else{
                    System.out.println("Unfortunately , please once again.");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    private int checkPlayerID(){
        int countPlayers = 0;
        open();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT (*) FROM \"Player\"");
            while (resultSet.next()) {
                countPlayers += Integer.parseInt(resultSet.getString("count"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return countPlayers;
    }

    private boolean checkIfExistsInDatabase() {
        open();
        System.out.println("Which player do you want to add?");
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

            ResultSet rs = statement.executeQuery("SELECT COUNT(*)  FROM \"Player\" WHERE \"PlayerName\" =   " + String.format("'" + name + "'") + "AND  \"PlayerSurname\" = " + String.format("'" + surname + "'") );
            if(rs.next()) {
                //assign a variable to query and check if coach exists or not in DB
                int resultOfQuery = Integer.parseInt(rs.getString("count"));
                if(resultOfQuery == 0){
                    System.out.println("The player doesnt exist in database, so you can add to a database ");
                    return true;
                }
                else{
                    System.out.println("The player exist in database, you cant add to a database");
                    return false;
                }
            }
            return  false;
        } catch (SQLException e) {

            System.out.println(e.getMessage());
            return false;
        }
    }


}
