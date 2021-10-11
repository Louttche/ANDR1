package com.example.andr1_group_8;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private List<News> newsList;
    private LayoutInflater layoutInflater;
    private Context context;

    public NewsAdapter(Context mContext, List<News> mNewsList) {
        this.newsList = mNewsList;
        this.context = mContext;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.newsList.size();
    }

    @Override
    public News getItem(int i) {
        return this.newsList.get(i);
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
            view = layoutInflater.inflate(R.layout.adapter_news, viewGroup, false);
        }

        // Get the relevant Views out of Child View
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_ntitle);
        WebView tvContent = (WebView) view.findViewById(R.id.wv_nContent);
        ImageView tvThumbnail = (ImageView) view.findViewById(R.id.iv_nthumbnail);

        News news = getItem(i);
        tvTitle.setText(StringUtils.abbreviate(news.getTitle(), 30));
        tvContent.loadData(StringUtils.abbreviate(news.getContent(), 80), "text/html", "utf-8");

        // Set up onClick so that an Email can be sent
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("news", "onClick: news link: " + news.getLink());
                Intent browse = new Intent( Intent.ACTION_VIEW , Uri.parse(news.getLink()) );
                context.startActivity(browse);
            }
        });

        // Set up thumbnail
        String thumbnail = news.getThumbnail();
        String bearerToken = ((HomeActivity) context).getCurrent_token();

        GlideUrl url = new GlideUrl(thumbnail, new LazyHeaders.Builder()
                .addHeader("Authorization", bearerToken).build());

        Glide.with(context)
                .load(url)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(15)))
                .error(R.drawable.person_photo)
                .into(tvThumbnail);

        return view;
    }
}
