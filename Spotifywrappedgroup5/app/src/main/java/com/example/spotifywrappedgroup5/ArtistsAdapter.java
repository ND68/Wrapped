package com.example.spotifywrappedgroup5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder> {
    private List<String> artistsNames;

    public ArtistsAdapter(List<String> artistsNames) {
        this.artistsNames = artistsNames;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        String name = artistsNames.get(position);
        holder.artistNameTextView.setText(name);
    }

    @Override
    public int getItemCount() {
        return artistsNames.size();
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder {
        TextView artistNameTextView;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            artistNameTextView = itemView.findViewById(android.R.id.text1);
        }
    }
}
