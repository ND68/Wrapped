package com.example.spotifywrappedgroup5;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

public class SpotifySummaryDirections {
  private SpotifySummaryDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalSpotifySummary() {
    return NavGraphDirections.actionGlobalSpotifySummary();
  }

  @NonNull
  public static NavDirections actionGlobalSettingsPage() {
    return NavGraphDirections.actionGlobalSettingsPage();
  }
}
