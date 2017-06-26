package com.example.winso.scheduleapp2;

import java.util.Scanner;

/**
 * Created by winson on 2017-06-07.
 */
public class Main {


    public static void main(String[] args) {
        String title = "";
        Scanner scan = new Scanner(System.in);
        System.out.println("Type in your username: ");
        String username = scan.nextLine();
        System.out.println("Your username is " + username);
        User user = new User(username);
        System.out.println("When are you usually available in your work days? Enter in 24 hour format");
        System.out.println("Start of your availability");
        int availStart = scan.nextInt();
        System.out.println("End of your availability");
        int availEnd = scan.nextInt();
        user.setAvailibility(availStart, availEnd);

        System.out.println("Overview: ");
        System.out.println("Your username is " + username);
        System.out.println("Your username availability is " + availStart + " to " + availEnd);


        //Continuous
        Scanner scan2 = new Scanner(System.in);
        System.out.println("Type in anything to start planning. Type in exit to exit planning.");
        String in = "";
        while(!in.equals("exit")) {
            //Scanner scan3 = new Scanner(System.in);
            in = scan.nextLine();

            if (scan.nextLine().equals("new apt")) {
                Appointment apt1;
                System.out.println("Enter the date of this appointment in this format, ddmmyyyy");
                String date1 = scan.nextLine();
                System.out.println("Enter the start time of this appointment in this format, hhmm");
                int startTime1 = convertTime(scan.nextLine());
                System.out.println("Enter the end time of this appointment in this format, hhmm");
                int endTime1 = convertTime(scan.nextLine());
                System.out.println("Will this appointment be overlapped with your other appointments?");
                boolean isStacked1 = scan.nextBoolean();
                System.out.println("How much should this appointment be prioritized over other appointments?");
                int isPriority1 = scan.nextInt();

                apt1 = new Appointment(title, startTime1, endTime1, date1, "none", "canada", false,
                        isStacked1, isPriority1, "a", Appointment.Occasions.Family);
                user.addApt(apt1);
                //user.displaySchedule(date1);
            }

            if (scan.nextLine().equals("schedule")) {
                System.out.println("Which day do you want to see? Enter in this format: ddmmyyyy");
                String dayToSee = scan.nextLine();
                user.displaySchedule(dayToSee);
            }
        }
    }

    private static int convertTime(String time) {
        //When input contains am or pm
        //Time needs to have 4 digits for it to be in hhmm format
        if(time.contains("am")) {
            int intTime = Integer.parseInt(time.replaceAll("\\D+",""));
            if(intTime > 1200){ //change
                throw new NumberFormatException("The time cannot be greater than 12am if you are entering the time using the 12 hour format. Alternatively, you can enter using the 24 hour format.");
            } else {
                return intTime;
            }
        }
        if(time.contains("pm")) {
            int intTime = Integer.parseInt(time.replaceAll("\\D+",""));
            if(intTime > 1200){
                throw new NumberFormatException("The time cannot be greater than 12pm if you are entering the time using the 12 hour format. Alternatively, you can enter using the 24 hour format.");
            }
            return intTime + 1200;
        }
        //When input is just number

        int intTime = Integer.parseInt(time.replaceAll("\\D+",""));
        if(intTime > 2400) {
            throw new NumberFormatException("The time cannot be greater than 24 if you are entering the time using the 24 hour format. Alternatively, you can enter using the 12 hour format and adding 'pm' after it.");
        } else {
            return intTime;
        }
    }
}
