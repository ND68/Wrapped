package com.example.Wrapped;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;

public class SignupPageDirections {
  private SignupPageDirections() {
  }

  @NonNull
  public static NavDirections actionSignUpPageToLandingPage() {
    return new ActionOnlyNavDirections(R.id.action_SignUpPage_to_LandingPage);
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
