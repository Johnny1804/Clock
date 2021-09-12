package com.example.clock.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.clock.R;

public class TimePickerFragment extends Fragment {

    private TimePicker timePicker;
    private Button startPause;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);

        timePicker = view.findViewById(R.id.time_picker);
        startPause = view.findViewById(R.id.start_pause);

        timePicker.setIs24HourView(true);

        startPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get time from time picker and put it in a integer as seconds
                int time = (timePicker.getHour() * 3600) + (timePicker.getMinute() * 60);

                // share time
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("time_saved", time);
                editor.apply();

                // load the fragment with progres bar
                loadFragment(new ProgressBarFragment());
            }
        });

        return view;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}