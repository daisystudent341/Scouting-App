package com.example.scoutingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.scoutingapp.databinding.ActivityMainBinding;

public class MainActivity extends FragmentActivity {
    @SuppressLint("StaticFieldLeak")
    public static NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this,
                R.id.nav_host);
    }
}