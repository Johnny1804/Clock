package com.example.clock.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.clock.views.ClockSurfaceView;

public class ClockFragment extends Fragment {

    private ClockSurfaceView clockSurfaceView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        clockSurfaceView = new ClockSurfaceView(getContext(), 500);

        return clockSurfaceView;
    }

    @Override
    public void onPause() {
        super.onPause();

        clockSurfaceView.onClockPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        clockSurfaceView.onClockResume();
    }

}