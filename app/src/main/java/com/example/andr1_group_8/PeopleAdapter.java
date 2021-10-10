package com.example.andr1_group_8;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.List;

public class PeopleAdapter extends BaseAdapter {

    private List<People> peopleList;
    private LayoutInflater layoutInflater;

    public PeopleAdapter(Context mContext, List<People> mPeopleList) {
        this.peopleList = mPeopleList;

        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.peopleList.size();
    }

    @Override
    public People getItem(int i) {
        return this.peopleList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Create the new child view by inflation
        if (view == null) {
            // Without recycling
            view = layoutInflater.inflate(R.layout.adapter_people, viewGroup, false);
        }

        // Get the relevant Views out of Child View
        TextView tvName = (TextView) view.findViewById(R.id.tv_pName);
        TextView tvEmail = (TextView) view.findViewById(R.id.tv_pEmail);
        ImageView tvImage = (ImageView) view.findViewById(R.id.iv_pPhoto);

        // Look up the value to be displayed and show in TextView
        People person = getItem(i);
        tvName.setText(person.getFullName());
        tvEmail.setText(person.getEmail());
        Drawable photo = person.getPhoto();
        tvImage.setImageDrawable(photo);

        return view;
    }
}
