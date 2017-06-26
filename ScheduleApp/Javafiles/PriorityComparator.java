package com.example.winso.scheduleapp2;

import java.util.Comparator;

/**
 * Created by winson on 2017-05-20.
 */
public class PriorityComparator implements Comparator<Appointment> {

    @Override
    public int compare(Appointment apt1, Appointment apt2) {
        return apt1.getPriority() - apt2.getPriority();
    }
}
