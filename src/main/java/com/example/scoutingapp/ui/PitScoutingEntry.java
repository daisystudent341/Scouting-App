package com.example.scoutingapp.ui;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
    SortedSet<Integer> climbLevelIdx = new TreeSet<>();
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


    private String getClimbLevels() {
        String res = "";
        for (int idx : climbLevelIdx) {
            res += climbLevels[idx] + ",";
        }
        return res.substring(0, res.length() - 1);
    }
    @NonNull
    public String toString() {
        return "" + name + SEP + teamNum + SEP + drivetrains[drivetrainIdx] + SEP + motorNum + SEP +
                motorTypes[motorTypeIdx] + SEP + formatTo2(width) + SEP + boolToBinary(highHub) +
                SEP + boolToBinary(lowHub) + SEP + getClimbLevels() + SEP +
                visionTypes[visionIdx];

    }
}
