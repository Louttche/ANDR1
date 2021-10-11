package com.example.andr1_group_8;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class peopleFragment extends Fragment {

    private Context context;
    private List<People> peopleList = new ArrayList<>();
    private View view;

    private EditText search_box;
    private TextView peopleCountText;

    public peopleFragment() {
        // Required empty public constructor
    }

    public static peopleFragment newInstance() {
        peopleFragment fragment = new peopleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_people_main, container, false);
        view = inflater.inflate(R.layout.fragment_people, container, false);
        Button searchButton = (Button) view.findViewById(R.id.btn_search);
        Button clearButton = (Button) view.findViewById(R.id.btn_clear);
        search_box = (EditText) view.findViewById(R.id.et_search);
        peopleCountText = (TextView) view.findViewById(R.id.peopleCountText);

        HomeActivity homeActivity = (HomeActivity) this.getActivity();
        if (homeActivity != null)
            new JSONTask_GetPeople().execute(homeActivity.getCurrent_token());

        searchButton.setOnClickListener(searchPeople());
        clearButton.setOnClickListener(clearFilter());

        return view;
    }

    private View.OnClickListener searchPeople() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                String searchQuery = search_box.getText().toString().toLowerCase();
                List<People> filteredPeople = peopleList.stream().filter(p -> p.getFullName().toLowerCase().contains(searchQuery)).collect(Collectors.toList());
                displayInList(filteredPeople);
            }
        };
    }

    private View.OnClickListener clearFilter() {
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View v) {
                search_box.setText("");
                displayInList(peopleList);
            }
        };
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
    }


    private class JSONTask_GetPeople extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String s = null;
            URL url = null;
            try {
                url = new URL("https://api.fhict.nl/people");
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
                peopleList = ParsePeopleJson(s);
                // List of people
                displayInList(peopleList);
            } catch (JSONException | IOException e) {
                System.out.println("Error reading people JSON");
                System.out.println(e.toString());
            }
        }
    }

    private List<People> ParsePeopleJson(String people_json) throws JSONException, IOException {
        List<People> parsedPeople = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(people_json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject personObject = jsonArray.getJSONObject(i);

            String firstName = personObject.getString("givenName");
            String lastName = personObject.getString("surName");
            String email = personObject.getString("mail");
            String photo = personObject.getString("photo");

            //TODO: Get actual photo of person + convert photo string to Drawable
            Drawable dummy_photo = getResources().getDrawable(R.drawable.person_photo);
            //Bitmap dummy_photo_bm = BitmapFactory.decodeResource(getResources(), R.drawable.person_photo);

            People person = new People(firstName, lastName, email, photo);
            parsedPeople.add(person);
        }

        return parsedPeople;
    }

    private void displayInList(List<People> people) {
        ListView people_list = (ListView) view.findViewById(R.id.lv_people);
        people_list.setAdapter(new PeopleAdapter(context, people));
        peopleCountText.setText("(" + people.size() + ")");
    }
}