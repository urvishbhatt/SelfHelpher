package com.example.bhatt.selfhelpher;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bhatt.selfhelpher.UserDatabase.UserContract;
import com.example.bhatt.selfhelpher.Youtubeclasses.GetPlaylistTitlesAsyncTask;
import com.example.bhatt.selfhelpher.coursedatabase.CourseContract;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;

import java.util.ArrayList;

public class MainWindow extends Fragment implements CourselibAdpater.ListItemClickListener {


    private String[] YoutubelinksArray;

    private ArrayList<String> Youtubelinks = new ArrayList<>();



    private ArrayList<CourselibData> mPlaylistTitles;

    private YouTube mYoutubeDataApi;
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();

    private RecyclerView recyclerView;

    View fragment2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment2 = inflater.inflate(R.layout.main_window,container,false);

        recyclerView = (RecyclerView)fragment2.findViewById(R.id.mainwindow_recycleview);

        databasework();
        youtubework();


        return fragment2;
    }

    private void databasework() {

        String[] project = {
                UserContract.UserEntry.PLAYLIST };


        Cursor cursor = getActivity().getContentResolver().query(
                UserContract.UserEntry.CONTENT_URL,
                project,
                null,
                null,
                null
        );

        try{

            int a = cursor.getColumnIndex(UserContract.UserEntry.PLAYLIST);


            int num = 0;

            while(cursor.moveToNext()){

                Youtubelinks.add(cursor.getString(a));

                Toast.makeText(getActivity(),Youtubelinks.get(num),Toast.LENGTH_SHORT).show();
                num++;
            }

        }finally {
            cursor.close();
            YoutubelinksArray = Youtubelinks.toArray(new String[Youtubelinks.size()]);
        }
    }

    private void youtubework() {

        mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                .setApplicationName(getResources().getString(R.string.app_name))
                .build();


        new GetPlaylistTitlesAsyncTask(mYoutubeDataApi){

            @Override
            protected void onPostExecute(PlaylistListResponse playlistListResponse) {

                if (playlistListResponse == null) {
                    return;
                }else {
                    mPlaylistTitles = new ArrayList();
                    for (com.google.api.services.youtube.model.Playlist playlist : playlistListResponse.getItems()) {

                        mPlaylistTitles.add(new CourselibData(playlist.getSnippet().getTitle()));

                    }
                }

                Toast.makeText(getActivity(),"Toast in Mainwindow",Toast.LENGTH_SHORT).show();

                updateUI();
            }
        }.execute(YoutubelinksArray);
    }

    private void updateUI() {
        CourselibAdpater adpater = new CourselibAdpater(getActivity(),mPlaylistTitles,MainWindow.this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adpater);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(getActivity(),CourseWindow.class);

        intent.putExtra("plylistid",YoutubelinksArray[clickedItemIndex]);
        intent.putExtra("INTENT_TAG","MainWindow");

        startActivity(intent);
    }
}
