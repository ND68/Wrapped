// Generated by view binder compiler. Do not edit!
package com.example.spotifywrappedgroup5.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.spotifywrappedgroup5.R;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SpotifySummaryBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView artistsview;

  @NonNull
  public final Button button1To2;

  @NonNull
  public final Button button2To1;

  @NonNull
  public final Button button2To3;

  @NonNull
  public final Button button3To2;

  @NonNull
  public final Button button3To4;

  @NonNull
  public final LinearLayout linearLayout3;

  @NonNull
  public final TextView lovinggg;

  @NonNull
  public final ConstraintLayout mainContainer;

  @NonNull
  public final ConstraintLayout page1;

  @NonNull
  public final ConstraintLayout page2;

  @NonNull
  public final ConstraintLayout page3;

  @NonNull
  public final TextView personalityTextView;

  @NonNull
  public final CircularProgressIndicator progressbar;

  @NonNull
  public final TextView topTrackBy;

  @NonNull
  public final ImageView topTrackImageView;

  @NonNull
  public final TextView topTrackName;

  @NonNull
  public final TextView topartiststextview;

  @NonNull
  public final TextView track1TextView;

  @NonNull
  public final TextView track2TextView;

  @NonNull
  public final TextView track3TextView;

  @NonNull
  public final TextView usernameTextView;

  @NonNull
  public final TextView yourpersonality;

  private SpotifySummaryBinding(@NonNull ConstraintLayout rootView,
      @NonNull RecyclerView artistsview, @NonNull Button button1To2, @NonNull Button button2To1,
      @NonNull Button button2To3, @NonNull Button button3To2, @NonNull Button button3To4,
      @NonNull LinearLayout linearLayout3, @NonNull TextView lovinggg,
      @NonNull ConstraintLayout mainContainer, @NonNull ConstraintLayout page1,
      @NonNull ConstraintLayout page2, @NonNull ConstraintLayout page3,
      @NonNull TextView personalityTextView, @NonNull CircularProgressIndicator progressbar,
      @NonNull TextView topTrackBy, @NonNull ImageView topTrackImageView,
      @NonNull TextView topTrackName, @NonNull TextView topartiststextview,
      @NonNull TextView track1TextView, @NonNull TextView track2TextView,
      @NonNull TextView track3TextView, @NonNull TextView usernameTextView,
      @NonNull TextView yourpersonality) {
    this.rootView = rootView;
    this.artistsview = artistsview;
    this.button1To2 = button1To2;
    this.button2To1 = button2To1;
    this.button2To3 = button2To3;
    this.button3To2 = button3To2;
    this.button3To4 = button3To4;
    this.linearLayout3 = linearLayout3;
    this.lovinggg = lovinggg;
    this.mainContainer = mainContainer;
    this.page1 = page1;
    this.page2 = page2;
    this.page3 = page3;
    this.personalityTextView = personalityTextView;
    this.progressbar = progressbar;
    this.topTrackBy = topTrackBy;
    this.topTrackImageView = topTrackImageView;
    this.topTrackName = topTrackName;
    this.topartiststextview = topartiststextview;
    this.track1TextView = track1TextView;
    this.track2TextView = track2TextView;
    this.track3TextView = track3TextView;
    this.usernameTextView = usernameTextView;
    this.yourpersonality = yourpersonality;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SpotifySummaryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SpotifySummaryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.spotify_summary, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SpotifySummaryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.artistsview;
      RecyclerView artistsview = ViewBindings.findChildViewById(rootView, id);
      if (artistsview == null) {
        break missingId;
      }

      id = R.id.button1To2;
      Button button1To2 = ViewBindings.findChildViewById(rootView, id);
      if (button1To2 == null) {
        break missingId;
      }

      id = R.id.button2To1;
      Button button2To1 = ViewBindings.findChildViewById(rootView, id);
      if (button2To1 == null) {
        break missingId;
      }

      id = R.id.button2To3;
      Button button2To3 = ViewBindings.findChildViewById(rootView, id);
      if (button2To3 == null) {
        break missingId;
      }

      id = R.id.button3To2;
      Button button3To2 = ViewBindings.findChildViewById(rootView, id);
      if (button3To2 == null) {
        break missingId;
      }

      id = R.id.button3To4;
      Button button3To4 = ViewBindings.findChildViewById(rootView, id);
      if (button3To4 == null) {
        break missingId;
      }

      id = R.id.linearLayout3;
      LinearLayout linearLayout3 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout3 == null) {
        break missingId;
      }

      id = R.id.lovinggg;
      TextView lovinggg = ViewBindings.findChildViewById(rootView, id);
      if (lovinggg == null) {
        break missingId;
      }

      id = R.id.mainContainer;
      ConstraintLayout mainContainer = ViewBindings.findChildViewById(rootView, id);
      if (mainContainer == null) {
        break missingId;
      }

      id = R.id.page1;
      ConstraintLayout page1 = ViewBindings.findChildViewById(rootView, id);
      if (page1 == null) {
        break missingId;
      }

      id = R.id.page2;
      ConstraintLayout page2 = ViewBindings.findChildViewById(rootView, id);
      if (page2 == null) {
        break missingId;
      }

      id = R.id.page3;
      ConstraintLayout page3 = ViewBindings.findChildViewById(rootView, id);
      if (page3 == null) {
        break missingId;
      }

      id = R.id.personalityTextView;
      TextView personalityTextView = ViewBindings.findChildViewById(rootView, id);
      if (personalityTextView == null) {
        break missingId;
      }

      id = R.id.progressbar;
      CircularProgressIndicator progressbar = ViewBindings.findChildViewById(rootView, id);
      if (progressbar == null) {
        break missingId;
      }

      id = R.id.topTrackBy;
      TextView topTrackBy = ViewBindings.findChildViewById(rootView, id);
      if (topTrackBy == null) {
        break missingId;
      }

      id = R.id.topTrackImageView;
      ImageView topTrackImageView = ViewBindings.findChildViewById(rootView, id);
      if (topTrackImageView == null) {
        break missingId;
      }

      id = R.id.topTrackName;
      TextView topTrackName = ViewBindings.findChildViewById(rootView, id);
      if (topTrackName == null) {
        break missingId;
      }

      id = R.id.topartiststextview;
      TextView topartiststextview = ViewBindings.findChildViewById(rootView, id);
      if (topartiststextview == null) {
        break missingId;
      }

      id = R.id.track1TextView;
      TextView track1TextView = ViewBindings.findChildViewById(rootView, id);
      if (track1TextView == null) {
        break missingId;
      }

      id = R.id.track2TextView;
      TextView track2TextView = ViewBindings.findChildViewById(rootView, id);
      if (track2TextView == null) {
        break missingId;
      }

      id = R.id.track3TextView;
      TextView track3TextView = ViewBindings.findChildViewById(rootView, id);
      if (track3TextView == null) {
        break missingId;
      }

      id = R.id.usernameTextView;
      TextView usernameTextView = ViewBindings.findChildViewById(rootView, id);
      if (usernameTextView == null) {
        break missingId;
      }

      id = R.id.yourpersonality;
      TextView yourpersonality = ViewBindings.findChildViewById(rootView, id);
      if (yourpersonality == null) {
        break missingId;
      }

      return new SpotifySummaryBinding((ConstraintLayout) rootView, artistsview, button1To2,
          button2To1, button2To3, button3To2, button3To4, linearLayout3, lovinggg, mainContainer,
          page1, page2, page3, personalityTextView, progressbar, topTrackBy, topTrackImageView,
          topTrackName, topartiststextview, track1TextView, track2TextView, track3TextView,
          usernameTextView, yourpersonality);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
