package com.example.spotifywrappedgroup5;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class SettingsPageDirections {
  private SettingsPageDirections() {
  }

  @NonNull
  public static NavDirections actionSettingsPageToLandingPage() {
    return new ActionOnlyNavDirections(R.id.action_settingsPage_to_LandingPage);
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
