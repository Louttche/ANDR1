package com.example.andr1_group_8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    public Home() {
        super(R.layout.activity_home);
    }

    AppBarConfiguration appBarConfiguration;

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

        // set up collapsing toolbar
        //CollapsingToolbarLayout layout = findViewById(R.id.collapsing_toolbar_layout);
        //Toolbar toolbar = findViewById(R.id.tb_topNavBar);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_host);
        NavController navController = navHostFragment.getNavController();

        // Set up toolbar widget as navigation bar
        //AppBarConfiguration appBarConfiguration =
                //new AppBarConfiguration.Builder(navController.getGraph())
                        //.setFallbackOnNavigateUpListener(this::onSupportNavigateUp)
                        //.build();

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();


        NavigationUI.setupActionBarWithNavController( this,
                navController, appBarConfiguration
        );

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fragment_nav_host);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.fragment_nav_host);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);

        // Handle item selection
        /*switch (item.getItemId()) {
            case R.id.peopleFragment:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_nav_host, peopleFragment.class, null)
                        .addToBackStack(homeFragment.class.getName())
                        .commit();
                return true;
            case R.id.activity_schedule:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_nav_host, scheduleFragment.class, null)
                        .addToBackStack(homeFragment.class.getName())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        } */
    }
}