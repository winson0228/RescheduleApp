import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by winson on 2017-05-20.
 */
public class Day {

    private int month;
    private int day;
    private String dayweek;
    private LinkedList<Appointment> appointments;

    public Day(int month, int day, String dayweek) {
        this.month = month;
        this.day = day;
        this.dayweek = dayweek;
    }

    public void addApt(Appointment apt) {
        appointments.add(apt);
        Collections.sort(appointments);
    }

    public void removeApt(Appointment apt) {
        appointments.remove(apt);
    }

    //getters
    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }

    public String getDayweek() {
        return this.dayweek;
    }

    public boolean isOccupied(int time) {
        if (appointments != null) {
            int i = 0;
            Appointment curr = appointments.get(0);
            int size = appointments.size();

            while ((time >= curr.getTimeStart()) && i < size) {
                //find the closest appointment to the given time
                i++;
                curr = appointments.get(i);
            }
            curr = appointments.get(i+1); //advance it to the next
            if ((time >= curr.getTimeStart()) || (time <= curr.getTimeEnd())) {
                //tests if the time is within the appointment
                return true;
                //needs testing
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    //Find the first occupied appointment in the day, needs revision
    public Appointment findOccupied() {
        // Assume that the linked list is sorted
        //Iterator<Appointment> apt_iterator= appointments.iterator();
        Appointment prev = appointments.get(0);
        int aptsize = appointments.size();
        for (int i = 1; i < aptsize; i++) {
            Appointment curr = appointments.get(i);
            if (prev.getTimeEnd() < curr.getTimeStart()) {
                prev = curr;
            } else {
                return curr;
            }
        }
        return null;
    }

    //sort by priorities of each appointment in a day
    public void sortPriority() {
        //Comparator class?
        //http://stackoverflow.com/questions/16425127/how-to-use-collections-sort-in-java-specific-situation
        //if switching to Java 8 http://stackoverflow.com/questions/6247233/java-how-to-sort-a-linked-list
        Collections.sort(appointments, new PriorityComparator());
    }

}
