package com.example.scoutingapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.scoutingapp.databinding.PitscoutingBinding;

public class PitFragment extends Fragment {

    private PitscoutingBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return binding.getRoot();
    }
}
