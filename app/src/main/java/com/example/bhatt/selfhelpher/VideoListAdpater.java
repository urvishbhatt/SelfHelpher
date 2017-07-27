package com.example.bhatt.selfhelpher;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.youtube.model.Video;

import java.util.List;

/**
 * Created by bhatt on 22-07-2017.
 */

public class VideoListAdpater extends RecyclerView.Adapter<VideoListAdpater.MyViewHolder> {

    List<Video> itams;
    Context context;

    private int randomAndroidColor;

    int lastPosition = -1;

    public VideoListAdpater(List<Video> itams, VideoClickListener context, int randomAndroidColor, Context context1) {

        this.itams = itams;
        ClickListener = context;
        this.context = context1;
        this.randomAndroidColor = randomAndroidColor;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.videolistfragment_card, parent, false);

        return new VideoListAdpater.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.numberview.setText(String.valueOf(position+1));
        holder.titleview.setText(itams.get(position).getSnippet().getTitle());

        String isodate = itams.get(position).getContentDetails().getDuration();
        Log.e("isodate",isodate);
        String min = isodate.substring(isodate.indexOf("T") + 1, isodate.indexOf("M"));
        String sec = isodate.substring(isodate.indexOf("M") + 1, isodate.indexOf("S"));
        String length = min + "." + sec;

        holder.lengthview.setText(length);
        holder.imageView.setBackgroundColor(randomAndroidColor);


        if(position >lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.up_from_bottom);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return itams.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView numberview,titleview,lengthview;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            numberview = (TextView) itemView.findViewById(R.id.video_number);
            titleview = (TextView) itemView.findViewById(R.id.video_name);
            lengthview = (TextView) itemView.findViewById(R.id.video_length);
            imageView = (ImageView) itemView.findViewById(R.id.video_list);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int clickedPostsion = getAdapterPosition();
            ClickListener.onListItemClick(clickedPostsion);
        }
    }

    public VideoClickListener ClickListener = null;

    interface VideoClickListener { void onListItemClick(int clickedItemIndex); }

}
