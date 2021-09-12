package com.example.clock.fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.clock.models.AlarmNotificationReceiver;
import com.example.clock.R;

import java.util.Calendar;
import java.util.Date;

public class ProgressBarFragment extends Fragment{

    private Button cancel;
    private Button start_pause;
    private ProgressBar progressBar;
    private TextView progressTextView;

    private int time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_progress_bar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        this.time = sharedPref.getInt("time_saved", 0);

        cancel = view.findViewById(R.id.cancel);
        start_pause = view.findViewById(R.id.start_pause);
        progressBar = view.findViewById(R.id.progress_bar);
        progressTextView = view.findViewById(R.id.txtProgress);

        setAlarm(view);

        progressBar.setMax(time);
        progressBar.setProgress(time);
        start_pause.setText("Pause");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // stop the alarm
                Intent intent = new Intent(getContext(), AlarmNotificationReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1253, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

                loadFragment(new TimePickerFragment());
            }
        });

        start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (start_pause.getText().toString()) {
                    case "Resume":
                        start_pause.setText("Pause");
                        timeResume();
                        break;

                    case "Pause":
                        start_pause.setText("Resume");
                        timePause();
                        break;
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (time >= 0) {
                    try {
                        Message msg = new Message();
                        msg.what = time;
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                        time = time - 1;
                    } catch (InterruptedException e) {
                    }
                }
            }
        }).start();

    }

    private Handler handler = new Handler() {
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            int currentPosition = msg.what;
            Log.d("DEBUG", String.valueOf(time)+" "+String.valueOf(currentPosition));
            progressBar.setProgress(currentPosition);
            progressTextView.setText(getStringTime(currentPosition));
        }
    };

    void timePause() {
    }

    void timeResume() {
    }

    private void setAlarm(View view) {
        AlarmManager alarmManager =  (AlarmManager) view.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1253, intent, 0);

        Date date = new Date();
        Calendar alarmTime = Calendar.getInstance();
        Calendar timeNow = Calendar.getInstance();

        int timeCopy = time;
        int timeValue = timeCopy / 3600; // get hours from time

        timeCopy %= 3600;
        timeNow.setTime(date);
        alarmTime.setTime(date);
        alarmTime.set(Calendar.HOUR_OF_DAY, timeValue);
        timeValue = timeCopy / 60;
        timeCopy %= 60;
        alarmTime.set(Calendar.MINUTE, timeValue);
        timeValue = timeCopy;
        alarmTime.set(Calendar.SECOND, timeValue);

        if (alarmTime.before(timeNow)) {
            alarmTime.add(Calendar.DATE, 1);
            time = alarmTime.getTime().getHours() * 3600 + alarmTime.getTime().getMinutes() * 60 + alarmTime.getTime().getSeconds();
        }

        time -= timeNow.getTime().getHours() * 3600 + timeNow.getTime().getMinutes() * 60 + timeNow.getTime().getSeconds();

        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        timePause();
    }

    @Override
    public void onResume() {
        super.onResume();
        timeResume();
    }

    private String getStringTime(int time) {
        StringBuilder stringBuilder = new StringBuilder();
        int hours = time / 3600; time %= 3600;
        int minutes = time / 60; time %= 60;
        int seconds = time;

        stringBuilder.
                append(String.valueOf(hours)).append(":").
                append(String.valueOf(minutes)).append(":").
                append(String.valueOf(seconds));

        return stringBuilder.toString();
    }

}