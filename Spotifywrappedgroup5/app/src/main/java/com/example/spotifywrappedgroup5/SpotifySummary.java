package com.example.spotifywrappedgroup5;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import androidx.recyclerview.widget.LinearLayoutManager;


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
    LottieAnimationView lottieAnimationView;
    private HashMap<String, Integer> genreCountMap = new HashMap<>();

    private Handler mainHandler = new Handler();

    MediaPlayer m;

    //** put all views here to make them global**
    //** views that aren't global variables can't be accessed by function **
    ProgressBar progressBar;
    ConstraintLayout container;
    private TextView usernameTextView;
    //private ImageView profilePicImageView;
    private TextView artistname;
    private RecyclerView artistsview;
    private ImageView topTrackImageView;
    private ImageView topArtistView;

    private TextView topTrackName;
    private TextView topTrackBy;
    private ConstraintLayout page1;
    private ConstraintLayout page2;
    private ConstraintLayout page3;


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
        container = view.findViewById(R.id.mainContainer);
        progressBar.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);

        usernameTextView = view.findViewById(R.id.usernameTextView);
        //profilePicImageView = view.findViewById(R.id.userProfilePic);
        artistsview = view.findViewById(R.id.artistsview);

        topTrackImageView = view.findViewById(R.id.topTrackImageView);
        //topArtistView = view.findViewById(R.id.topArtistView);
        topTrackName = view.findViewById(R.id.topTrackName);
        topTrackBy = view.findViewById(R.id.topTrackBy);
        lottieAnimationView = view.findViewById(R.id.fireworksAnimationView);

        Button button1to2 = view.findViewById(R.id.button1To2);
        Button button2to1 = view.findViewById(R.id.button2To1);
        Button button2to3 = view.findViewById(R.id.button2To3);
        Button button3to2 = view.findViewById(R.id.button3To2);
        page1 = view.findViewById(R.id.page1);
        page2 = view.findViewById(R.id.page2);
        page2.setVisibility(View.INVISIBLE);
        page3 = view.findViewById(R.id.page3);
        page3.setVisibility(View.INVISIBLE);
        button1to2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.INVISIBLE);
                page2.setVisibility(View.VISIBLE);
            }
        });

        button2to1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page2.setVisibility(View.INVISIBLE);
                page1.setVisibility(View.VISIBLE);
            }
        });

        button2to3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page2.setVisibility(View.INVISIBLE);
                page3.setVisibility(View.VISIBLE);
            }
        });

        button3to2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page3.setVisibility(View.INVISIBLE);
                page2.setVisibility(View.VISIBLE);
            }
        });
    }


    public void start() {
        // **put all api calls here**
        // **put actual function code at the bottom of the page**

        displayUserProfile();
        displayTopArtists();
        displayTopTrack();

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

        final Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + mAccessToken)
                .build();

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        final JSONObject[] json = new JSONObject[1];
        boolean loading = true;

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
//                    if (json[0] == null) {
//                        System.out.println("JSON is null");
//                    } else {
//                        System.out.println(json[0].toString(3));
//                    }

                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                    Toast.makeText(getActivity(), "Failed to parse data, please relaunch app",
                            Toast.LENGTH_SHORT).show();
                }

            }

        });

        System.out.println("gotten JSON");
        while(json[0] == null) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //wait until json returns before returning json
        }
        System.out.println("returning JSON");
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
                .setScopes(new String[] {"user-top-read" }) // <--- Change the scope of your requested token here
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
        stopPlaying();
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

    public void startAudioStream(String url) {
        if (m == null)
            m = new MediaPlayer();
        try {
            Log.d("TAG", "Playing: " + url);
            m.setAudioStreamType(AudioManager.STREAM_MUSIC);
            m.setDataSource(url);
            //descriptor.close();
            m.prepare();
            m.setVolume(1f, 1f);
            m.setLooping(false);
            m.start();
        } catch (Exception e) {
            Log.d("TAG", "Error playing in SoundHandler: " + e.toString());
        }
    }

    private void stopPlaying() {
        if (m != null && m.isPlaying()) {
            m.stop();
            m.release();
            m = new MediaPlayer();
            m.reset();
        }
    }

    public void displayUserProfile() {
        JSONObject profileJSON = getJSON("https://api.spotify.com/v1/me");

        // Set to text in profileTextView.
        try {
            String userName = profileJSON.get("display_name").toString();
            setTextAsync(String.format("%s,", userName), usernameTextView);

//            String picURL = profileJSON.getJSONArray("images").getJSONObject(0).get("url").toString();
//            new FetchImage(profilePicImageView, picURL).start();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error displaying data" + e, Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
    }

    public void displayTopArtists() {
        JSONObject topArtists = getJSON("https://api.spotify.com/v1/me/top/artists?limit=5");
        try {
            JSONArray items = topArtists.getJSONArray("items");
            ArrayList<String> artistsNames = new ArrayList<>();
            ArrayList<String> imageUrls = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");
                artistsNames.add(name);
                String imageUrl = artist.getJSONArray("images").getJSONObject(0).get("url").toString();
                imageUrls.add(imageUrl);
                JSONArray genresArray = artist.getJSONArray("genres");
                StringBuilder genresStringBuilder = new StringBuilder();
                for (int j = 0; j < genresArray.length(); j++) {
                    genresStringBuilder.append(genresArray.getString(j));
                    if (j < genresArray.length() - 1) {
                        genresStringBuilder.append(", ");
                    }
                    String genre = genresArray.getString(j);
                    genreCountMap.put(genre, genreCountMap.getOrDefault(genre, 0) + 1);
                }
                String genres = genresStringBuilder.toString();
                int popularity = artist.getInt("popularity");
                TextView artistInfoTextView = new TextView(getActivity());
                artistInfoTextView.setText(String.format("%s\nGenres: %s\nPopularity: %d\n\n", name, genres, popularity));
            }
            ArtistsAdapter adapter = new ArtistsAdapter(artistsNames, imageUrls);
            artistsview.setAdapter(adapter);
            artistsview.setLayoutManager(new LinearLayoutManager(getActivity())); // Don't forget to set the LayoutManager
            listeningPersonality();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error displaying data" + e, Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
    }
    public void displayTopGenres() {
        JSONObject topGenres = getJSON("https://api.spotify.com/v1/me/top/artists");
        try {
            JSONArray items = topGenres.getJSONArray("items");
            ArrayList<String> genresName = new ArrayList<>();
            for (int i = 0; i < items.length(); i++) {
                JSONObject artist = items.getJSONObject(i);
                String genres = artist.getString("genres");
                genresName.add(genres);
                JSONArray genresArray = artist.getJSONArray("genres");
                StringBuilder genresStringBuilder = new StringBuilder();
                for (int j = 0; j < genresArray.length(); j++) {
                    genresStringBuilder.append(genresArray.getString(j));
                    if (j < genresArray.length() - 1) {
                        genresStringBuilder.append(", ");
                    }
                }

            }
            ArtistsAdapter adapter = new ArtistsAdapter(genresName, null);
            artistsview.setAdapter(adapter);
            artistsview.setLayoutManager(new LinearLayoutManager(getActivity())); // Don't forget to set the LayoutManager

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error displaying data" + e, Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
    }

    public void displayTopTrack() {
        System.out.println("getting track");
        JSONObject topTracks = getJSON("https://api.spotify.com/v1/me/top/tracks?offset=1&limit=3");
        System.out.println("track gotten");

        try {
            JSONObject trackObject = topTracks.getJSONArray("items").getJSONObject(0);
            JSONObject album = trackObject.getJSONObject("album");

            String trackName = trackObject.get("name").toString();
            String albumName = album.get("name").toString();
            setTextAsync(String.format("%s - %s", trackName, albumName), topTrackName);

            String albumImageURL = album.getJSONArray("images").getJSONObject(0).get("url").toString();
            new FetchImage(topTrackImageView, albumImageURL).start();

            String trackBy = trackObject.getJSONArray("artists").getJSONObject(0).get("name").toString();
            setTextAsync(String.format("By: %s", trackBy), topTrackBy);

            String songURL = trackObject.get("preview_url").toString();
            startAudioStream(songURL);

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error displaying data" + e, Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
    }

    public void getSongRec() {

        // Hard coded values (CHANGE THIS TO MEET THE LOGIC OF THE APP):
        String seedGenres = "classical,country";
        String seedTracks = "0c6xIDDpzE81m2q797ordA";

        String url = String.format("https://api.spotify.com/v1/recommendations?limit=10&market=US&seed_artists=%s&seed_genres=%s&seed_tracks=%s",
                seedArtists, seedGenres, seedTracks);

        JSONObject recommendations = getJSON(url);
        try {
            JSONArray tracks = recommendations.getJSONArray("tracks");
            for (int i = 0; i < tracks.length(); i++) {
                JSONObject track = tracks.getJSONObject(i);
                String trackName = track.getString("name");
                String artistName = track.getJSONArray("artists").getJSONObject(0).getString("name");
                System.out.println("Recommended track: " + trackName + " by " + artistName);
            }
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Error parsing recommendations: " + e.getMessage(), Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
    }
    // Method to display listening personality
    public void listeningPersonality() {
        // Determine the user's listening personality based on their top genres
        String userPersonality = determinePersonality(genreCountMap);

        // Assuming there's a TextView in your layout with the id personalityTextView
        TextView personalityTextView = getView().findViewById(R.id.personalityTextView);
        personalityTextView.setText(userPersonality);

//        lottieAnimationView.setAnimation("fireworks.json");
//         lottieAnimationView.playAnimation();
    }

    private String determinePersonality(HashMap<String, Integer> genreCountMap) {
        // Logic to determine the most common genre or a set of genres
        // Then map that to a listening personality
        // For example:
        String mostCommonGenre = getMostCommonGenre(genreCountMap);

        // Now map the most common genre to a personality
        // This mapping is purely illustrative; you need to define it based on your own rules
        String personality;
        switch (mostCommonGenre) {
            case "rock":
                personality = "The Rocker";
                break;
                case "pop":
                    personality = "The Pop Enthusiast";
                    break;
                    case "pluggnb":
                    personality = "The pluggnb Enthusiast";
                    break;
                case "k-pop girl group":
                    personality = "The K-Pop Stan";
                    break;
                case "jazz":
                    personality = "The Deep Driver";
                    break;
                    case "rap":
                personality = "The Devotee";
                break;
            default:
                personality = "The Eclectic";
        }
        return personality;
    }

    private String getMostCommonGenre(HashMap<String, Integer> genreCountMap) {
        // Logic to find out the most common genre from the map
        Map.Entry<String, Integer> mostCommonEntry = null;
        for (Map.Entry<String, Integer> entry : genreCountMap.entrySet()) {
            if (mostCommonEntry == null || entry.getValue().compareTo(mostCommonEntry.getValue()) > 0) {
                mostCommonEntry = entry;
            }
        }
        return mostCommonEntry != null ? mostCommonEntry.getKey() : "Various";
    }}
