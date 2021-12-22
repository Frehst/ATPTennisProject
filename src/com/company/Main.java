package com.company;

import com.company.Model.Backhand;
import com.company.Model.Coach;
import com.company.Model.Country;
import com.company.Model.Player;

public class Main {

    static final String url = "jdbc:postgresql://localhost/ATPRank";
    static final String user = "postgres";
    static final String password = "Mojapupa21";
    static final String QUERY = "SELECT \"CoachName\", \"CoachSurname\" From public.\"Coach\"";

    public static void main(String[] args)  {

        Player player = new Player();
        Country country = new Country();
        Coach coach = new Coach();

        //player.searchPlayer();
        //coach.deleteCoach();
        //coach.AllCoaches();
        coach.deleteCoach();

    }


}
