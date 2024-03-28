package com.example.spotifywrappedgroup5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spotifywrappedgroup5.databinding.SpotifySummaryBinding;
public class SpotifySummary extends Fragment {
    private @NonNull SpotifySummaryBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        binding = SpotifySummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}