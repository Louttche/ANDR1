package com.example.andr1_group_8;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class PeopleAdapter extends BaseAdapter {

    private List<People> peopleList;
    private LayoutInflater layoutInflater;
    private Context context;

    public PeopleAdapter(Context mContext, List<People> mPeopleList) {
        this.peopleList = mPeopleList;
        this.context = mContext;
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
        String photo = person.getPhoto();

        // Set up image (using Glide so that we can pass the token to load the image url)
        String bearerToken = ((HomeActivity) context).getCurrent_token();

        GlideUrl url = new GlideUrl(photo, new LazyHeaders.Builder()
                .addHeader("Authorization", bearerToken).build());

        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(15)))
                .error(R.drawable.person_photo)
                .into(tvImage);

        return view;
    }
}
