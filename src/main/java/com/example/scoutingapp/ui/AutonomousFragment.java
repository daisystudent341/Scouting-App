package com.example.scoutingapp.ui;
import android.os.Bundle;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = AutonomousBinding.inflate(inflater, container, false);

        Button bQuit = binding.buttonQuit;
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
                binding.textHighValue.setText(QR.currEntry.highScoredAuton);
            }

        });
        Button bHighMinus = binding.buttonHighMinus;
        bHighMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.highScoredAuton--;
                binding.textHighValue.setText(QR.currEntry.highScoredAuton);

            }

        });
        Button bHighMissedPlus = binding.buttonHighPlus;
        bHighMissedPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.highMissedAuton++;
                binding.textHighMissedValue.setText(QR.currEntry.highMissedAuton);
            }

        });
        Button bHighMissedMinus = binding.buttonHighMinus;
        bHighMissedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.highMissedAuton--;
                binding.textHighMissedValue.setText(QR.currEntry.highMissedAuton);

            }

        });
        Button bLowPlus = binding.buttonLowPlus;
        bLowPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.lowScoredAuton++;
                binding.textLowValue.setText(QR.currEntry.lowScoredAuton);
            }

        });
        Button bLowMinus = binding.buttonLowMinus;
        bLowMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.lowScoredAuton--;
                binding.textLowValue.setText(QR.currEntry.lowScoredAuton);


            }

        });
        Button bLowMissedPlus = binding.buttonLowMissedPlus;
        bLowMissedPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.lowMissedAuton++;
                binding.textLowMissedValue.setText(QR.currEntry.lowMissedAuton);

            }

        });
        Button bLowMissedMinus = binding.buttonLowMissedMinus;
        bLowMissedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.lowMissedAuton--;
                binding.textLowMissedValue.setText(QR.currEntry.lowMissedAuton);

            }

        });

        CheckBox cInteracts = binding.checkBoxOtherColor;
        cInteracts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QR.currEntry.interactsWithOtherTeamAuton = cInteracts.isChecked();

                }


        });
        CheckBox cTaxi = binding.checkBoxTaxi;
        cInteracts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QR.currEntry.taxi = cTaxi.isChecked();

            }


        });

        return binding.getRoot();
    }
}
