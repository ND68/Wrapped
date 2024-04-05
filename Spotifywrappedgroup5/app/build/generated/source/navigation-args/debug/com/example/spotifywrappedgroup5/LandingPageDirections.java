package com.example.spotifywrappedgroup5;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class LandingPageDirections {
  private LandingPageDirections() {
  }

  @NonNull
  public static NavDirections actionLandingPageToSignUpPage() {
    return new ActionOnlyNavDirections(R.id.action_LandingPage_to_SignUpPage);
  }

  @NonNull
  public static NavDirections actionLandingPageToSpotifySummary() {
    return new ActionOnlyNavDirections(R.id.action_LandingPage_to_SpotifySummary);
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
