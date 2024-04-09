package com.example.spotifywrappedgroup5;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class NavGraphDirections {
  private NavGraphDirections() {
  }

  @NonNull
  public static NavDirections actionGlobalSpotifySummary() {
    return new ActionOnlyNavDirections(R.id.action_global_SpotifySummary);
  }

  @NonNull
  public static NavDirections actionGlobalSettingsPage() {
    return new ActionOnlyNavDirections(R.id.action_global_settingsPage);
  }
}
