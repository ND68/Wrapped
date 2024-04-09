// Generated by view binder compiler. Do not edit!
package com.example.spotifywrappedgroup5.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.spotifywrappedgroup5.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SettingsPageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button deleteAccount;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final TextView textView;

  @NonNull
  public final Button updateEmail;

  @NonNull
  public final Button updatePass;

  private SettingsPageBinding(@NonNull ConstraintLayout rootView, @NonNull Button deleteAccount,
      @NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull Button updateEmail,
      @NonNull Button updatePass) {
    this.rootView = rootView;
    this.deleteAccount = deleteAccount;
    this.linearLayout = linearLayout;
    this.textView = textView;
    this.updateEmail = updateEmail;
    this.updatePass = updatePass;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SettingsPageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SettingsPageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.settings_page, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SettingsPageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.delete_account;
      Button deleteAccount = ViewBindings.findChildViewById(rootView, id);
      if (deleteAccount == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = ViewBindings.findChildViewById(rootView, id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.update_email;
      Button updateEmail = ViewBindings.findChildViewById(rootView, id);
      if (updateEmail == null) {
        break missingId;
      }

      id = R.id.update_pass;
      Button updatePass = ViewBindings.findChildViewById(rootView, id);
      if (updatePass == null) {
        break missingId;
      }

      return new SettingsPageBinding((ConstraintLayout) rootView, deleteAccount, linearLayout,
          textView, updateEmail, updatePass);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
