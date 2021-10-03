package com.example.andr1_group_8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ScheduleAdapter extends BaseAdapter {

    private List<ScheduleItem> scheduleList;
    private LayoutInflater layoutInflater;


    public ScheduleAdapter(Context mContext, List<ScheduleItem> mPeopleList) {
        this.scheduleList = mPeopleList;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return scheduleList.size();
    }

    @Override
    public ScheduleItem getItem(int i) {
        return scheduleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_schedule, viewGroup, false);
        }

        TextView tvSubject = (TextView) view.findViewById(R.id.tv_subject);
        TextView tvDay = (TextView) view.findViewById(R.id.tv_day);
        TextView tvHours = (TextView) view.findViewById(R.id.tv_hours);

        ScheduleItem scheduleItem = getItem(i);
        tvSubject.setText(scheduleItem.getSubject());
        tvDay.setText(scheduleItem.getDay());
        tvHours.setText(scheduleItem.getStartTime() + " - " + scheduleItem.getEndTime());

        return view;
    }
}
