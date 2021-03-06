package com.example.bhatt.selfhelpher;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.Youtubeclasses.ApiKey;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    final String TAG = "YoutubeVideo";
    YouTubePlayerView playerview;
    private String Youtubelink;
    private String Youtubetitle;
    private String Youtubedes;
    private TextView titleview, desview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_video);

        titleview = (TextView) findViewById(R.id.video_Title);
        desview = (TextView) findViewById(R.id.video_Des);

        Bundle bundle = getIntent().getBundleExtra("bundle");

        Youtubelink = bundle.getString("Youtubelink");
        Youtubetitle = bundle.getString("Youtubetitle");
        Youtubedes = bundle.getString("Youtubedes");

        titleview.setText(Youtubetitle);
        desview.setText(Youtubedes);

        playerview = (YouTubePlayerView) findViewById(R.id.youtubeview);
        playerview.initialize(ApiKey.YOUTUBE_API_KEY, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        Log.e(TAG, "onInitializationSuccess provider is " + provider.getClass().toString());
        Toast.makeText(this, getResources().getText(R.string.youtube_player_done), Toast.LENGTH_LONG).show();

        if (!wasRestored) {
            youTubePlayer.loadVideo(Youtubelink);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        final int REQUEST_CODE = 1;

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show();
        } else {
            Toast.makeText(this, getResources().getText(R.string.youtube_problem), Toast.LENGTH_LONG).show();
        }
    }
}
