package com.cookandroid.ott_teacher_final;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerViewHolders>{

    private ArrayList<Movie> mMovieList;
    private LayoutInflater mInflate;
    private Context mContext;
    //constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<Movie> itemList) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mMovieList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.list_item, parent, false);
        RecyclerViewHolders viewHolder = new RecyclerViewHolders(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders holder, int position) {
        //포스터만 출력하자.
        String url = "https://image.tmdb.org/t/p/w500" + mMovieList.get(position).getPoster_path();

        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .transition(
                        new DrawableTransitionOptions()
                                .crossFade()
                )
                .into(holder.imageView);

        //각 아이템 클릭 이벤트
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAbsoluteAdapterPosition();
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("title", mMovieList.get(adapterPosition).getTitle());
                intent.putExtra("original_title", mMovieList.get(adapterPosition).getOriginal_title());
                intent.putExtra("poster_path", mMovieList.get(adapterPosition).getPoster_path());
                intent.putExtra("overview", mMovieList.get(adapterPosition).getOverview());
                intent.putExtra("release_date", mMovieList.get(adapterPosition).getRelease_date());
                intent.putIntegerArrayListExtra("genre_ids", (ArrayList<Integer>) mMovieList.get(adapterPosition).getGenre_Ids());
                intent.putExtra("id",mMovieList.get(adapterPosition).getId());
                mContext.startActivity(intent);
                Log.d("Adapter", "Clcked: " + holder.getAbsoluteAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mMovieList.size();
    }
    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ActionBar.Tab type;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}

