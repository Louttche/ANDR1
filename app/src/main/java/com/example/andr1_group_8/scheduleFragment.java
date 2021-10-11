package com.example.andr1_group_8;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link scheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class scheduleFragment extends Fragment {
    private Context context;
    private View view;
    List<ScheduleItem> scheduleItems = new ArrayList<>();
    List<String> weekdayNames = new ArrayList<String>();

    public scheduleFragment() {
        // Required empty public constructor
    }

    public static scheduleFragment newInstance(String param1, String param2) {
        scheduleFragment fragment = new scheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
    }

    private class JSONTask_GetSchedule extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String s = null;
            URL url = null;
            try {
                url = new URL("https://api.fhict.nl/schedule/me");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Authorization", "Bearer " + strings[0]);

                connection.connect();

                InputStream is = connection.getInputStream();
                Scanner scn = new Scanner(is);
                s = scn.useDelimiter("\\Z").next();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return s;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                scheduleItems = ParseScheduleItems(s);
                displayInList(scheduleItems);
            } catch (JSONException e) {
                System.out.println("Error reading people JSON");
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_schedule, container, false);

        weekdayNames.add("Monday");
        weekdayNames.add("Tuesday");
        weekdayNames.add("Wednesday");
        weekdayNames.add("Thursday");
        weekdayNames.add("Friday");
        weekdayNames.add("Saturday");
        weekdayNames.add("Sunday");

        // Calendar View
        CalendarView simpleCalendarView = (CalendarView) view.findViewById(R.id.cv_schedule); // get the reference of CalendarView
        simpleCalendarView.setVisibility(View.GONE);
        simpleCalendarView.setFirstDayOfWeek(2); // set Monday as the first day of the week

        Button calendarButton = (Button) view.findViewById(R.id.btn_calendar);
        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleCalendarView.setVisibility(View.VISIBLE);
            }
        });
        //long selectedDate = simpleCalendarView.getDate(); // get selected date in milliseconds // --> Get selected date
        //simpleCalendarView.setDate(1463918226920L); // --> Set selected date

        // Create onTouchListener so that clicks don't work on fragments below
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                simpleCalendarView.setVisibility(View.GONE);
                return true;
            }
        });

        HomeActivity homeActivity = (HomeActivity) this.getActivity();
        new JSONTask_GetSchedule().execute(homeActivity.getCurrent_token());
        displayInList(scheduleItems);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<ScheduleItem> ParseScheduleItems(String apiString) throws JSONException {
        List<ScheduleItem> parsedScheduleItems = new ArrayList<>();
        JSONObject baseObject = new JSONObject(apiString);
        JSONArray jsonArray = baseObject.getJSONArray("data");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject scheduleObject = jsonArray.getJSONObject(i);

            String subject = scheduleObject.getString("subject");
            String start = scheduleObject.getString("start");
            String end = scheduleObject.getString("end");

            Calendar startDate = null;
            Calendar endDate = null;
            try {
                startDate = getDateFromString(start);
                endDate = getDateFromString(end);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int weekdayIndex = startDate.get(Calendar.DAY_OF_WEEK);
            String weekdayName = startDate.get(Calendar.DATE) + "." + (startDate.get(Calendar.MONTH) + 1) + " " + weekdayNames.get(weekdayIndex);
            String startHour = startDate.get(Calendar.HOUR_OF_DAY) + ":" + parseMinute(startDate.get(Calendar.MINUTE));
            String endHour = endDate.get(Calendar.HOUR_OF_DAY) + ":" + parseMinute(endDate.get(Calendar.MINUTE));

            ScheduleItem scheduleItem = new ScheduleItem(weekdayName, startHour, endHour, subject);
            parsedScheduleItems.add(scheduleItem);
        }

        return parsedScheduleItems;
    }

    private void displayInList(List<ScheduleItem> scheduleItems) {
        ListView scheduleList = (ListView) view.findViewById(R.id.lv_schedule);
        scheduleList.setAdapter(new ScheduleAdapter(context, scheduleItems));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Calendar getDateFromString(String str) throws ParseException {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            cal.setTime(sdf.parse(str));
            return cal;
    }

    private String parseMinute(int minute) {
        return minute < 10 ? "0" + minute : "" + minute;
    }
}