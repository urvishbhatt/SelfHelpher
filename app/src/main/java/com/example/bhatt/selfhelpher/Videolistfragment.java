package com.example.bhatt.selfhelpher;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.Youtubeclasses.YoutubeAsytask;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import java.util.List;
import java.util.Random;

/**
 * Created by bhatt on 18-07-2017.
 */

public class Videolistfragment extends Fragment implements VideoListAdpater.VideoClickListener {

    View fragment1;
    Intent intent;
    private YouTube mYoutubeDataApi;
    private String YoutubeVideoplaylist;
    private String nextPageToken;
    private VideoListResponse videoListResponse;
    private Video item;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment1 = inflater.inflate(R.layout.videolistfragment, container, false);

        YoutubeVideoplaylist = getActivity().getIntent().getStringExtra("plylistid");

        nextPageToken = null;

        recyclerView = (RecyclerView) fragment1.findViewById(R.id.videoList_recycleview);


        if (savedInstanceState == null) {

            int[] androidColors = getResources().getIntArray(R.array.androidcolor);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

            YoutubeAsytask youtubeAsytask = new YoutubeAsytask(YoutubeVideoplaylist, nextPageToken) {

                @Override
                protected void onPostExecute(VideoListResponse videoListResponse) {
                    super.onPostExecute(videoListResponse);

                    if (videoListResponse != null) {
                        UIworks(videoListResponse);
                    } else {
                        Toast.makeText(getActivity(), getResources().getText(R.string.youtube_problem), Toast.LENGTH_SHORT).show();
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

        int[] androidColors = getResources().getIntArray(R.array.androidcolor);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        VideoListAdpater adapter = new VideoListAdpater(itams, this, randomAndroidColor, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("YO", "YO");
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {


        Video item = videoListResponse.getItems().get(clickedItemIndex);

        String link = item.getId().toString();


        if (CourseWindow.issecondfragment) {

            //////youtube details fragment////////////////////////////////////////
            YoutubeFragmentDetails fragment2 = new YoutubeFragmentDetails();
            Bundle bundle = new Bundle();
            bundle.putString("Youtubetitle", item.getSnippet().getTitle());
            bundle.putString("Youtubedes", item.getSnippet().getDescription());
            fragment2.setArguments(bundle);
            getFragmentManager().beginTransaction().add(R.id.fragment2, fragment2).commit();

            //////youtube player fragment///////////////////////////////////////////
            YoutubeFragmentVideo fragment3 = new YoutubeFragmentVideo();
            Bundle bundle1 = new Bundle();
            bundle1.putString("Youtubelink", link);
            fragment3.setArguments(bundle1);
            getFragmentManager().beginTransaction().add(R.id.fragment3, fragment3).commit();


        } else {

            ////start youtube player activity////////////////////////////////////////
            Bundle bundle = new Bundle();
            bundle.putString("Youtubelink", link);
            bundle.putString("Youtubetitle", item.getSnippet().getTitle());
            bundle.putString("Youtubedes", item.getSnippet().getDescription());
            intent = new Intent(getActivity(), YoutubeVideo.class);
            intent.putExtra("bundle", bundle);
            startActivity(intent);

        }


    }
}
