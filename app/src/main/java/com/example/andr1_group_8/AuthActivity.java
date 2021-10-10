package com.example.andr1_group_8;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class AuthActivity extends AppCompatActivity implements TokenFragment
        .OnFragmentInteractionListener {

    public AuthActivity() {
        super(R.layout.activity_auth);
    }

    AuthActivity activityReference;
    private String current_token = "invalid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentAuth, authFragment.class, null)
                    .commit();

            activityReference = this;
        }

        // Set up Navigation
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_auth);
        NavController navController = navHostFragment.getNavController();

    }

    @Override
    public void onFragmentInteraction(String token) {
        this.current_token = token;

        // Store token in 'dataService' custom service
        Intent dataServiceIntent = new Intent(this, dataService.class);
        dataServiceIntent.putExtra("token", token);
        startService(dataServiceIntent);

        try {
            if (token != "invalid"){
                Navigation.findNavController(this, R.id.nav_host_fragment_auth)
                        .navigate(R.id.action_tokenFragment_to_homeActivity);
                finish();
            }
        } catch (NullPointerException e){
            Log.e("token", "onFragmentInteraction: ", e);
        }
    }

    public String getCurrent_token() {
        return this.current_token;
    }
}