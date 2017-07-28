package com.example.bhatt.selfhelpher;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.Youtubeclasses.ApiKey;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;


/**
 * Created by bhatt on 25-07-2017.
 */

public class YoutubeFragmentVideo extends Fragment {


    View fragment3;
    String TAG = "YoutubeFragmentVideo";
    YouTubePlayerFragment youTubePlayerFragment;
    private String Youtubelink;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        fragment3 = inflater.inflate(R.layout.youtubefragmentview, container, false);

        Youtubelink = getArguments().getString("Youtubelink");

        youTubePlayerFragment = new YouTubePlayerFragment();

        getFragmentManager().beginTransaction().add(R.id.youtube_fragment, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(ApiKey.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

                Log.e(TAG, "onInitializationSuccess provider is " + provider.getClass().toString());
                Toast.makeText(getActivity(), getResources().getText(R.string.youtube_player_done), Toast.LENGTH_LONG).show();


                if (!wasRestored) {
                    youTubePlayer.loadVideo(Youtubelink);
                }

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                final int REQUEST_CODE = 1;

                if (youTubeInitializationResult.isUserRecoverableError()) {
                    youTubeInitializationResult.getErrorDialog(getActivity(), REQUEST_CODE).show();
                } else {
                    String errorMessager = String.format("there was error initialize the YoutubePlayer (%1s)", youTubeInitializationResult.toString());
                    Toast.makeText(getActivity(), errorMessager, Toast.LENGTH_LONG).show();
                }
            }
        });

        return fragment3;
    }


}
