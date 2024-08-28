package com.cookandroid.ott_teacher_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TvMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter2 adapter2;
    ArrayList<Tv> tvList;
    ArrayList<Provider> providerList = new ArrayList<Provider>();
    Toolbar myToolbar;
    Button backBtn, changeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tvList = new ArrayList<Tv>();

        //Asynctask - OKHttp
        String main_url = "https://api.themoviedb.org/3/tv/top_rated?api_key=35cdae8ef9784a8bc8bc71d865c937a0&language=ko-KR&page=1";
        String[] strings = {main_url};
        MyAsyncTask mAsyncTask = new MyAsyncTask();
        mAsyncTask.execute(strings[0]);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(TvMainActivity.this, 2);  //한 열에 포스터 몇개인지
        recyclerView.setLayoutManager(gridLayoutManager);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main_url = "https://api.themoviedb.org/3/tv/top_rated?api_key=35cdae8ef9784a8bc8bc71d865c937a0&language=ko-KR&page=1";
                String[] strings = {main_url};
                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(strings[0]);
            }
        });
        changeBtn = (Button) findViewById(R.id.changeBtn);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("제목을 입력하세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            //寃?됱뼱瑜????낅젰?섍퀬 ?쒖튂 踰꾪듉???뚮??꾨븣
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(TvMainActivity.this, s + "에 관한 작품을 검색합니다.", Toast.LENGTH_LONG).show();
                String sn = s.toString();
                //?ш린??AsyncTask瑜??댁슜 寃??由ы섏뒪?몃줈 ?곗씠?곕? 諛쏆븘 ?ㅺ쾶 泥섎━ ?섏옄. - AsyncTask 怨듭쑀?좉쾬.
                String search_url = "https://api.themoviedb.org/3/search/tv?api_key=35cdae8ef9784a8bc8bc71d865c937a0&query="+sn+"&language=ko-KR&page=1";
                String[] strings = {search_url};
                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(strings[0]);
                return false;
            }
            //寃???낅젰李쎌뿉 ?덈줈???띿뒪?멸? ?ㅼ뼱媛덈븣 留덈떎 ?몄텧 - ?ш린???꾩슂 ?놁쓬
            @Override
            public boolean onQueryTextChange(String s) {
                //Log.d("Search", "keyword: " + s);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.action_search:
                //Toast.makeText(this, "action_search", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_light:
                //Toast.makeText(this, "action_movie", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_dark:
                //Toast.makeText(this, "action_tv", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "", Toast.LENGTH_LONG).show();
                return true;
            default:
                Toast.makeText(this, "default", Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
    public class MyAsyncTask extends AsyncTask<String, Void, Tv[]> {
        //濡쒕뵫以??쒖떆
        ProgressDialog progressDialog = new ProgressDialog(TvMainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\t로딩중...");
            //show dialog
            progressDialog.show();
            //紐⑸줉 諛곗뿴???댁슜???щ━?????볥뒗??
            tvList.clear();
        }
        @Override
        protected Tv[] doInBackground(String... strings) {
            Log.d("AsyncTask", "url : " + strings[0]);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream())
                        .getAsJsonObject().get("results");
                Tv[] posts = gson.fromJson(rootObject, Tv[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Tv[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //ArrayList??李⑤??濡?吏묒뼱 ?ｋ뒗??
            if (result.length > 0) {
                for (Tv p : result) {
                    tvList.add(p);
                }
            }
            //?대떟???ㅼ젙
            adapter2 = new MyRecyclerViewAdapter2(TvMainActivity.this, tvList);
            recyclerView.setAdapter(adapter2);
        }
    }
}

