package com.example.scoutingapp.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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
import com.example.scoutingapp.databinding.QrdataBinding;

import java.util.ArrayList;


public class QRDataFragment extends Fragment {
    private QrdataBinding binding;
    ArrayList<Bitmap> qrCodes = QR.getQRCodes();
    int currQR = 0;

    /*
    call after edit or delete
     */
    private void updateQRCodes() {
        qrCodes = QR.getQRCodes();
    }


    private void updateQRView() {

        if (currQR == 0) {
            binding.imageView.setVisibility(View.INVISIBLE);
        }
        else {
            binding.imageView.setImageBitmap(qrCodes.get(currQR - 1));
        }
    }

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = QrdataBinding.inflate(inflater, container, false);


        if (QR.getNumQRCodes() > 0) currQR = 1;
        else currQR = 0;

        updateQRCodes();
        updateQRView();
        binding.textViewNumQRCodes.setText(currQR + "/" + QR.getNumQRCodes());



        Button bNext = binding.buttonNextQR;
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(currQR + 1 > QR.getNumQRCodes())) {
                    currQR++;
                    binding.textViewNumQRCodes.setText(currQR + "/" + QR.getNumQRCodes());
                    updateQRView();
                }



            }

        });

        Button bPrev = binding.buttonPrevQR;
        bPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(currQR - 1 < 1)) {
                    currQR--;
                    binding.textViewNumQRCodes.setText(currQR + "/" + QR.getNumQRCodes());
                    updateQRView();
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
                        return R.id.action_navigation_qrdata_to_inputinformation;
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



}
