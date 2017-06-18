package com.example.winso.scheduleapp;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by winson on 2017-05-22.
 */
public class User {

    private HashMap<String, LinkedList<Appointment>> calendar = new HashMap<String, LinkedList<Appointment>>();
    private String username;
    private int availStart;
    private int availEnd;



    public User(String username) {
        this.username = username;
    }

    public void addApt(Appointment apt) {
        String date = apt.getDate();
        if (calendar.containsKey(date)) {
            calendar.get(date).add(apt);
        } else {
            calendar.put(date, new LinkedList<Appointment>());
            calendar.get(date).add(apt);
        }
        //calendar.get(date).sort(appointments);
        Collections.sort(calendar.get(date));
    }

    public void removeApt(Appointment apt) {
        String date = apt.getDate();
        calendar.get(date).remove(apt);
    }

    //Display schedule for today (only this for now)
    public void displaySchedule(String date) {
        if (calendar.get(date).size() > 0) {
            LinkedList<Appointment> day = calendar.get(date);
            for (Appointment apt : day) {
                apt.displayInfo();
            }
        } else {
            System.out.println("No appointments for that day");
        }
    }

    //getters

    public String getusername() {
        return this.username;
    }

    public LinkedList<Appointment> getLApt(String date) {
        return calendar.get(date);
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
    public Appointment findFirstOccupied(String day) {
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

    //find appointment occupying a time in a day
    public Appointment findOccupied(String day, int time) {
        LinkedList<Appointment> lapt = calendar.get(day);
        for(Appointment apt : lapt) {
            if(apt.getTimeStart() >= time && apt.getTimeEnd() <= time) {
                return apt;
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
