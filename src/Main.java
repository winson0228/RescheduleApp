import java.util.Scanner;

/**
 * Created by winson on 2017-06-07.
 */
public class Main {


    public static void main(String[] args) {
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



        Scanner scan2 = new Scanner(System.in);
        if (scan2.nextLine().equals("new apt")) {
            Appointment apt1;
            System.out.println("Enter the date of this appointment in this format, ddmmyyyy");
            String date1 = Integer.toString(scan2.nextInt());
            System.out.println("Enter the start time of this appointment");
            int startTime1 = scan2.nextInt();
            System.out.println("Enter the end time of this appointment");
            int endTime1 = scan2.nextInt();
            System.out.println("Will this appointment be overlapped with your other appointments?");
            boolean isStacked1 = scan2.nextBoolean();
            System.out.println("How much should this appointment be prioritized over other appointments?");
            int isPriority1 = scan2.nextInt();

            apt1 = new Appointment(startTime1, endTime1, date1, "none", "canada", false,
                    isStacked1, isPriority1, "a", Appointment.Occasions.Family);
        }

        if(scan2.nextLine().equals("schedule")) {
            user.displaySchedule();
        }
    }
}
