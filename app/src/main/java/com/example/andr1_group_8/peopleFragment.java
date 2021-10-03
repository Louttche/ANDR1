package com.example.andr1_group_8;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class peopleFragment extends Fragment {

    private Context context;
    private FragmentManager supportFragmentManager;
    private List<People> peopleList = new ArrayList<>();
    private View view;

    private Button searchButton;
    private Button clearButton;
    private EditText search_box;
    private TextView peopleCountText;

    public peopleFragment() {
        // Required empty public constructor
    }

    public static peopleFragment newInstance(String param1, String param2) {
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
        searchButton = (Button) view.findViewById(R.id.btn_search);
        clearButton = (Button) view.findViewById(R.id.btn_clear);
        search_box = (EditText) view.findViewById(R.id.et_search);
        peopleCountText = (TextView) view.findViewById(R.id.peopleCountText);

        Home mainActivity = (Home) this.getActivity();
        new JSONTask_GetPeople().execute(mainActivity.getCurrent_token());

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
            } catch (JSONException e) {
                System.out.println("Error reading people JSON");
                System.out.println(e.toString());
            }
        }
    }

    private List<People> ParsePeopleJson(String people_json) throws JSONException {
        List<People> parsedPeople = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(people_json);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject personObject = jsonArray.getJSONObject(i);

            String firstName = personObject.getString("givenName");
            String lastName = personObject.getString("surName");
            String email = personObject.getString("mail");
            String photo = personObject.getString("photo");

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