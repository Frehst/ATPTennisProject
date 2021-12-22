package com.company.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Country extends DatabaseConnection {

    private int CountryID;
    private String CountryName;

    private static final String QUERY = "SELECT \"CountryID\", \"CountryName\" FROM \"Country\"";

    public int getCountryID() {
        return CountryID;
    }

    public void setCountryID(int countryID) {
        CountryID = countryID;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }


    public void ALlCountries() {
        open();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY);
            while (resultSet.next()) {
                // Retrieve by column name
                System.out.println(resultSet.getString("CountryID") + " " + resultSet.getString("CountryName"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void AddCountry() {
        open();

        if (checkIfExistsInDatabase()) {
            System.out.println("Please write again country.");
            Scanner scanner = new Scanner(System.in);
            String country = scanner.next();

            int iD = checkCountryID() + 1;
            country = String.format("'" +country + "'");
            String finalValues = String.format("(" + iD + "," + country + ")");
            try (Statement statement = conn.createStatement()) {
                int inserted = statement.executeUpdate("INSERT  INTO \"Country\"(\"CountryID\",\"CountryName\") Values " + finalValues);
                if(inserted == 1 ){
                    System.out.println("You added country correctly :)");
                }
                else{
                    System.out.println("Unfortunately , please once again.");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    private boolean checkIfExistsInDatabase() {
        open();
        System.out.println("Which country do you want to add?");
        Scanner country = new Scanner(System.in);

        while (country.hasNextInt()) {
            System.out.println("string, please!");
            country.nextLine();
        }
        country.nextLine();


        try (Statement statement = conn.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT COUNT (*) FROM \"Country\" WHERE \"CountryName\" =  " + String.format("'" + country + "'"));
            if(resultSet.next()) {
                //assign a variable to query and check if country exists or not in DB
                int resultOfQuery = Integer.parseInt(resultSet.getString("count"));
                if(resultOfQuery == 0){
                    System.out.println("The country doesnt exist in database, so you can add to a database ");
                    return true;
                }
                else{
                    System.out.println("The country exist in database, you cant add to a database");
                    return false;
                }
            }
            return  false;
        } catch (SQLException e) {

                System.out.println(e.getMessage());
            return false;
        }
    }

    private int checkCountryID(){
        int countCountry = 0;
        open();
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT (*) FROM \"Country\"");
            while (resultSet.next()) {
                countCountry += Integer.parseInt(resultSet.getString("count"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return countCountry;
    }
}
