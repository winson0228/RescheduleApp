import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by winson on 2017-05-22.
 */
public class User {

    private HashMap<String, LinkedList<Appointment>> calendar = new HashMap<String, LinkedList<Appointment>>();
    private String name;
    private int availStart;
    private int availEnd;



    public User(String name) {
        this.name = name;
    }

    public void addApt(Appointment apt) {
        String time = apt.getTime();
        if (calendar.containsKey(time)) {
            calendar.get(time).add(apt);
        } else {
            calendar.put(time, new LinkedList<Appointment>());
            calendar.get(time).add(apt);
        }
        //calendar.get(time).sort(appointments);
    }

    public void removeApt(Appointment apt) {
        String time = apt.getTime();
        calendar.get(time).remove(apt);
    }

    //getters

    public String getName() {
        return this.name;
    }

    public void setAvailibility(int time1, int time2) {
        this.availStart = time1;
        this.availEnd = time2;
    }
    public boolean isOccupied(String day, int time) {
        LinkedList<Appointment> getDay = calendar.get(day);
        if (calendar.get(day) != null) {
            int i = 0;
            Appointment curr = getDay.get(0);
            int size = getDay.size();

            while ((time >= curr.getTimeStart()) && i < size) {
                //find the closest appointment to the given time
                i++;
                curr = getDay.get(i);
            }
            curr = getDay.get(i+1); //advance it to the next
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
    public Appointment findOccupied(String day) {
        // Assume that the linked list is sorted
        //Iterator<Appointment> apt_iterator= appointments.iterator();
        LinkedList<Appointment> getDay = calendar.get(day);
        Appointment prev = getDay.get(0);
        int aptsize = getDay.size();
        for (int i = 1; i < aptsize; i++) {
            Appointment curr = getDay.get(i);
            if (prev.getTimeEnd() < curr.getTimeStart()) {
                prev = curr;
            } else {
                return curr;
            }
        }
        return null;
    }

    //sort by priorities of each appointment in a day
    public void sortPriority(String date) {
        //Comparator class?
        //http://stackoverflow.com/questions/16425127/how-to-use-collections-sort-in-java-specific-situation
        //if switching to Java 8 http://stackoverflow.com/questions/6247233/java-how-to-sort-a-linked-list

        Collections.sort(calendar.get(date), new PriorityComparator());
    }



}
