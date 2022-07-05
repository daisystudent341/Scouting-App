package com.example.scoutingapp.ui;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

public class PitScoutingEntry {
    String name;
    int teamNum;
    int drivetrainIdx;
    private static final String [] drivetrains = {"Tank", "Swerve", "Mecanum", "Other"};
    int motorNum;
    int motorTypeIdx;
    private static final String [] motorTypes = {"Falcon", "NEO", "CIM", "Mini CIM"};
    double width;
    boolean highHub;
    boolean lowHub;
    int climbLevelIdx;
    private final String[] climbLevels = {"No", "Low", "Mid", "High", "Traversal"};
    int visionIdx;
    private final String[] visionTypes = {"No", "Limelight", "Camera", "Custom"};
    private final static CharSequence SEP = "¦";


    private int boolToBinary(boolean x) {
        return (x ? 1 : 0);
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    private String formatTo2(double d) {
        return String.format("%.2f",d);
    }

    @NonNull
    public String toString() {
        return "" + name + SEP + teamNum + SEP + drivetrains[drivetrainIdx] + SEP + motorNum + SEP +
                motorTypes[motorTypeIdx] + SEP + formatTo2(width) + SEP + boolToBinary(highHub) +
                SEP + boolToBinary(lowHub) + SEP + climbLevels[climbLevelIdx] + SEP +
                visionTypes[visionIdx];

    }
}
