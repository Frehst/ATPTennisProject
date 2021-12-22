package com.company.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final String url = "jdbc:postgresql://localhost/ATPRank";
    private final String user = "postgres";
    private final String password = "Mojapupa21";

    public Connection conn;

    public boolean open(){
        try {
            conn = DriverManager.getConnection(url,user,password);
            return true;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
