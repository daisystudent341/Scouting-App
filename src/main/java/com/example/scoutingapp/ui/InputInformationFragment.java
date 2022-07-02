package com.example.scoutingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.example.scoutingapp.MainActivity;
import com.example.scoutingapp.QR;
import com.example.scoutingapp.R;
import com.example.scoutingapp.databinding.InputinformationBinding;


public class InputInformationFragment extends Fragment {
    private InputinformationBinding binding;
    private String scoutName = "";
    private boolean teamColor = false; // false = blue, true = red
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = InputinformationBinding.inflate(inflater, container, false);

        loadFromCache();

        Button bStart = binding.buttonStartMatch;
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.scoutName = scoutName = binding.editTextScoutName.getText().toString();
                QR.currEntry.matchNum =
                        Integer.getInteger(binding.editTextScoutName.getText().toString());
                QR.currEntry.teamScouting =
                        Integer.getInteger(binding.editTextTeamScouting.getText().toString());
                teamColor = binding.toggleButtonTeamColor.isChecked();
                QR.currEntry.teamColor = teamColor ? 'R' : 'B';

                MainActivity.navController.navigate(new NavDirections() {
                    @Override
                    public int getActionId() {


                        return R.id.action_navigation_inputinformation_to_autonomous;
                    }

                    @NonNull
                    @Override
                    public Bundle getArguments() {
                        return null;
                    }
                });


            }

        });


        Button bBack = binding.buttonBack;
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(new NavDirections() {
                    @Override
                    public int getActionId() {
                        return R.id.action_navigation_inputinformation_to_home;
                    }

                    @NonNull
                    @Override
                    public Bundle getArguments() {
                        return null;
                    }
                });


            }

        });

        return binding.getRoot();
    }
    private void loadFromCache() {
        binding.toggleButtonTeamColor.setChecked(teamColor);
        binding.editTextScoutName.setText(scoutName);

    }


}
