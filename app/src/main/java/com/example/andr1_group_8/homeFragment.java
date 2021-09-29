package com.example.andr1_group_8;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {

    public homeFragment() {
        // Required empty public constructor
        super(R.layout.fragment_home);
    }

    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // UI Elements
        //Button people_button = (Button) view.findViewById(R.id.btn_people);
        //people_button.setOnClickListener(new View.OnClickListener(){
            //@Override
            //public void onClick(View v){

                // Hide this fragment's layout
                //v.findViewById(R.id.home_layout).setVisibility(View.INVISIBLE);

                //getParentFragmentManager().beginTransaction()
                        //.add(R.id.fragment_nav_host, peopleFragment.class, null)
                        //.hide(this) // getParentFragmentManager().findFragmentById(R.id.homeFragment)
                        //.commit();

                // Navigates to correct fragment
                //NavDirections action =
                //        homeFragmentDirections.actionHomeFragmentToPeopleFragment();
                //Navigation.findNavController(view).navigate(action);

            //}
        //});

        return view;
    }
}