package com.example.spotifywrappedgroup5;

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
}
