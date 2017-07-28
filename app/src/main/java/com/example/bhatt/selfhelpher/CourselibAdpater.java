package com.example.bhatt.selfhelpher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bhatt on 21-07-2017.
 */

public class CourselibAdpater extends RecyclerView.Adapter<CourselibAdpater.MyViewHolder> {

    final private ListItemClickListener mOnClickListener;
    ArrayList<CourselibData> mPlaylistTitles = new ArrayList<>();
    Context context;
    int lastPosition = -1;
    private SparseBooleanArray selectedItems;

    public CourselibAdpater(Context context, ArrayList<CourselibData> arrayList, ListItemClickListener listener) {
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
        holder.imageView.setBackgroundColor(courselibData.getcolor());

        if (position > lastPosition) {

            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.up_from_bottom);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }

    }

    @Override
    public int getItemCount() {
        return mPlaylistTitles.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    /*******************************************************************************************/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.course_lib_title);
            imageView = (ImageView) itemView.findViewById(R.id.course_lib_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int clickedPostsion = getAdapterPosition();

            mOnClickListener.onListItemClick(clickedPostsion);
        }
    }
}
