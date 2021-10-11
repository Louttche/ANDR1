package com.example.andr1_group_8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.andr1_group_8.dataService.dataServiceBinder;

public class HomeActivity extends AppCompatActivity implements TokenFragment
        .OnFragmentInteractionListener {

    public HomeActivity() {
        super(R.layout.activity_home);
    }

    HomeActivity activityReference;
    AppBarConfiguration appBarConfiguration;

    boolean mdataServiceBound = false;
    public dataService mdataService;

    String current_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentHome, homeFragment.class, null)
                    .commit();

            activityReference = this;
        }

        // Set up Navigation
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupActionBarWithNavController( this,
                navController, appBarConfiguration
        );

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Set up data service
        Intent intent = new Intent(this, dataService.class);
        //finish();
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mdataServiceBound) {
            unbindService(mServiceConnection);
            mdataServiceBound = false;
        }
        Log.v("dataService", "onStop: ");
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation
                .findNavController(this, R.id.nav_host_fragment);
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
        NavController navController = Navigation
                .findNavController(this, R.id.nav_host_fragment);

        switch (item.getItemId()){
            case R.id.log_out:
                // TODO: Reset token and send back to auth page
                this.mdataService.onDestroy();
                navController.navigate(R.id.action_homeFragment_to_authActivity);
                finish();
                return true;
            default:
                return NavigationUI.onNavDestinationSelected(item, navController)
                        || super.onOptionsItemSelected(item);
        }
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mdataServiceBound = false;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            dataServiceBinder myBinder = (dataServiceBinder) service;
            mdataService = myBinder.getService();
            mdataServiceBound = true;

            try {
                // If token is invalid, redirect to Auth activity
                current_token = mdataService.getUser_token();
                Log.d("token", "Home onServiceConnected: " + current_token);

                if (current_token == "invalid"){
                    Navigation.findNavController(activityReference, R.id.nav_host_fragment)
                            .navigate(R.id.action_homeFragment_to_authActivity);
                    finish();
                }
            } catch (NullPointerException e){
                Log.e("token", "Home onServiceConnected: token = " + current_token, e);
            }
        }
    };

    @Override
    public void onFragmentInteraction(String token) {
        this.current_token = token;
    }

    public String getCurrent_token() {
        return this.current_token;
    }
}