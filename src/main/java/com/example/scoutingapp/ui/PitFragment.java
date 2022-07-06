package com.example.scoutingapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import com.example.scoutingapp.MainActivity;
import com.example.scoutingapp.QR;
import com.example.scoutingapp.R;
import com.example.scoutingapp.databinding.HomeBinding;
import com.example.scoutingapp.databinding.PitscoutingBinding;

public class PitFragment extends Fragment {

    private PitscoutingBinding binding;
    private TextView tv;
    private TextView ttmp;
    private CheckBox checkTank;
    private CheckBox checkSwerve;
    private CheckBox checkMecanum;
    private CheckBox checkOther;
    private TextView editMotorNum;
    private CheckBox checkFalcon;
    private CheckBox checkNEO;
    private CheckBox checkCIM;
    private CheckBox checkMiniCIM;
    private TextView width;
    private CheckBox lowHub;
    private CheckBox highHub;
    private CheckBox noBar;
    private CheckBox lowBar;
    private CheckBox midBar;
    private CheckBox highBar;
    private CheckBox travBar;
    private CheckBox limelight;
    private CheckBox cam;
    private CheckBox custom;
    private CheckBox novision;
    private Button sub;

    private static String name = "";

    int getColor(int id) {

        return ContextCompat.getColor(getContext(), id);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = PitscoutingBinding.inflate(inflater, container, false);
        Button bSubmitPit = binding.buttonSubmitPit;
        tv = binding.editTextNamePit;
        ttmp = binding.editTextTeamNumberPit;
        checkTank = binding.checkBoxTank;
        checkSwerve = binding.checkBoxSwerve;
        checkMecanum = binding.checkBoxMecanum;
        checkOther = binding.checkBoxOther;
        editMotorNum = binding.editMotorNumber;
        checkFalcon = binding.checkBoxFalcon;
        checkNEO = binding.checkBoxNEO;
        checkCIM = binding.checkBoxCIM;
        checkMiniCIM = binding.checkBoxMiniCIM;
        width = binding.editRobotWidthHanging;
        highHub = binding.checkBoxHighHubPit;
        lowHub = binding.checkBoxLowHubPit;
        noBar = binding.checkBoxNoBarPit;
        lowBar = binding.checkBoxLowBarPit;
        midBar = binding.checkBoxMidBarPit;
        highBar = binding.checkBoxHighBarPit;
        travBar = binding.checkBoxTraversalBarPit;
        limelight = binding.checkBoxLimelight;
        cam = binding.checkBoxCamera;
        custom = binding.checkBoxCustom;
        novision = binding.checkBoxNoVision;
        sub = binding.buttonSubmitPit;

        tv.setText(name);
        bSubmitPit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding = PitscoutingBinding.inflate(inflater, container, false);
                boolean good = true;
                sub.setTextColor(getColor(R.color.black));
                sub.setText("Submit");
                QR.currPitEntry.name = name = tv.getText().toString();
                if (QR.currPitEntry.name.isEmpty()) {
                        good = false;
                }
                if (ttmp.getText().toString().isEmpty()) {
                    good = false;
                }
                else {
                    QR.currPitEntry.teamNum = Integer.valueOf(ttmp.getText().toString());
                }
                if (checkTank.isChecked()) {
                    QR.currPitEntry.drivetrainIdx = 0;
                }
                else if (checkSwerve.isChecked()) {
                    QR.currPitEntry.drivetrainIdx = 1;
                }
                else if (checkMecanum.isChecked()) {
                    QR.currPitEntry.drivetrainIdx = 2;
                }
                else if (checkOther.isChecked()) {
                    QR.currPitEntry.drivetrainIdx = 3;
                }
                else {
                    good = false;
                }
                if (editMotorNum.getText().toString().isEmpty()) {
                    good = false;
                }
                else {
                    QR.currPitEntry.motorNum = Integer.valueOf(editMotorNum.getText().toString());
                }
                if (checkFalcon.isChecked()) {
                    QR.currPitEntry.motorTypeIdx = 0;
                }
                else if (checkNEO.isChecked()) {
                    QR.currPitEntry.motorTypeIdx = 1;
                }
                else if (checkCIM.isChecked()) {
                    QR.currPitEntry.motorTypeIdx = 2;
                }
                else if (checkMiniCIM.isChecked()) {
                    QR.currPitEntry.motorTypeIdx = 3;
                }
                else {
                    good = false;
                }
                if (width.getText().toString().isEmpty()) {
                    good = false;
                }
                else {
                    QR.currPitEntry.width = Double.valueOf(width.getText().toString());
                }
                QR.currPitEntry.highHub = highHub.isChecked();
                QR.currPitEntry.lowHub = lowHub.isChecked();
                boolean onePressed = false;
                if (noBar.isChecked()) {
                    QR.currPitEntry.climbLevelIdx.add(0);
                    onePressed = true;
                }

                if (lowBar.isChecked()) {
                    QR.currPitEntry.climbLevelIdx.add(1);
                    onePressed = true;

                }
                if (midBar.isChecked()) {
                    QR.currPitEntry.climbLevelIdx.add(2);
                    onePressed = true;

                }
                if (highBar.isChecked()) {
                    QR.currPitEntry.climbLevelIdx.add(3);
                    onePressed = true;

                }
                if (travBar.isChecked()) {
                    QR.currPitEntry.climbLevelIdx.add(4);
                    onePressed = true;

                }
                if (!onePressed) {
                    good = false;
                }
                if (limelight.isChecked()) {
                    QR.currPitEntry.visionIdx = 1;
                }
                else if (cam.isChecked()) {
                    QR.currPitEntry.visionIdx = 2;
                }
                else if (custom.isChecked()) {
                    QR.currPitEntry.visionIdx = 3;
                }
                else if (novision.isChecked()) {
                    QR.currPitEntry.visionIdx = 0;
                }
                else {
                    good = false;
                }
                Log.d("PP", good ? "good" : "bad");

                if (good) {
                    QR.addCurrPitEntry();
                    MainActivity.navController.navigate(new NavDirections() {
                        @Override
                        public int getActionId() {
                            return R.id.action_navigation_refresh_pit;
                        }

                        @NonNull
                        @Override
                        public Bundle getArguments() {
                            return null;
                        }
                    });
                }
                else {
                    sub.setTextColor(getColor(R.color.red));
                    sub.setText("Invalid entry!");
                }
            }});

        Button bBack = binding.buttonBackPit;
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity.navController.navigate(new NavDirections() {
                    @Override
                    public int getActionId() {
                        return R.id.action_navigation_pitscouting_to_home;
                    }

                    @NonNull
                    @Override
                    public Bundle getArguments() {
                        return null;
                    }
                });
            }


        });
        CheckBox[] drivetraincbs = {binding.checkBoxTank, binding.checkBoxSwerve, binding.checkBoxMecanum, binding.checkBoxOther};

        CheckBox ctank = binding.checkBoxTank;
        ctank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(drivetraincbs,0);

            }


        });
        CheckBox cSwerve = binding.checkBoxSwerve;
        cSwerve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(drivetraincbs,1);

            }


        });
        CheckBox cmecanum = binding.checkBoxMecanum;
        cmecanum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(drivetraincbs,2);

            }


        });
        CheckBox cOther = binding.checkBoxOther;
        cOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(drivetraincbs,3);

            }


        });
        CheckBox[] motordrivecbs = {binding.checkBoxFalcon, binding.checkBoxNEO, binding.checkBoxCIM, binding.checkBoxMiniCIM};

        CheckBox cFalcon = binding.checkBoxFalcon;
        cFalcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(motordrivecbs,0);

            }


        });
        CheckBox cNeo = binding.checkBoxNEO;
        cNeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(motordrivecbs,1);

            }


        });
        CheckBox cCIM= binding.checkBoxCIM;
        cCIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(motordrivecbs,2);

            }


        });
        CheckBox cMiniCIM = binding.checkBoxMiniCIM;
        cMiniCIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(motordrivecbs,3);

            }


        });




        CheckBox[] visioncbs = {binding.checkBoxNoVision, binding.checkBoxLimelight, binding.checkBoxCamera, binding.checkBoxCustom};

        CheckBox cNoVision = binding.checkBoxNoVision;
        cNoVision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(visioncbs,0);

            }


        });
        CheckBox cLimelight = binding.checkBoxLimelight;
        cLimelight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(visioncbs,1);

            }


        });
        CheckBox cCamera = binding.checkBoxCamera;
        cCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(visioncbs,2);

            }


        });
        CheckBox cCustom = binding.checkBoxCustom;
        cCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlyOneChecked(visioncbs,3);

            }


        });

        return binding.getRoot();
    }

    void onlyOneChecked(CheckBox[] vals, int recent) {
        for (int i = 0; i < vals.length; i++) {
            if (i == recent) continue;
            vals[i].setChecked(false);
        }
    }
}
