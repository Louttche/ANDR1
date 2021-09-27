package com.example.andr1_group_8;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
        super(R.layout.fragment_home);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // UI Elements
        Button people_button = (Button) view.findViewById(R.id.btn_people);
        people_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                NavDirections action =
                        homeFragmentDirections.actionHomeFragmentToPeopleFragment();
                Navigation.findNavController(view).navigate(action);

                // TODO: Replace existing fragment (opacity)

                //FragmentManager fm = getFragmentManager();
                //fm.beginTransaction()
                //        .show(new peopleFragment())
                //        .commit();

                //final FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.replace(R.id.homeFragment, new peopleFragment());
                //ft.commit();

            }
        });

        Button schedule_button = (Button) view.findViewById(R.id.btn_schedule);
        schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action =
                        homeFragmentDirections.actionHomeFragmentToActivitySchedule();
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;

    }
}