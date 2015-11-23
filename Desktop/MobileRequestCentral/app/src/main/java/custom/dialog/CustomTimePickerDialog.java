package custom.dialog;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import testing.MyInterface;

/**
 * Created by amitk on 9/7/2015.
 */
public class CustomTimePickerDialog extends TimePickerDialog implements MyInterface {

    public static int TIME_PICKER_INTERVAL = 30;
    static long diffMonths, diffDays;
    private final OnTimeSetListener callback;
    MyInterface myInterface;
    TextView textView;
    int hour, min;
    Context context;
    TextView plannedEndTextView;
    TextView startDateTextView;
    TextView endDateTextView;
    long diffHours, diffMinutes;
    String startstrForDate, endstrForDate, startdateandTimeString;
    private TimePicker timePicker;

    public CustomTimePickerDialog(Context context, OnTimeSetListener callBack,
                                  int hourOfDay, int minute, boolean is24HourView, TextView timetextView, TextView startDateTextView, TextView endDateTextView, TextView plannedEndTextView, String startdateandTimeString/*, long diffDays, long diffHours, long diffMinutes*/) {
        super(context, TimePickerDialog.THEME_HOLO_LIGHT, callBack, hourOfDay, minute / TIME_PICKER_INTERVAL,
                is24HourView);
        myInterface = this;
        this.hour = hourOfDay;
        this.min = minute / TIME_PICKER_INTERVAL;
        this.callback = callBack;
        this.textView = timetextView;
        this.context = context;
        this.plannedEndTextView = plannedEndTextView;
        this.startDateTextView = startDateTextView;
        this.endDateTextView = endDateTextView;
        this.startdateandTimeString = startdateandTimeString;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        diffMonths = Math.abs(b.get(Calendar.MONTH) - a.get(Calendar.MONTH));
        diffDays = Math.abs(b.get(Calendar.DAY_OF_MONTH) - a.get(Calendar.DAY_OF_MONTH));
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {

            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (callback != null && timePicker != null) {
            timePicker.clearFocus();
            callback.onTimeSet(timePicker, timePicker.getCurrentHour(),
                    timePicker.getCurrentMinute() * TIME_PICKER_INTERVAL);


            if (textView.getTag() != null) {
                if (textView.getTag().equals("starttimetextView")) {
                    textView.setText(String.format("%02d", timePicker.getCurrentHour()) + ":" + String.format("%02d", timePicker.getCurrentMinute() * TIME_PICKER_INTERVAL));
                    /*startstrForDate = startDateTextView.getText().toString()+" "+textView.getText().toString();*/
                }
            }

            if (textView.getTag() != null) {
                if (textView.getTag().equals("textView")) {
                    textView.setText(String.format("%02d", timePicker.getCurrentHour()) + ":" + String.format("%02d", timePicker.getCurrentMinute() * TIME_PICKER_INTERVAL));
                    endstrForDate = endDateTextView.getText().toString() + " " + textView.getText().toString();
                    myInterface.ifFilled();

                }

            }


        }
    }

    @Override
    protected void onStop() {
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field timePickerField = classForid.getField("timePicker");
            this.timePicker = (TimePicker) findViewById(timePickerField
                    .getInt(null));
            Field field = classForid.getField("minute");

            NumberPicker mMinuteSpinner = (NumberPicker) timePicker
                    .findViewById(field.getInt(null));
            mMinuteSpinner.setMinValue(0);
            mMinuteSpinner.setMaxValue((60 / TIME_PICKER_INTERVAL) - 1);
            List<String> displayedValues = new ArrayList<String>();
            for (int i = 0; i < 60; i += TIME_PICKER_INTERVAL) {
                displayedValues.add(String.format("%02d", i));
            }
            mMinuteSpinner.setDisplayedValues(displayedValues
                    .toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean ifFilled() {
        System.out.println("set");

        if (plannedEndTextView != null) {

            Date startdate = new Date(startdateandTimeString);
            System.out.println(startdate);

            Date enddate = new Date(endstrForDate);
            System.out.println(enddate);


            if (enddate.before(startdate)) {
                Toast.makeText(context, "End date should be greater than start date!", Toast.LENGTH_SHORT).show();
                plannedEndTextView.setText("");
            } else {

                System.out.println(getDiffYears(startdate, enddate));

                long l = enddate.getTime() - startdate.getTime();

                diffMinutes = l / (60 * 1000) % 60;
                diffHours = l / (60 * 60 * 1000) % 24;

                String plannedDurationString = "";
                String years, months, days, hours, minutes;
                int year = getDiffYears(startdate, enddate);

                years = (year > 0) ? (year == 1) ? year + " year  " : (year > 1) ? year + " years  " : "" : "";
                months = (diffMonths > 0) ? (diffMonths == 1) ? diffMonths + " month  " : (diffMonths > 1) ? diffMonths + " months  " : "" : "";
                days = (diffDays > 0) ? (diffDays == 1) ? diffDays + " day  " : (diffDays > 1) ? diffDays + " days  " : "" : "";
                hours = (diffHours > 0) ? (diffHours == 1) ? diffHours + " hour  " : (diffHours > 1) ? diffHours + " hours  " : "" : "";
                minutes = (diffMinutes > 0) ? (diffMinutes == 1) ? diffMinutes + " minute  " : (diffMinutes > 1) ? diffMinutes + " minutes  " : "" : "";

                /*years = (year > 0)? year+" years, " : "";
                months = (diffMonths >0)? diffMonths+" months, ": "";
                days = (diffDays > 0)? diffDays+" days, ": "";
                hours = (diffHours > 0)?diffHours+" hours, ": "";
                minutes = (diffMinutes> 0)? diffMinutes+ " minutes":"";*/

                plannedDurationString = years + months + days + hours + minutes;

                plannedEndTextView.setText(plannedDurationString);

            }

        }
        return true;
    }
}