package com.example.scoutingapp.ui;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import com.example.scoutingapp.R;
import com.example.scoutingapp.databinding.ActivityMainBinding;


public class HomeFragment {
    private ActivityMainBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = ActivityMainBinding.inflate(inflater, container, false);

        Button bViewData = binding.buttonData;
        bViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: create view data page

            }

        });
        Button bScout = binding.buttonScout;
        bScout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NavDirections() {
                    @Override
                    public int getActionId() {
                        return R.id.action_navigation_home_to_inputinformation;
                    }

                    @NonNull
                    @Override
                    public Bundle getArguments() {
                        return null;
                    }
                };

            }

        });

        Button bPitScout = binding.buttonPitScout;
        bPitScout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NavDirections() {
                    @Override
                    public int getActionId() {
                        return R.id.action_navigation_home_to_pitscouting;
                    }

                    @NonNull
                    @Override
                    public Bundle getArguments() {
                        return null;
                    }
                };

            }

        });
        return binding.getRoot();
    }

    private void updateEntry() {

    }
}
