package com.cookandroid.ott_teacher_final;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    List<String> genres = new ArrayList<String>();
    String title;
    String original_title;
    String poster_path;
    String overview;
    String release_date;
    List<Integer> genre_ids = new ArrayList<Integer>();
    String url;
    String url2;
    int id;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        original_title = intent.getStringExtra("original_title");
        poster_path = intent.getStringExtra("poster_path");
        overview = intent.getStringExtra("overview");
        release_date = intent.getStringExtra("release_date");
        genre_ids = intent.getIntegerArrayListExtra("genre_ids");
        id = intent.getIntExtra("id", 0);
        link = intent.getStringExtra("link");

        TextView textView_title = (TextView) findViewById(R.id.mv_title);
        textView_title.setText(title);
        TextView textView_original_title = (TextView) findViewById(R.id.mv_original_title);
        textView_original_title.setText(original_title);
        ImageView imageView_poster = (ImageView) findViewById(R.id.mv_poster);
        url = "https://image.tmdb.org/t/p/w500" + poster_path;
        url2 = "https://www.themoviedb.org/movie/" + id + "/watch";

        Glide.with(this)
                .load(url)
                .centerCrop()
                .transition(
                        new DrawableTransitionOptions()
                                .crossFade()
                )
                .into(imageView_poster);

        TextView textView_overview = (TextView) findViewById(R.id.mv_overview);
        textView_overview.setText(overview);
        TextView textView_release_date = (TextView) findViewById(R.id.mv_release_date);
        textView_release_date.setText("개봉일: "+release_date);
        TextView textView_genres = (TextView) findViewById(R.id.mv_genres);
        textView_genres.setText(String.valueOf(convert(genre_ids, genres)));
        TextView textView_more = (TextView) findViewById(R.id.mv_more);
        textView_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_overview.setMaxLines(1000);
                textView_more.setText("");
            }
        });
        ImageButton tv_link = (ImageButton) findViewById(R.id.mv_link);
        tv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
                startActivity(intent1);
            }
        });
    }

    public String convert(List<Integer> genre_ids, List<String> genres) {

        for (Integer genereId : genre_ids) {
            if (genereId == 28) {
                genres.add("액션");
            } else if (genereId == 12) {
                genres.add("어드벤처");
            } else if (genereId == 16) {
                genres.add("애니메이션");
            } else if (genereId == 80) {
                genres.add("범죄");
            } else if (genereId == 99) {
                genres.add("다큐");
            } else if (genereId == 18) {
                genres.add("드라마");
            } else if (genereId == 10751) {
                genres.add("가족");
            } else if (genereId == 14) {
                genres.add("판타지");
            } else if (genereId == 36) {
                genres.add("역사");
            } else if (genereId == 27) {
                genres.add("호러");
            } else if (genereId == 10402) {
                genres.add("음악");
            } else if (genereId == 9648) {
                genres.add("미스테리");
            } else if (genereId == 10749) {
                genres.add("로맨스");
            } else if (genereId == 35) {
                genres.add("코미디");
            } else if (genereId == 878) {
                genres.add("SF");
            } else if (genereId == 10770) {
                genres.add("TV영화");
            } else if (genereId == 53) {
                genres.add("스릴러"); //스릴러
            } else if (genereId == 10752) {
                genres.add("전쟁");
            } else if (genereId == 37) {
                genres.add("서부");
            }
        }
        return TextUtils.join(", ", genres);
    }
}