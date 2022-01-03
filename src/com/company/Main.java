package com.company;

import com.company.Model.Backhand;
import com.company.Model.Coach;
import com.company.Model.Country;
import com.company.Model.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {

        menu();


    }
    public static void menu(){
        Player player = new Player();
        Country country = new Country();
        Coach coach = new Coach();

        boolean decision = true;

        while(decision){

            System.out.println("What do you want to do?\n0-quit\n1-add coach\n2- see all coaches\n3-delete coach\n4-add country" +
                    "\n5-see all countries");
            Scanner option = new Scanner(System.in);


            switch (option.nextInt()) {
                case 0 -> decision = false;
                case 1 -> coach.AddCoach();
                case 2 -> coach.AllCoaches();
                case 3 -> coach.deleteCoach();
                case 4-> country.AddCountry();
                case 5 -> country.ALlCountries();

            }

            if(!decision)
                System.out.println("Thank you for using.");
        }



    }
}
