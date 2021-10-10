package com.example.andr1_group_8;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class authFragment extends Fragment {

    private Button loginButton;
    private View view;

    public authFragment() {
        // Required empty public constructor
        super(R.layout.fragment_auth);
    }

    public static authFragment newInstance() {
        authFragment fragment = new authFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        view = inflater.inflate(R.layout.fragment_auth, container, false);

        //AuthActivity auth = (AuthActivity) getActivity();
        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_auth);

        loginButton = (Button) view.findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Go to token fragment to login
                navController.navigate(R.id.action_authFragment_to_tokenFragment);
                container.removeAllViews();
            }
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}