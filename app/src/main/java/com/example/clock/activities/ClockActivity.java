package com.example.clock.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.clock.fragments.ClockFragment;
import com.example.clock.fragments.TimePickerFragment;
import com.example.clock.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClockActivity extends AppCompatActivity {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        toolbar = getSupportActionBar();

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_clock:
                        toolbar.setTitle("Clock");
                        fragment = new ClockFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_timer:
                        toolbar.setTitle("Timer");
                        fragment = new TimePickerFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });

        // default setting
        toolbar.setTitle("Clock");
        loadFragment(new ClockFragment());
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}