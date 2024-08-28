package com.cookandroid.ott_teacher_final;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

public class MyRecyclerViewAdapter2 extends RecyclerView.Adapter<MyRecyclerViewAdapter2.RecyclerViewHolders2>{

    private ArrayList<Tv> mTvList;
    private LayoutInflater mInflate;
    private Context mContext;
    //constructor
    public MyRecyclerViewAdapter2(Context context, ArrayList<Tv> itemList) {
        this.mContext = context;
        this.mInflate = LayoutInflater.from(context);
        this.mTvList = itemList;


    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter2.RecyclerViewHolders2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.list_item, parent, false);
        MyRecyclerViewAdapter2.RecyclerViewHolders2 viewHolder2 = new MyRecyclerViewAdapter2.RecyclerViewHolders2(view);
        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter2.RecyclerViewHolders2 holder, int position) {
        //?ъ뒪?곕쭔 異쒕젰?섏옄.
        String url = "https://image.tmdb.org/t/p/w500" + mTvList.get(position).getPoster_path();

        Glide.with(mContext)
                .load(url)
                .centerCrop()
                .transition(
                        new DrawableTransitionOptions()
                                .crossFade()
                )
                .into(holder.imageView);
        //媛??꾩씠???대┃ ?대깽??
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAbsoluteAdapterPosition();
                Intent intent = new Intent(mContext, DetailActivity2.class);
                intent.putExtra("name", mTvList.get(adapterPosition).getName());
                intent.putExtra("original_name", mTvList.get(adapterPosition).getOriginal_name());
                intent.putExtra("poster_path", mTvList.get(adapterPosition).getPoster_path());
                intent.putExtra("overview", mTvList.get(adapterPosition).getOverview());
                intent.putExtra("first_air_date", mTvList.get(adapterPosition).getFirst_air_date());
                intent.putIntegerArrayListExtra("genre_ids", (ArrayList<Integer>) mTvList.get(adapterPosition).getGenre_Ids());
                intent.putExtra("id",mTvList.get(adapterPosition).getId());
                mContext.startActivity(intent);
                Log.d("Adapter", "Clcked: " + holder.getAbsoluteAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mTvList.size();
    }


    //酉고???- ?곕줈 ?대옒???뚯씪濡?留뚮뱾?대룄 ?쒕떎.
    public static class RecyclerViewHolders2 extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ActionBar.Tab type;

        public RecyclerViewHolders2(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
