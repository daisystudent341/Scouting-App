package com.example.scoutingapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import com.example.scoutingapp.ui.MatchEntry;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;


public class QR {
    public static MatchEntry currEntry = new MatchEntry();
    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private static ArrayList<MatchEntry> entries = new ArrayList<>();
    private static ArrayList<Bitmap> res = new ArrayList<>();


    private final static BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
    private final static MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    private final static CharSequence SEPARATOR = "§";

    private static final int MAX_MATCHES = 20;

    @SuppressLint({"NewApi", "LocalSuppress"})
    private static String formatEntries(int startIdx, int endIdx) {
        String res = "";
        for(int i = startIdx; i <= endIdx; i++) {
            res += entries.get(i).toString() + SEPARATOR;

        }

        return res.substring(0, res.length() - 1);
    }

    private static void updateAllQRCodes() {

        res.clear();
        int len = getNumQRCodes();

        for (int i = 0; i < len; i++) {
            int start = i * MAX_MATCHES;
            int end = Math.min(entries.size() - 1, start + MAX_MATCHES - 1);
            try {
                res.add(barcodeEncoder.createBitmap(multiFormatWriter.encode(formatEntries(start, end),
                        BarcodeFormat.QR_CODE, WIDTH, HEIGHT)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Bitmap> getQRCodes() {
        return res;
    }

    public static void deleteEntry(int idx) {
        entries.remove(idx);
        updateAllQRCodes();

    }

    public static void editEntry(int idx, MatchEntry e) {
        entries.set(idx, e);
        updateAllQRCodes();

    }


    public static void addCurrEntry() {
        entries.add(currEntry);
        currEntry.clear();

        updateAllQRCodes();
    }

    public static int getNumQRCodes() {
        return (int)Math.ceil((double)entries.size() / (double)MAX_MATCHES);
    }

}
