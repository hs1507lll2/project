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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity2 extends AppCompatActivity {

    List<String> genres = new ArrayList<String>();
    String name;
    String original_name;
    String poster_path;
    String overview;
    String first_air_date;
    List<Integer> genre_ids = new ArrayList<Integer>();
    String url;
    String url2;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        original_name = intent.getStringExtra("original_name");
        poster_path = intent.getStringExtra("poster_path");
        overview = intent.getStringExtra("overview");
        first_air_date = intent.getStringExtra("first_air_date");
        genre_ids = intent.getIntegerArrayListExtra("genre_ids");
        id = intent.getIntExtra("id", 0);

        TextView textView_title1 = (TextView) findViewById(R.id.tv_title);
        textView_title1.setText(name);
        TextView textView_original_title1 = (TextView) findViewById(R.id.tv_original_name);
        textView_original_title1.setText(original_name);
        ImageView imageView_poster1 = (ImageView) findViewById(R.id.iv_poster);
        url = "https://image.tmdb.org/t/p/w500" + poster_path;
        url2 = "https://www.themoviedb.org/tv/" + id + "/watch";

        Glide.with(this)
                .load(url)
                .centerCrop()
                .transition(
                        new DrawableTransitionOptions()
                                .crossFade()
                )
                .into(imageView_poster1);

        TextView textView_overview1 = (TextView) findViewById(R.id.tv_overview);
        textView_overview1.setText(overview);
        TextView textView_first_air_date = (TextView) findViewById(R.id.tv_first_air_date);
        textView_first_air_date.setText("방영일: "+first_air_date);
        TextView textView_genres1 = (TextView) findViewById(R.id.tv_genres);
        textView_genres1.setText(String.valueOf(convert(genre_ids, genres)));
        TextView textView_more1 = (TextView) findViewById(R.id.tv_more);
        textView_more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_overview1.setMaxLines(1000);
                textView_more1.setText("");
            }
        });
        ImageButton tv_link = (ImageButton) findViewById(R.id.tv_link);
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
