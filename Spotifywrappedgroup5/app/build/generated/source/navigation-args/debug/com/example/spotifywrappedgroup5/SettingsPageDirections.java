package com.example.spotifywrappedgroup5;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;

public class SettingsPageDirections {
  private SettingsPageDirections() {
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
