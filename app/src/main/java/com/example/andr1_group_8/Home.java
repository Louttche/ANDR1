package com.example.andr1_group_8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

public class Home extends AppCompatActivity {

    public Home() {
        super(R.layout.activity_home);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentHome, homeFragment.class, null)
                    .commit();
        }

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_host);
        NavController navController = navHostFragment.getNavController();

        // Begin the transaction
        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Replace the contents of the container with the new fragment
        //ft.replace(R.id.placeholder, new homeFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());

        // Complete the changes added above
        //ft.commit();
    }
}