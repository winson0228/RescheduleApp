package com.example.winso.scheduleapp2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.winso.scheduleapp2.SingleViewerActivity.user;

public class AddAptActivity extends AppCompatActivity {

    private String[] amPM= new String[] {"AM", "PM"};
    private String[] hours = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private String[] minutes = new String[] {"00", "10", "15", "30", "45"};
    private String startHour;
    private String startMin;
    private String startAmPm;
    private String endHour;
    private String endMin;
    private String endAmPm;
    private String startTime;
    private String endTime;
    private EditText titleInput;
    private EditText dspInput;
    private TextView displayStartTime;
    private TextView displayEndTime;
    private TextView display_date;
    private String dateString;
    private Button openCal;
    private long prevDate = 0;
    private Button finishedBtn;
    private Spinner startHourSpinner;
    private Spinner startMinSpinner;
    private Spinner startAmPmSpinner;
    private Spinner endHourSpinner;
    private Spinner endMinSpinner;
    private Spinner endAmPmSpinner;
    private static final String EVENT_DATE = "event_date";
    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apt);
        display_date = (TextView) findViewById(R.id.display_date);
        displayStartTime = (TextView) findViewById(R.id.timeStart);
        displayEndTime = (TextView) findViewById(R.id.tvTimeEnd);
        titleInput = (EditText) findViewById(R.id.titleInput);
        dspInput = (EditText) findViewById(R.id.dspInput);
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/DD/yyyy");
        //Current time
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
        Calendar calendar = new GregorianCalendar(pdt);
        Date trialTime = new Date();
        calendar.setTime(trialTime);

        //Begin at today's date
        dateString = Integer.toString(calendar.get(Calendar.MONTH) +1 ) + "/" + Integer.toString(calendar.get(Calendar.DATE)) + "/" + Integer.toString(calendar.get(Calendar.YEAR));
        display_date.setText(dateString);


        //Calendar Dialog
        openCal = (Button) findViewById(R.id.openCalBtn);

        openCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder cal_builder = new AlertDialog.Builder(AddAptActivity.this);
                View cal_view = getLayoutInflater().inflate(R.layout.activity_calendar_dialog, null);
                mCalendarView = (CalendarView) cal_view.findViewById(R.id.calendarView);
                if (prevDate != 0) {
                    mCalendarView.setDate(prevDate);
                }
                cal_builder.setView(cal_view);
                final AlertDialog cal_dialog = cal_builder.create();
                cal_dialog.show();
                mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                        prevDate = mCalendarView.getDate();
                        dateString = (i1+1) + "/" + i2 + "/" + i;
                        display_date.setText(dateString);
                        Intent dateIntent = new Intent(AddAptActivity.this, SingleViewerActivity.class);
                        dateIntent.putExtra(EVENT_DATE, dateString);
                        System.out.println("Calendar date changed to: " + dateString);
                        cal_dialog.dismiss();
                    }
                });

            }
        });

        //Spinners
        startHourSpinner = (Spinner) findViewById(R.id.startHourSpinner);
        ArrayAdapter<String> startHourAdapter = new ArrayAdapter<String>(AddAptActivity.this, android.R.layout.simple_spinner_dropdown_item, hours);
        //startHourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startHourSpinner.setAdapter(startHourAdapter);

        startHourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startHour = (String) adapterView.getItemAtPosition(i);
                updateStartTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startMinSpinner = (Spinner) findViewById(R.id.startMinSpinner);
        ArrayAdapter<String> startMinAdapter = new ArrayAdapter<String>(AddAptActivity.this, android.R.layout.simple_spinner_dropdown_item, minutes);
        startMinSpinner.setAdapter(startMinAdapter);

        startMinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startMin = (String) adapterView.getItemAtPosition(i);
                updateStartTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        startAmPmSpinner = (Spinner) findViewById(R.id.startAmPmSpinner);
        ArrayAdapter<String> startAmPmAdapter = new ArrayAdapter<String>(AddAptActivity.this, android.R.layout.simple_spinner_dropdown_item, amPM);
        startAmPmSpinner.setAdapter(startAmPmAdapter);
        startAmPmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                startAmPm = (String) adapterView.getItemAtPosition(i);
                updateStartTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        endHourSpinner = (Spinner) findViewById(R.id.endHourSpinner);
        ArrayAdapter<String> endHourAdapter = new ArrayAdapter<String>(AddAptActivity.this, android.R.layout.simple_spinner_dropdown_item, hours);
        endHourSpinner.setAdapter(endHourAdapter);
        endHourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                endHour = (String) adapterView.getItemAtPosition(i);
                updateEndTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        endMinSpinner = (Spinner) findViewById(R.id.endMinSpinner);
        ArrayAdapter<String> endMinAdapter = new ArrayAdapter<String>(AddAptActivity.this, android.R.layout.simple_spinner_dropdown_item, minutes);
        endMinSpinner.setAdapter(endMinAdapter);
        endMinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                endMin = (String) adapterView.getItemAtPosition(i);
                updateEndTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        endAmPmSpinner = (Spinner) findViewById(R.id.endAmPmSpinner);
        ArrayAdapter<String> endAmPmAdapter = new ArrayAdapter<String>(AddAptActivity.this, android.R.layout.simple_spinner_dropdown_item, amPM);
        endAmPmSpinner.setAdapter(endAmPmAdapter);
        endAmPmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                endAmPm = (String) adapterView.getItemAtPosition(i);
                updateEndTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        finishedBtn = (Button) findViewById(R.id.finished_adding_btn);
        finishedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startHour24 = Integer.parseInt(startHour);
                int endHour24 = Integer.parseInt(endHour);
                if (startAmPm.equals("PM")) {
                    startHour = Integer.toString(startHour24 += 12);
                }
                if (endAmPm.equals("PM")) {
                    endHour = Integer.toString(endHour24 += 12);
                }
                String timeStart24 = startHour.concat(startMin);
                String timeEnd24 = endHour.concat(endMin);
                String aptTitle = titleInput.getText().toString();
                String aptDsp = dspInput.getText().toString();
                if (aptTitle.equals("")) {
                    aptTitle = "Untitled Event";
                }
                if (aptDsp.equals("")) {
                    aptDsp = "Empty Information";
                }

                if (startAmPm.equals("PM") && endAmPm.equals("AM")) {
                    System.out.println("Error");
                }
                if ((startAmPm.equals("AM") && endAmPm.equals("AM")) || (startAmPm.equals("PM") && endAmPm.equals("PM"))) {
                    if ((startHour24 > endHour24)) {
                        System.out.println("Error");
                    }
                    if (startHour24 == endHour24) {
                        if (Integer.parseInt(startMin) >= Integer.parseInt(endMin)) {
                            System.out.println("Error");
                        }
                    }
                }
                Appointment new_apt = new Appointment(aptTitle, Integer.parseInt(timeStart24), Integer.parseInt(timeEnd24), dateString.replaceAll("\\D+",""), aptDsp);
                System.out.println("regular exp: " + dateString.replaceAll("\\D+",""));
                user.addApt(new_apt);
                finish();
                //Intent gotoMain = new Intent(AddAptActivity.this, SingleViewerActivity.class);
                //startActivity(gotoMain);
            }
        });
    }

    private void updateStartTime() {
        displayStartTime.setText(startHour + ":" + startMin + " " + startAmPm);
    }

    private void updateEndTime() {
        displayEndTime.setText(endHour + ":" + endMin + " " + endAmPm);
    }

}
