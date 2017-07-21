package com.example.bhatt.selfhelpher;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.Youtubeclasses.ApiKey;
import com.example.bhatt.selfhelpher.Youtubeclasses.GetPlaylistAsyncTask;
import com.example.bhatt.selfhelpher.Youtubeclasses.PlaylistVideos;
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

public class Videolistfragment extends Fragment {

    View fragment1;

    private YouTube mYoutubeDataApi;
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();


    private String YoutubeVideoplaylist;

    private YouTube youtube;


    String name;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment1 = inflater.inflate(R.layout.videolistfragment,container,false);

        YoutubeVideoplaylist = "PLWn8QRqY8ySxWJ8XTVafnIATXrr6XN5JZ";

        mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                .setApplicationName(getResources().getString(R.string.app_name))
                .build();

        Networkingtask task = new Networkingtask() {};
        task.execute();

        return fragment1;
    }

    abstract class Networkingtask extends AsyncTask<Void,Void,String>{


        private static final String TAG = "GetPlaylistAsyncTask";
        private final Long YOUTUBE_PLAYLIST_MAX_RESULTS = 10L;

        //see: https://developers.google.com/youtube/v3/docs/playlistItems/list
        private static final String YOUTUBE_PLAYLIST_PART = "snippet";
        private static final String YOUTUBE_PLAYLIST_FIELDS = "pageInfo,nextPageToken,items(id,snippet(resourceId/videoId))";
        //see: https://developers.google.com/youtube/v3/docs/videos/list
        private static final String YOUTUBE_VIDEOS_PART = "snippet,contentDetails,statistics"; // video resource properties that the response will include.
        private static final String YOUTUBE_VIDEOS_FIELDS = "items(id,snippet(title,description,thumbnails/high),contentDetails/duration,statistics)"; // selector specifying which fields to include in a partial response.



        final String nextPageToken = null;


        @Override
        protected String doInBackground(Void... params) {




            PlaylistItemListResponse playlistItemListResponse;
            try {
                playlistItemListResponse = mYoutubeDataApi.playlistItems()
                        .list(YOUTUBE_PLAYLIST_PART)
                        .setPlaylistId(YoutubeVideoplaylist)
                        .setPageToken(nextPageToken)
                        .setFields(YOUTUBE_PLAYLIST_FIELDS)
                        .setMaxResults(YOUTUBE_PLAYLIST_MAX_RESULTS)
                        .setKey(ApiKey.YOUTUBE_API_KEY)
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            if (playlistItemListResponse == null) {
                Log.e(TAG, "Failed to get playlist");
                return null;
            }

            List<String> videoIds = new ArrayList();

            // pull out the video id's from the playlist page
            for (PlaylistItem item : playlistItemListResponse.getItems()) {
                videoIds.add(item.getSnippet().getResourceId().getVideoId());
            }

            String a = videoIds.get(0);

            return a;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s.isEmpty()){

                Toast.makeText(getActivity(),"is empty",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(),"not empty",Toast.LENGTH_SHORT).show();
                Log.e("I_AM_FUCKED",s);
                Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
            }

        }
    }
}
