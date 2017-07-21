package com.example.bhatt.selfhelpher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bhatt on 21-07-2017.
 */

public class CourselibAdpater extends RecyclerView.Adapter<CourselibAdpater.MyViewHolder> {

    ArrayList<CourselibData> mPlaylistTitles = new ArrayList<>();
    Context context;

    CourselibAdpater(Context context, ArrayList<CourselibData> arrayList,ListItemClickListener listener){
        this.context = context;
        this.mPlaylistTitles = arrayList;
        mOnClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.courselib_cardview, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CourselibData courselibData = mPlaylistTitles.get(position);

        holder.textView.setText(courselibData.getcoursename());


    }

    @Override
    public int getItemCount() {
        return mPlaylistTitles.size();
    }

    /*******************************************************************************************/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.course_lib_title);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            int clickedPostsion = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPostsion);

        }
    }

    final private ListItemClickListener mOnClickListener;

    interface ListItemClickListener { void onListItemClick(int clickedItemIndex); }
}
