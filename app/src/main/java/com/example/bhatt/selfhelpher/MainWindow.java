package com.example.bhatt.selfhelpher;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhatt.selfhelpher.UserDatabase.UserContract;
import com.example.bhatt.selfhelpher.Youtubeclasses.GetPlaylistTitlesAsyncTask;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;

import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends Fragment implements CourselibAdpater.ListItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {


    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();
    View fragment2;
    CourselibAdpater adpater;
    Object actionMode = null;
    private String[] YoutubelinksArray;
    private ArrayList<String> Youtubelinks = new ArrayList<>();
    private ArrayList<CourselibData> mPlaylistTitles;
    private YouTube mYoutubeDataApi;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragment2 = inflater.inflate(R.layout.main_window, container, false);


        Log.e("BuildConfig", BuildConfig.FLAVOR);


        if (BuildConfig.FLAVOR.equals("free")) {


            AdView mAdView = (AdView) fragment2.findViewById(R.id.adView);

            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);

        }

        recyclerView = (RecyclerView) fragment2.findViewById(R.id.mainwindow_recycleview);

//        databasework();
        getLoaderManager().initLoader(2, null, this);

//        youtubework();

        return fragment2;
    }


    /*************************************************************************************/

    private void databasework() {

        String[] project = {
                UserContract.UserEntry.PLAYLIST};


        Cursor cursor = getActivity().getContentResolver().query(
                UserContract.UserEntry.CONTENT_URL,
                project,
                null,
                null,
                null
        );

        try {

            int a = cursor.getColumnIndex(UserContract.UserEntry.PLAYLIST);


            int num = 0;

            while (cursor.moveToNext()) {

                Youtubelinks.add(cursor.getString(a));

                num++;
            }


        } finally {
            cursor.close();
            YoutubelinksArray = Youtubelinks.toArray(new String[Youtubelinks.size()]);
        }
    }

    /*********************************************************************************************/

    private void youtubework() {

        mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                .setApplicationName(getResources().getString(R.string.app_name))
                .build();


        new GetPlaylistTitlesAsyncTask(mYoutubeDataApi) {

            @Override
            protected void onPostExecute(PlaylistListResponse playlistListResponse) {

                int[] androidColors = getResources().getIntArray(R.array.androidcolor);

                if (playlistListResponse == null) {
                    return;
                } else {
                    mPlaylistTitles = new ArrayList();
                    for (com.google.api.services.youtube.model.Playlist playlist : playlistListResponse.getItems()) {

                        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

                        mPlaylistTitles.add(new CourselibData(playlist.getSnippet().getTitle(), randomAndroidColor));

                    }
                }
                updateUI();
            }
        }.execute(YoutubelinksArray);
    }

    private void updateUI() {
        adpater = new CourselibAdpater(getActivity(), mPlaylistTitles, MainWindow.this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adpater);


    }


    @Override
    public void onListItemClick(int clickedItemIndex) {

        Intent intent = new Intent(getActivity(), CourseWindow.class);

        intent.putExtra("plylistid", YoutubelinksArray[clickedItemIndex]);
        intent.putExtra("INTENT_TAG", "MainWindow");

        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] project = {
                UserContract.UserEntry.PLAYLIST};


        return new CursorLoader(
                getContext(),
                UserContract.UserEntry.CONTENT_URL,
                project,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        try {

            int a = cursor.getColumnIndex(UserContract.UserEntry.PLAYLIST);

            int num = 0;

            while (cursor.moveToNext()) {

                Youtubelinks.add(cursor.getString(a));

                num++;
            }


        } finally {
            YoutubelinksArray = Youtubelinks.toArray(new String[Youtubelinks.size()]);

            youtubework();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
