package com.example.scoutingapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.example.scoutingapp.MainActivity;
import com.example.scoutingapp.QR;
import com.example.scoutingapp.R;
import com.example.scoutingapp.databinding.InputinformationBinding;


public class InputInformationFragment extends Fragment {
    private InputinformationBinding binding;
    private static String scoutName = "";
    private static boolean teamColor = false; // false = blue, true = red

    int getColor(int id) {

        return ContextCompat.getColor(getContext(), id);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = InputinformationBinding.inflate(inflater, container, false);

        loadFromCache();

        if (!teamColor) {
            binding.toggleButtonTeamColor.setBackgroundColor(getColor(R.color.blue));
        } else {
            binding.toggleButtonTeamColor.setBackgroundColor(getColor(R.color.red));

        }

        ToggleButton bColor = binding.toggleButtonTeamColor;
        bColor.setOnClickListener(new View.OnClickListener() {
              @SuppressLint("ResourceAsColor")
              @Override
              public void onClick(View v) {
                  if (bColor.isChecked()) {
                      binding.toggleButtonTeamColor.setBackgroundColor(getColor(R.color.red));

                  } else binding.toggleButtonTeamColor.setBackgroundColor(getColor(R.color.blue));


              }
          });

        Button bStart = binding.buttonStartMatch;
        bStart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                QR.currEntry.scoutName = scoutName = binding.editTextScoutName.getText().toString();
                boolean good = true;
                binding.editTextScoutName.setHintTextColor(getColor(R.color.black));
                binding.editTextScoutName.setHint("Scout Name");
                if (scoutName.isEmpty()) {
                    good = false;
                    binding.editTextScoutName.setHintTextColor(getColor(R.color.red));
                    binding.editTextScoutName.setHint("Invalid scout name");
                }

                binding.editTextMatchNumber.setHintTextColor(getColor(R.color.black));
                binding.editTextMatchNumber.setHint("Match number");
                binding.editTextTeamScouting.setHintTextColor(getColor(R.color.black));
                binding.editTextMatchNumber.setHint("Team scouting");
                try {
                    QR.currEntry.matchNum =
                            Integer.parseInt(binding.editTextMatchNumber.getText().toString());
                }
                catch (NumberFormatException e) {
                    binding.editTextMatchNumber.setHintTextColor(getColor(R.color.red));
                    binding.editTextMatchNumber.setHint("Invalid match number");
                    good = false;
                }
                try {
                    QR.currEntry.teamScouting =
                            Integer.parseInt(binding.editTextTeamScouting.getText().toString());
                }
                catch (NumberFormatException e) {
                    binding.editTextTeamScouting.setHintTextColor(getColor(R.color.red));
                    binding.editTextTeamScouting.setHint("Invalid team number");
                    good = false;
                }

                teamColor = binding.toggleButtonTeamColor.isChecked();
                QR.currEntry.teamColor = teamColor ? 'R' : 'B';
                if (good) {
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
        Log.d("CALLED", scoutName);
    }


}
