package com.example.andr1_group_8;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link scheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class scheduleFragment extends Fragment {
    private Context context;
    private View view;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_schedule, container, false);
        List<ScheduleItem> scheduleItems = new ArrayList<>();
        scheduleItems.add(new ScheduleItem("Monday", "10:00", "12:00", "Android 1"));
        scheduleItems.add(new ScheduleItem("Monday", "10:00", "12:00", "Android 1"));
        scheduleItems.add(new ScheduleItem("Monday", "10:00", "12:00", "Android 1"));
        scheduleItems.add(new ScheduleItem("Monday", "10:00", "12:00", "Android 1"));
        scheduleItems.add(new ScheduleItem("Monday", "10:00", "12:00", "Android 1"));
        scheduleItems.add(new ScheduleItem("Monday", "10:00", "12:00", "Android 1"));
        scheduleItems.add(new ScheduleItem("Monday", "10:00", "12:00", "Android 1"));
        displayInList(scheduleItems);

        return view;
    }

    private void displayInList(List<ScheduleItem> scheduleItems) {
        ListView scheduleList = (ListView) view.findViewById(R.id.lv_schedule);
        scheduleList.setAdapter(new ScheduleAdapter(context, scheduleItems));
    }
}