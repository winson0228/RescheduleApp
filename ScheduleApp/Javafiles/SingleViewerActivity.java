package com.example.winso.scheduleapp;

import android.content.Intent;
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

import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.LinkedList;

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
    private TextView testDate;
    private static final String EVENT_DATE = "event_date";
    public static String newline = System.getProperty("line.separator");

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static final User user = new User("user1");
    private static String today = "06172017";
    private String newEventDate = "";
    private final LinkedList<Appointment> eventsToday = user.getLApt(today);

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
        testDate = (TextView) findViewById(R.id.testIntent);
        testDate.setText(newEventDate);

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

    private void updateAptList() {
        user.getLApt(today);
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
        TextView event_num;
        TextView event_info;
        //dont use static??
        private static Appointment apt;
        private static LinkedList<Appointment> lApt = user.getLApt(today);

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
            if(lApt != null) {
                apt = lApt.get(eventNumber - 1);
            }
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View swiper_layout_view = inflater.inflate(R.layout.fragment_swipible_layout, container, false);
            event_num = (TextView) swiper_layout_view.findViewById(R.id.tv_event_num);
            event_info = (TextView) swiper_layout_view.findViewById(R.id.event_info);
            Bundle bundle = getArguments();
            String message = Integer.toString(bundle.getInt(ARG_EVENT_NUMBER));
            if (apt != null) {
                event_info.append(apt.getTimeStart() + " - " + apt.getTimeEnd() + newline);
            } else {
                event_num.setText("Event Number: " + message);
                event_info.setText("Test " + message);
            }
            return swiper_layout_view;
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
            return SwipibleFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            if (user.getLApt(today) != null) {
                return user.getLApt(today).size();
            } else {
                return 1;
            }

        }

        /*
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return null;
        }
        */

    }
}
