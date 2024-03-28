package com.example.spotifywrappedgroup5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spotifywrappedgroup5.databinding.SpotifySummaryBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpotifySummary extends Fragment {
    private static final String TOP_ARTISTS_URL = "https://api.spotify.com/v1/me/top/artists?limit=5";
    private String accessToken;

    public SpotifySummary(String accessToken) {
        this.accessToken = accessToken;
    }
    private @NonNull SpotifySummaryBinding binding;
    public List<Artist> fetchTopArtists() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(TOP_ARTISTS_URL)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        // Parse the JSON response to extract the list of artists
        Gson gson = new Gson();
        Type artistListType = new TypeToken<List<Artist>>(){}.getType();
        List<Artist> artistList = gson.fromJson(jsonData, artistListType);

        return artistList;
    }
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