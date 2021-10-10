package com.example.andr1_group_8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadJson extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_json);
        populateListView();
    }

    private List<String> readJson() throws IOException, JSONException {
        InputStream is = getApplicationContext().getAssets().open("json.json");

        // turn InputStream "is" into a String
        String jsonString = (new Scanner(is)).useDelimiter("\\Z").next();

        // get the full object from the jsonString
        JSONObject jsonObject = new JSONObject(jsonString);

        // from that object, get field "data", which is a JSONArray
        JSONArray jsonArray = jsonObject.getJSONArray("data");

        // get all elements from that JSONArray, and put into ArrayList
        List<String> subjectList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            // each array element is an object
            JSONObject scheduleObject = jsonArray.getJSONObject(i);

            // from each object, get field "subject", which is a string
            String subName = scheduleObject.getString("subject");

            // add each "subject" string to list
            subjectList.add(subName);
        }

        return subjectList;
    }

    private void populateListView() {
        lv = (ListView) findViewById(R.id.json_lv);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        try {
            List<String> your_array_list = readJson();

            // This is the array adapter, it takes the context of the activity as a
            // first parameter, the type of list view as a second parameter and your
            // array as a third parameter.
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    your_array_list);

            lv.setAdapter(arrayAdapter);
        } catch (IOException e) {
            //nothing
            System.out.println("Exception 1");
            System.out.println(e.toString());
        } catch (JSONException e) {
            System.out.println("Exception 2");
            System.out.println(e.toString());
        }
    }
}