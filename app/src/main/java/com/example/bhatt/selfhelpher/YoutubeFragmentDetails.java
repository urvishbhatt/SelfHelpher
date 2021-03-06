package com.example.bhatt.selfhelpher;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bhatt on 25-07-2017.
 */

public class YoutubeFragmentDetails extends Fragment {

    final String TAG = "YoutubeVideo";
    View fragment2;
    private String Youtubetitle;
    private String Youtubedes;
    private TextView titleview, desview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        fragment2 = inflater.inflate(R.layout.youtubefragment, container, false);

        titleview = (TextView) fragment2.findViewById(R.id.video_Title);
        desview = (TextView) fragment2.findViewById(R.id.video_Des);


        Youtubetitle = getArguments().getString("Youtubetitle");
        Youtubedes = getArguments().getString("Youtubedes");

        titleview.setText(Youtubetitle);
        desview.setText(Youtubedes);


        return fragment2;
    }
}