package com.example.spotifywrappedgroup5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spotifywrappedgroup5.databinding.LandingPageBinding;
import com.example.spotifywrappedgroup5.databinding.SettingsPageBinding;

public class SettingsPage extends Fragment {

    private SettingsPageBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = SettingsPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}