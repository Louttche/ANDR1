package com.example.andr1_group_8;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class peopleFragment extends Fragment {

    private Context context;
    private FragmentManager supportFragmentManager;

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

        View view = inflater.inflate(R.layout.fragment_people, container, false);
        Button student_button = (Button) view.findViewById(R.id.btn_students);
        Button teacher_button = (Button) view.findViewById(R.id.btn_students);
        EditText search_box = (EditText) view.findViewById(R.id.et_search);

        // List of people
        ListView people_list = (ListView) view.findViewById(R.id.lv_people);
        people_list.setAdapter(new PeopleAdapter(context, getPeople()));

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;
    }

    public List<People> getPeople(){
        List<People> temp_pList = new ArrayList<>();

        // Dummy Data
        temp_pList.add(new People("John", "Smeeth", null));
        temp_pList.add(new People("Maria", "Burgers", null));
        temp_pList.add(new People("Julia", "Rohurts", null));
        temp_pList.add(new People("Eric", "Gothernburg", null));
        temp_pList.add(new People("Jesus", "Christ", null));

        return  temp_pList;
    }
}