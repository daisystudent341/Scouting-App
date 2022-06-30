package com.example.scoutingapp;

import android.graphics.Bitmap;

import com.example.scoutingapp.ui.MatchEntry;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class QR {
    public static MatchEntry currEntry;

    private static ArrayList<MatchEntry> entries = new ArrayList<>();
    private final static BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
    private final static MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    private final static char SEPARATOR = '|';
    private final static char TERMINATOR = ';';
    private static final int WIDTH = 0;
    private static final int HEIGHT = 0;
    private static final int MAX_MATCHES = 20;

    private static String formatEntries(int startIdx, int endIdx) {
        StringBuilder res = new StringBuilder();
        for(int i = startIdx; i <= endIdx; i++) {
            res.append(entries.get(i).toString() + SEPARATOR);
        }
        res.setCharAt(res.length() - 1, TERMINATOR);
        return res.toString();
    }

    public static ArrayList<Bitmap> getQRCodesFromEntries() {
        entries.add(currEntry);
        currEntry.clear();

        ArrayList<Bitmap> res = new ArrayList<>();
        int len = (int)Math.ceil((double)entries.size() / (double)MAX_MATCHES);

        for (int i = 0; i < len; i++) {
            int start = i * MAX_MATCHES;
            int end = start + MAX_MATCHES - 1;
            try {
                res.add(barcodeEncoder.createBitmap(multiFormatWriter.encode(formatEntries(start, end),
                        BarcodeFormat.QR_CODE, WIDTH, HEIGHT)));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static void clearEntries() {
        entries.clear();
    }
}
