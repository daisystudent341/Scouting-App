package com.example.scoutingapp.ui;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import com.example.scoutingapp.MainActivity;
import com.example.scoutingapp.QR;
import com.example.scoutingapp.R;
import com.example.scoutingapp.databinding.AutonomousBinding;



public class AutonomousFragment  extends Fragment {
    private AutonomousBinding binding;
    private CheckBox cInteracts;
    private CheckBox cTaxi;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = AutonomousBinding.inflate(inflater, container, false);



        binding.textUserInfo.setText("Name: " + QR.currEntry.scoutName + "\nScouting: " +
                QR.currEntry.teamScouting + "\nColor: " +
                (QR.currEntry.teamColor == 'R' ? "Red" : "Blue") +
                "\nMatch: " + QR.currEntry.matchNum);


        Button bQuit = binding.buttonQuit;
        cTaxi = binding.checkBoxTaxi;
        cInteracts = binding.checkBoxOtherColor;

        binding.textHighValue.setText("" + QR.currEntry.highScoredAuton);
        binding.textHighMissedValue.setText("" + QR.currEntry.highMissedAuton);
        binding.textLowValue.setText("" + "" + QR.currEntry.lowScoredAuton);
        binding.textLowMissedValue.setText("" + QR.currEntry.lowMissedAuton);

        cInteracts.setChecked(QR.currEntry.interactsWithOtherTeamAuton);
        cTaxi.setChecked(QR.currEntry.taxi);

        bQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(new NavDirections() {
                    @Override
                    public int getActionId() {
                        return R.id.action_navigation_autonomous_to_inputinformation;
                    }

                    @NonNull
                    @Override
                    public Bundle getArguments() {
                        return null;
                    }
                });


            }

        });
        Button bSubmit = binding.buttonSubmit;
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.navController.navigate(new NavDirections() {
                    @Override
                    public int getActionId() {
                        return R.id.action_navigation_autonomous_to_teleop;
                    }

                    @NonNull
                    @Override
                    public Bundle getArguments() {
                        return null;
                    }
                });

            }

        });

        Button bHighPlus = binding.buttonHighPlus;
        bHighPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.highScoredAuton++;
                binding.textHighValue.setText("" + QR.currEntry.highScoredAuton);
            }

        });
        Button bHighMinus = binding.buttonHighMinus;
        bHighMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.highScoredAuton--;
                QR.currEntry.highScoredAuton = Math.max(0, QR.currEntry.highScoredAuton);
                binding.textHighValue.setText("" + QR.currEntry.highScoredAuton);

            }

        });
        Button bHighMissedPlus = binding.buttonHighMissedPlus;
        bHighMissedPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.highMissedAuton++;
                binding.textHighMissedValue.setText("" + QR.currEntry.highMissedAuton);
            }

        });
        Button bHighMissedMinus = binding.buttonHighMissedMinus;
        bHighMissedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.highMissedAuton--;
                QR.currEntry.highMissedAuton = Math.max(0, QR.currEntry.highMissedAuton);

                binding.textHighMissedValue.setText("" + QR.currEntry.highMissedAuton);

            }

        });
        Button bLowPlus = binding.buttonLowPlus;
        bLowPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.lowScoredAuton++;
                binding.textLowValue.setText("" + "" + QR.currEntry.lowScoredAuton);
            }

        });
        Button bLowMinus = binding.buttonLowMinus;
        bLowMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.lowScoredAuton--;
                QR.currEntry.lowScoredAuton = Math.max(0, QR.currEntry.lowScoredAuton);

                binding.textLowValue.setText("" + "" + QR.currEntry.lowScoredAuton);


            }

        });
        Button bLowMissedPlus = binding.buttonLowMissedPlus;
        bLowMissedPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.lowMissedAuton++;
                binding.textLowMissedValue.setText("" + QR.currEntry.lowMissedAuton);

            }

        });
        Button bLowMissedMinus = binding.buttonLowMissedMinus;
        bLowMissedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.lowMissedAuton--;
                QR.currEntry.lowMissedAuton = Math.max(0, QR.currEntry.lowMissedAuton);

                binding.textLowMissedValue.setText("" + QR.currEntry.lowMissedAuton);

            }

        });



        cInteracts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("YOOOO", cInteracts.isChecked() ?"YE":"NA");
                    QR.currEntry.interactsWithOtherTeamAuton = cInteracts.isChecked();

                }


        });
        cTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("YOOOO", cTaxi.isChecked() ?"YE":"NA");

                QR.currEntry.taxi = cTaxi.isChecked();

            }


        });

        return binding.getRoot();
    }
}
