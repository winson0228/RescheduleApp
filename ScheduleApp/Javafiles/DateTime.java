package com.example.winso.scheduleapp2;

import java.util.*;

/**
 * Created by winso on 6/23/2017.
 */
public final class DateTime {

    private static DateTime today = new DateTime();
    private static String todayString;
    public static Calendar calendar;

    private DateTime() {
        // get the supported ids for GMT-08:00 (Pacific Standard Time)
        String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
        // if no ids were returned, something is wrong. get out.
        if (ids.length == 0)
            System.exit(0);

        // create a Pacific Standard Time time zone
        SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

        // set up rules for Daylight Saving Time
        pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

        // create a GregorianCalendar with the Pacific Daylight time zone
        // and the current date and time
        calendar = new GregorianCalendar(pdt);
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        todayString = Integer.toString(calendar.get(Calendar.MONTH) +1 ) + Integer.toString(calendar.get(Calendar.DATE)) + Integer.toString(calendar.get(Calendar.YEAR));
    }

    public static Calendar getCalendar() {
        return calendar;
    }
}
