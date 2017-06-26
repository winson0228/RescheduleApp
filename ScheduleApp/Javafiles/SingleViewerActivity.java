package com.example.winso.scheduleapp2;

import android.content.Intent;
import android.icu.util.Calendar;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.*;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Locale;

public class SingleViewerActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SwipePagerAdapter mSwipePagerAdapter;
    private TextView testDateTV;
    private TextView todayTV;
    private static final String EVENT_DATE = "event_date";
    public static String newline = System.getProperty("line.separator");

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static final User user = new User("user1");
    public static final String today = "6242017";
    private java.util.Calendar calendar = DateTime.getCalendar();
    private String newEventDate = "";
    private String displayDate;
    private static LinkedList<Appointment> eventsToday = user.getLApt(today);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_viewer);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSwipePagerAdapter = new SwipePagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.single_view_pager);
        mViewPager.setAdapter(mSwipePagerAdapter);


        Intent incomingIntent = getIntent();
        newEventDate = incomingIntent.getStringExtra(EVENT_DATE);
        //testDateTV = (TextView) findViewById(R.id.testIntent);
        //testDateTV.setText(newEventDate);

        todayTV = (TextView) findViewById(R.id.today_date);

        todayTV.setText(calendar.getDisplayName(Calendar.MONTH, java.util.Calendar.LONG,  Locale.getDefault()) + " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(java.util.Calendar.YEAR));
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent add_apt_activity = new Intent(SingleViewerActivity.this, AddAptActivity.class);
                startActivity(add_apt_activity);
            }
        });

        //update List
        updateAptList();

        System.out.println(user.getLApt(today));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAptList();
    }
    private void updateAptList() {
        user.getLApt(today);
        mSwipePagerAdapter.notifyDataSetChanged();
        //System.out.println(user.getLApt(today).size());
        System.out.println(today);
        System.out.println("Updated today's calendar");
    }


    /**
     * A Swipible fragment containing a simple view.
     */
    public static class SwipibleFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_EVENT_NUMBER = "event_number";
        //private TextView emptyAptTV;
        private TextView event_num;
        private TextView event_info;
        private TextView eventTitleTV;
        private TextView eventTimeTV;
        //dont use static??
        public static Appointment apt;
        public static LinkedList<Appointment> lAptFrag = user.getLApt(today);

        public SwipibleFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static SwipibleFragment newInstance(int eventNumber) {
            SwipibleFragment fragment = new SwipibleFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_EVENT_NUMBER, eventNumber);
            fragment.setArguments(args);
            //lApt = user.getLApt(today);
            if(lAptFrag != null) {
                System.out.println("lApt is not null");
            }
            System.out.println("event number (newInstance) " + eventNumber);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (savedInstanceState == null) {
                // During initial setup, plug in the details fragment.
                lAptFrag = user.getLApt(today);
            }
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View swiper_layout_view = inflater.inflate(R.layout.fragment_swipible_layout, container, false);
            event_num = (TextView) swiper_layout_view.findViewById(R.id.tv_event_num);
            event_info = (TextView) swiper_layout_view.findViewById(R.id.event_dsp);
            eventTitleTV = (TextView) swiper_layout_view.findViewById(R.id.event_title);
            eventTimeTV = (TextView) swiper_layout_view.findViewById(R.id.event_time);
            //emptyAptTV = (TextView) swiper_layout_view.findViewById(R.id.noapt_TV);
            Bundle bundle = getArguments();
            String message = Integer.toString(bundle.getInt(ARG_EVENT_NUMBER));
            int eventNumber = bundle.getInt(ARG_EVENT_NUMBER);
            System.out.println(lAptFrag);
            System.out.println("event number " + eventNumber);
            eventTitleTV.setText("No events.");
            if (lAptFrag != null) {
                //emptyAptTV.setText("");
                apt = lAptFrag.get(eventNumber);
                eventTitleTV.setText(apt.getTitle());
                event_num.setText("Event Number: " + bundle.getInt(ARG_EVENT_NUMBER));
                eventTimeTV.setText(formatTime(Integer.toString(apt.getTimeStart())) + " - " + formatTime(Integer.toString(apt.getTimeEnd())));
                event_info.setText(apt.getDsp());
            }

            return swiper_layout_view;
        }

        public String formatTime(String time) {
            if (time.length() == 3) {
                return "0" + time.substring(0, 1) + ":" + time.substring(1);
            } else  {
                return time.substring(0, 2) + ":" + time.substring(2);
            }
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SwipePagerAdapter extends FragmentStatePagerAdapter {

        public SwipePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a SwipibleFragment (defined as a static inner class below).
            if (SwipibleFragment.lAptFrag != null) {
               SwipibleFragment.apt = SwipibleFragment.lAptFrag.get(position);
               System.out.println("getItem Position = " + position);
            }
            return SwipibleFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            if (user.getLApt(today) != null) {
                return user.getLApt(today).size();
            } else {
                return 0;
            }

        }
/*
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return (view ==  o);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
        /*
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return null;
        }
        */

    }
}
