package com.softeksol.paisalo.jlgsourcing.Utilities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sachindra on 2016-10-21.
 */
public class DateUtils {
    static String[] formatStrings = {"yyyy-MM-dd", "dd-MM-yyyy", "dd/MM/yyyy"};
    public static String[] formatdateTimeStrings = {"yyyy-MM-dd'T'HH:mm:ss.SSS"};

    public static String getDobFrmAge(int age) {
        Calendar calendar = Calendar.getInstance();
        return "01-Jan-" + String.valueOf(calendar.get(Calendar.YEAR) - age);
        //return "01-Jul-"+String.valueOf(calendar.get(Calendar.YEAR)-age);
    }

    /*public static  Date getDobFrmAge(int age,Date dateAsOn){
        Calendar calendar=Calendar.getInstance();
        return "01-Jul-"+String.valueOf(calendar.get(Calendar.YEAR)-age);
    }*/

    public static int getAge(Date DateOfBirth) {
        Calendar cal_dob = Calendar.getInstance();
        cal_dob.setTime(DateOfBirth);
        return getAge(cal_dob);
    }

    public static int getAge(Date DateOfBirth, Date AsOnDate) {
        Calendar cal_dob = Calendar.getInstance();
        Calendar cal_ason = Calendar.getInstance();
        cal_dob.setTime(DateOfBirth);
        cal_ason.setTime(AsOnDate);
        return getAge(cal_dob, cal_ason);
    }

    public static int getAge(Calendar DateOfBirth) {
        Calendar today = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        int yourAge = today.get(Calendar.YEAR) - DateOfBirth.get(Calendar.YEAR);
        dob.setTime(DateOfBirth.getTime());
        dob.add(Calendar.YEAR, yourAge);
        if (today.before(dob)) {
            yourAge--;
        }
        return yourAge;
    }

    public static int getAge(Calendar DateOfBirth, Calendar AsOnDate) {
        Calendar dob = Calendar.getInstance();
        int yourAge = AsOnDate.get(Calendar.YEAR) - DateOfBirth.get(Calendar.YEAR);
        dob.setTime(DateOfBirth.getTime());
        dob.add(Calendar.YEAR, yourAge);
        if (AsOnDate.before(dob)) {
            yourAge--;
        }
        return yourAge;
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDate(long milliSeconds) {
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return calendar.getTime();
    }

    public static String getFormatedDate(String dbDate) {
        if (dbDate.equals(null) || dbDate.equals(""))
            return "";
        else
            return dbDate.substring(8, 10) + "-" + dbDate.substring(5, 7) + "-"
                    + dbDate.substring(0, 4);
    }

    public static String getFormatedDate(Date date, String formatSting) {
        if (date == null)
            return "01-Jul-1996";
        else
            return (new SimpleDateFormat(formatSting)).format(date);
    }

    public static Date getParsedDate(String dateString) {
        for (String formatString : formatStrings) {
            try {
                Date dt = new SimpleDateFormat(formatString).parse(dateString);
                if (dt.getClass().getSimpleName().equals("Date")) if (getAge(dt) < 150) return dt;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Date getParsedDate(String dateString, String formatString) {
        Date retDate = null;
        if (!(dateString == null || dateString.equals("")))
            //Log.d("DateString",dateString);
            try {
                retDate = new SimpleDateFormat(formatString).parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return retDate;
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        //public DatePickerFragment ()

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }

    }

    public static void showDatePicker(Context context, DatePickerDialog.OnDateSetListener listener, Calendar calendar, int addYears, long minDateInMillis, long maxDateInMillis) {
        calendar.add(Calendar.YEAR, addYears);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        if (minDateInMillis > 0) datePickerDialog.getDatePicker().setMinDate(minDateInMillis);
        if (maxDateInMillis > 0) datePickerDialog.getDatePicker().setMaxDate(maxDateInMillis);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePickerDialog.setTitle("Select date");
        datePickerDialog.show();
    }

    public static void showDatePicker(Context context, DatePickerDialog.OnDateSetListener listener, Calendar calendar) {
        showDatePicker(context, listener, calendar, 0, 0, 0);
    }

    public static void showDatePicker(Context context, DatePickerDialog.OnDateSetListener listener, Calendar calendar, long minDateInMillis, long maxDateInMillis) {
        showDatePicker(context, listener, calendar, 0, minDateInMillis, maxDateInMillis);
    }

    public static Date getDatePart(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date today;
        if (date == null) {
            today = new Date();
        } else {
            today = date;
        }
        try {
            today = formatter.parse(formatter.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return today;
    }
}
