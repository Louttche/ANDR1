package com.example.andr1_group_8;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link newsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class newsFragment extends Fragment {

    private Context context;
    private List<News> newsList = new ArrayList<>();
    private View view;

    public newsFragment() {
        // Required empty public constructor
        super(R.layout.fragment_news);
    }

    public static newsFragment newInstance() {
        newsFragment fragment = new newsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_news, container, false);

        // Create onTouchListener so that clicks don't work on fragments below
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        HomeActivity homeActivity = (HomeActivity) this.getActivity();
        if (homeActivity != null)
            new JSONTask_GetNews().execute(homeActivity.getCurrent_token());

        return view;
    }

    // News

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private class JSONTask_GetNews extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String s = null;
            URL url = null;
            try {
                url = new URL("https://api.fhict.nl/newsfeeds/BronNieuws/posts");
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                newsList = ParseNewsJson(s);
                // List of news
                displayInList(newsList);
            } catch (JSONException | IOException e) {
                System.out.println("Error reading news JSON");
                System.out.println(e.toString());
            }
        }
    }

    private List<News> ParseNewsJson(String news_json) throws JSONException, IOException {
        List<News> parsedNews = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(news_json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject newsObject = jsonArray.getJSONObject(i);

            String date = newsObject.getString("pubDate");
            String title = newsObject.getString("title");
            String thumbnail = newsObject.getString("thumbnail");
            String link = newsObject.getString("link");
            String content = newsObject.getString("content");

            News news = new News(date, title, thumbnail, link, content);
            parsedNews.add(news);
        }

        return parsedNews;
    }

    private void displayInList(List<News> news) {
        ListView news_list = (ListView) view.findViewById(R.id.lv_news);
        news_list.setAdapter(new NewsAdapter(context, news));
    }
}