package com.example.spotifywrappedgroup5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.spotifywrappedgroup5.databinding.SpotifySummaryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;
import com.spotify.sdk.android.auth.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Handler mainHandler = new Handler();

    //** put all views here to make them global**
    //** views that aren't global variables can't be accessed by function **
    ProgressBar progressBar;

    ConstraintLayout container;
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
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");

        String uid = auth.getCurrentUser().getUid();
        //System.out.println(uid);
        HashMap userData = new HashMap<>();

        Query checkUserDatabase = reference.orderByChild("uid").equalTo(uid);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nameFromDB = snapshot.child(uid).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(uid).child("email").getValue(String.class);
                    String accessCodeFromDB = snapshot.child(uid).child("accessCode").getValue(String.class);

                    userData.put("name",  nameFromDB);
                    userData.put("email",  emailFromDB);
                    userData.put("accessCode",  accessCodeFromDB);
                    //System.out.println(userData.get("name"));
                } else {
                    System.out.println("snapshot does not exists");
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        getToken();
        // **instantiate all views here**
        // **make sure the views are also global variables**
        progressBar = view.findViewById(R.id.progressbar);
        container = view.findViewById(R.id.main_layout);
        progressBar.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);

        usernameTextView = view.findViewById(R.id.usernameTextView);
        profilePicImageView = view.findViewById(R.id.userProfilePic);
    }


    public void start() {
        // **put all api calls here**
        // **put actual function code at the bottom of the page**

        displayUserProfile();
        progressBar.setVisibility(View.INVISIBLE);
        container.setVisibility(View.VISIBLE);
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
            System.out.println(mAccessToken + mAccessCode);
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

                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(getActivity(), "Failed to parse data, please relaunch app",
                            Toast.LENGTH_SHORT).show();
                }

            }

        });

        while(json[0] == null) {
            //wait until json returns before returning json
        }
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

    class FetchImage extends Thread {
        String URL;
        Bitmap bitmap;
        ImageView imageView;
        public FetchImage(ImageView imageView, String URL) {
            this.imageView = imageView;
            this.URL = URL;
        }
        @Override
        public void run() {

            InputStream inputStream = null;
            try {
                inputStream = new URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

    public void displayUserProfile() {
        JSONObject profileJSON = getJSON("https://api.spotify.com/v1/me");

        // Set to text in profileTextView.
        try {
            String userName = profileJSON.get("display_name").toString();
            setTextAsync(userName, usernameTextView);

            String picURL = profileJSON.getJSONArray("images").getJSONObject(0).get("url").toString();
            new FetchImage(profilePicImageView, picURL).start();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error displaying data" + e, Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
    }
}