package com.example.bhatt.selfhelpher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.Youtubeclasses.ApiKey;
import com.example.bhatt.selfhelpher.Youtubeclasses.PlaylistVideos;
import com.example.bhatt.selfhelpher.Youtubeclasses.YoutubeAsytask;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Lists;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.VideoSnippet;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by bhatt on 18-07-2017.
 */

public class Videolistfragment extends Fragment implements VideoListAdpater.VideoClickListener {

    View fragment1;



    private YouTube mYoutubeDataApi;
    private String YoutubeVideoplaylist;
    private String nextPageToken;

    private VideoListResponse videoListResponse;
    private Video item;

    private RecyclerView recyclerView;

    Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment1 = inflater.inflate(R.layout.videolistfragment,container,false);

        YoutubeVideoplaylist = getActivity().getIntent().getStringExtra("plylistid");
        nextPageToken = null;

        recyclerView = (RecyclerView)fragment1.findViewById(R.id.videoList_recycleview);


        if (savedInstanceState == null){

            YoutubeAsytask youtubeAsytask = new YoutubeAsytask(YoutubeVideoplaylist,nextPageToken) {

                @Override
                protected void onPostExecute(VideoListResponse videoListResponse) {
                    super.onPostExecute(videoListResponse);

                    if (videoListResponse != null){
                        Toast.makeText(getActivity(),"YOY",Toast.LENGTH_SHORT).show();
                        UIworks(videoListResponse);
                    } else {
                        Toast.makeText(getActivity(),"FUCK",Toast.LENGTH_SHORT).show();
                    }
                }
            };
            youtubeAsytask.execute();

        }

        return fragment1;
    }



    private void UIworks(VideoListResponse videoListResponse) {

        this.videoListResponse = videoListResponse;

        List<Video> itams = videoListResponse.getItems();

        VideoListAdpater adapter = new VideoListAdpater(itams,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        Toast.makeText(getActivity(),itams.get(0).getSnippet().getTitle(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("YO","YO");
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Video item = videoListResponse.getItems().get(clickedItemIndex);

        String link = item.getId().toString();

        Toast.makeText(getActivity(),link,Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();

        bundle.putString("Youtubelink",link);
        bundle.putString("Youtubetitle",item.getSnippet().getTitle());
        bundle.putString("Youtubedes",item.getSnippet().getDescription());

        intent = new Intent(getActivity(),YoutubeVideo.class);
        intent.putExtra("bundle",bundle);

        startActivity(intent);

    }
}
