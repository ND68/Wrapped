package com.example.spotifywrappedgroup5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spotifywrappedgroup5.databinding.SpotifySummaryBinding;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.sql.SQLOutput;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SpotifySummary extends Fragment {
    public static final String CLIENT_ID = "4cf685333f204e4fadde2561002b308a";
    public static final String REDIRECT_URI = "spotifywrappedgroup5://auth";

    public static final int AUTH_TOKEN_REQUEST_CODE = 0;
    public static final int AUTH_CODE_REQUEST_CODE = 1;

    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken, mAccessCode;
    private Call mCall;
    private @NonNull SpotifySummaryBinding binding;

    //** put all views here to make them global**
    //** views that aren't global variables can't be accessed by function **
    private TextView usernameTextView;
    private ImageView profilePicImageView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = SpotifySummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getToken();

        // **instantiate all views here**
        // **make sure the views are also global variables**
        usernameTextView = view.findViewById(R.id.usernameTextView);
        profilePicImageView = view.findViewById(R.id.userProfilePic);
    }


    public void start() {
        // **put all api calls here**
        // **put actual function code at the bottom of the page**

        displayUserProfile();
    }

    /**
     * Get token from Spotify
     * This method will open the Spotify login activity and get the token
     * What is token?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getToken() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.TOKEN);
        // To start LoginActivity from a Fragment:
        Intent intent = AuthorizationClient.createLoginActivityIntent(getActivity(), request);
        startActivityForResult(intent, AUTH_TOKEN_REQUEST_CODE);

        // To close LoginActivity
        AuthorizationClient.stopLoginActivity(getActivity(), AUTH_TOKEN_REQUEST_CODE);
    }

    /**
     * Get code from Spotify
     * This method will open the Spotify login activity and get the code
     * What is code?
     * https://developer.spotify.com/documentation/general/guides/authorization-guide/
     */
    public void getCode() {
        final AuthorizationRequest request = getAuthenticationRequest(AuthorizationResponse.Type.CODE);
        // To start LoginActivity from a Fragment:
        Intent intent = AuthorizationClient.createLoginActivityIntent(getActivity(), request);
        startActivityForResult(intent, AUTH_CODE_REQUEST_CODE);

        // To close LoginActivity
        AuthorizationClient.stopLoginActivity(getActivity(), AUTH_CODE_REQUEST_CODE);
    }


    /**
     * When the app leaves this activity to momentarily get a token/code, this function
     * fetches the result of that external activity to get the response from Spotify
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);

        // Check which request code is present (if any)
        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
            mAccessToken = response.getAccessToken();
            getCode();

        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
            mAccessCode = response.getCode();
            start();
        }
    }

    /**
     * Get user profile
     * This method will get the user profile using the token
     */
    public JSONObject getJSON(String url) {
        if (mAccessToken == null) {
            Toast.makeText(getActivity(), "Error Accessing Access Token", Toast.LENGTH_SHORT).show();
            return null;
        }

        // Create a request to get the user profile
        final Request request = new Request.Builder()
                // URL here. Added artists
                .url(url)
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        final JSONObject[] json = new JSONObject[1];

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
                Toast.makeText(getActivity(), "Failed to fetch data, watch Logcat for more details",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    // Access JSON response here.
                    json[0] = new JSONObject(response.body().string());
                    //System.out.println(json[0]);

                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(getActivity(), "Failed to parse data, watch Logcat for more details",
                            Toast.LENGTH_SHORT).show();
                }

            }

        });
        try {
            Thread.sleep(300);
        } catch (Exception e) {

        }

        //System.out.println(json[0]);
        return json[0];
    }


    /**
     * Creates a UI thread to update a TextView in the background
     * Reduces UI latency and makes the system perform more consistently
     *
     * @param text the text to set
     * @param textView TextView object to update
     */

    /**
     * Get authentication request
     *
     * @param type the type of the request
     * @return the authentication request
     */
    private AuthorizationRequest getAuthenticationRequest(AuthorizationResponse.Type type) {
        return new AuthorizationRequest.Builder(CLIENT_ID, type, getRedirectUri().toString())
                .setShowDialog(false)
                .setScopes(new String[] { "user-top-read" }) // <--- Change the scope of your requested token here
                .setCampaign("your-campaign-token")
                .build();
    }

    private void setTextAsync(final String text, TextView textView) {
        if (isAdded()) {
            getActivity().runOnUiThread(() -> textView.setText(text));
        }
    }

    /**
     * Gets the redirect Uri for Spotify
     *
     * @return redirect Uri object
     */
    private Uri getRedirectUri() {
        return Uri.parse(REDIRECT_URI);
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void displayUserProfile() {
        JSONObject profileJSON = getJSON("https://api.spotify.com/v1/me");

        // Set to text in profileTextView.
        try {
            String userName = profileJSON.get("display_name").toString();
            setTextAsync(userName, usernameTextView);


        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error displaying data" + e, Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
    }
}