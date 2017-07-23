package com.example.bhatt.selfhelpher;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.example.bhatt.selfhelpher.Youtubeclasses.GetPlaylistTitlesAsyncTask;
import com.example.bhatt.selfhelpher.coursedatabase.CourseContract;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.Video;

import java.util.ArrayList;
import java.util.List;

public class Courselib extends AppCompatActivity implements CourselibAdpater.ListItemClickListener {

    private String subject;
    private int subjectno;

    private ArrayList<String> Youtubelinks = new ArrayList<>();

    private String[] YoutubelinksArray;

    private YouTube mYoutubeDataApi;
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();

    private ArrayList<CourselibData> mPlaylistTitles;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.courselib);


        subject = getIntent().getStringExtra("subject");
        subjectno = getIntent().getIntExtra("subjectno",5);


        recyclerView = (RecyclerView)findViewById(R.id.course_lib_recycleview);

        databasework();
        youtubework();

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

                Toast.makeText(Courselib.this,"Toast",Toast.LENGTH_SHORT).show();

                updateUI();
            }
        }.execute(YoutubelinksArray);
    }

    private void updateUI() {

        CourselibAdpater adpater = new CourselibAdpater(Courselib.this,mPlaylistTitles,Courselib.this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adpater);
    }

    private void databasework() {

        String[] project = {
                CourseContract.CourseEntry.SUBJECT,
                CourseContract.CourseEntry.COURSEID,
                CourseContract.CourseEntry.PLAYLIST };


        Cursor cursor = getContentResolver().query(
                CourseContract.CourseEntry.CONTENT_URL,
                project,
                null,
                null,
                null
        );

        try{
            int a = cursor.getColumnIndex(CourseContract.CourseEntry.SUBJECT);
            int b = cursor.getColumnIndex(CourseContract.CourseEntry.PLAYLIST);


            while(cursor.moveToNext()){

                String name = cursor.getString(a);

                if (name.equals(subject)){

                    String Youtubeplaylist = cursor.getString(b);

                    Log.e("Courselib",name);
                    Log.e("Courselib",Youtubeplaylist);

                    Youtubelinks.add(Youtubeplaylist);

                }
            }

        }finally {
            cursor.close();
            YoutubelinksArray = Youtubelinks.toArray(new String[Youtubelinks.size()]);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        String plylistid = Youtubelinks.get(clickedItemIndex);

        double Course_id = (subjectno + ((clickedItemIndex+1)/10));
        double decimal = clickedItemIndex+1;
        double decimal2 = decimal/10;

        Course_id = Course_id + decimal2;

        Toast.makeText(Courselib.this,String.valueOf(Course_id),Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Courselib.this,CourseWindow.class);
        intent.putExtra("INTENT_TAG","FromCourselib");
        intent.putExtra("plylistid",plylistid);
        intent.putExtra("subject",subject);
        intent.putExtra("Course_id",Course_id);

        startActivity(intent);

    }
}
