package com.example.scoutingapp.ui;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

public class MatchEntry {
    private final static CharSequence SEP = "¦";

    public int matchNum = 0;
    public String scoutName = "Name";
    public int teamScouting = 0;
    public char teamColor = 'B'; // B = blue, R = red

    public int highScoredAuton = 0;
    public int highMissedAuton = 0;
    public int lowScoredAuton = 0;
    public int lowMissedAuton = 0;
    public boolean taxi = false;
    public boolean interactsWithOtherTeamAuton = false;

    public int highScoredTeleop = 0;
    public int highMissedTeleop = 0;
    public int lowScoredTeleop = 0;
    public int lowMissedTeleop = 0;
    public double climbTime = 0;
    public int climbLevel = 0; // 0 (no climb) to 4 (traversal)

    private long climbStartMS = 0;


    private final String[] climb = {"No", "Low", "Mid", "High", "Traversal"};

    public void startClimb() {
        climbStartMS = System.currentTimeMillis();
    }

    public String currClimbTimeDeltaFormatted() {
        double tDelta = (double)(System.currentTimeMillis() - climbStartMS) / 1000.0f;
        return formatTo2(tDelta);
    }

    public void endClimb() {
        climbTime = (double)(System.currentTimeMillis() - climbStartMS) / 1000.0f;
    }


    private int boolToBinary(boolean x) {
        return (x ? 1 : 0);
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    private String formatTo2(double d) {
        return String.format("%.2f",d);
    }


    private String getClimbLevel() {
        return climb[climbLevel];
    }

    @NonNull
    public String toString() {

        return "" + matchNum + SEP + scoutName + SEP + teamScouting + SEP + teamColor + SEP +
                highScoredAuton + SEP + highMissedAuton + SEP + lowScoredAuton + SEP +
                lowMissedAuton + SEP + boolToBinary(taxi) + SEP +
                boolToBinary(interactsWithOtherTeamAuton) + SEP + highScoredTeleop + SEP +
                highMissedTeleop + SEP + lowScoredTeleop + SEP + lowMissedTeleop + SEP +
                formatTo2(climbTime) + SEP + getClimbLevel();

    }

}
