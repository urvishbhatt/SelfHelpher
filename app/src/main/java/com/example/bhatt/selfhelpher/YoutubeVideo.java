package com.example.bhatt.selfhelpher;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.Youtubeclasses.ApiKey;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private String Youtubelink;
    private String Youtubetitle;
    private String Youtubedes;

    private TextView titleview,desview;

    YouTubePlayerView playerview;

    final String TAG = "YoutubeVideo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_video);


        titleview = (TextView)findViewById(R.id.video_Title);
        desview = (TextView)findViewById(R.id.video_Des);

        Bundle bundle = getIntent().getBundleExtra("bundle");

        Youtubelink = bundle.getString("Youtubelink");
        Youtubetitle = bundle.getString("Youtubetitle");
        Youtubedes = bundle.getString("Youtubedes");


        Log.e("LOLO",Youtubetitle);
        Log.e("LOLO",Youtubelink);

        titleview.setText(Youtubetitle);
        desview.setText(Youtubedes);

        playerview = (YouTubePlayerView) findViewById(R.id.youtubeview);
        playerview.initialize(ApiKey.YOUTUBE_API_KEY,this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        Log.e(TAG,"onInitializationSuccess provider is " + provider.getClass().toString());
        Toast.makeText(this,"Initialization youtube player Success",Toast.LENGTH_LONG).show();


        if (!wasRestored){
            youTubePlayer.loadVideo(Youtubelink);
        }


    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        final int REQUEST_CODE = 1;

        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this,REQUEST_CODE).show();
        } else {
            String errorMessager = String.format("there was error initialize the YoutubePlayer (%1s)",youTubeInitializationResult.toString());
            Toast.makeText(this,errorMessager,Toast.LENGTH_LONG).show();
        }

    }
}
