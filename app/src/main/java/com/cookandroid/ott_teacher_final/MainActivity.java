package com.cookandroid.ott_teacher_final;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView, recyclerView2;
    private MyRecyclerViewAdapter adapter;
    ArrayList<Movie> movieList;
    ArrayList<Provider> providerList = new ArrayList<Provider>();
    Toolbar myToolbar;
    Button backBtn, changeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        movieList = new ArrayList<Movie>();

        String main_url = "https://api.themoviedb.org/3/movie/upcoming?api_key=35cdae8ef9784a8bc8bc71d865c937a0&language=ko-KR&page=1";
        String[] strings = {main_url};
        MyAsyncTask mAsyncTask = new MyAsyncTask();
        mAsyncTask.execute(strings[0]);

        //세로 스크롤
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);  //한 열에 포스터 몇개인지
        recyclerView.setLayoutManager(gridLayoutManager);

        String main_url2 = "https://api.themoviedb.org/3/movie/now_playing?api_key=35cdae8ef9784a8bc8bc71d865c937a0&language=ko-KR&page=1";
        String[] strings2 = {main_url2};
        MyAsyncTask myAsyncTask2 = new MyAsyncTask();
        myAsyncTask2.execute(strings2[0]);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(gridLayoutManager2);

        //가로 스크롤
        /*LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);*/

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String main_url = "https://api.themoviedb.org/3/movie/upcoming?api_key=35cdae8ef9784a8bc8bc71d865c937a0&language=ko-KR&page=1";
                String[] strings = {main_url};
                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(strings[0]);
            }
        });
        changeBtn = (Button) findViewById(R.id.changeBtn);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TvMainActivity.class);
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
            //검색어를 다 입력하고 서치 버튼을 눌렀을 때
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this, s + "에 대한 작품을 검색합니다!", Toast.LENGTH_LONG).show();
                String sn = s.toString();
                //여기서 AsyncTask를 이용 검색 리퀘스트로 데이터를 받아 오게 처리 하자. - AsyncTask 공유할것.
                String search_url = "https://api.themoviedb.org/3/search/movie?api_key=35cdae8ef9784a8bc8bc71d865c937a0&query="+sn+"&language=ko-KR&page=1";
                String[] strings = {search_url};
                MyAsyncTask mAsyncTask = new MyAsyncTask();
                mAsyncTask.execute(strings[0]);
                return false;
            }
            //검색 입력창에 새로운 텍스트가 들어갈때 마다 호출 - 여기선 필요 없음
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
    public class MyAsyncTask extends AsyncTask<String, Void, Movie[]> {
        //로딩중 표시
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("\t로딩중...");
            //show dialog
            progressDialog.show();
            //목록 배열의 내용을 클리어 해놓기
            movieList.clear();
        }
        @Override
        protected Movie[] doInBackground(String... strings) {
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
                Movie[] posts = gson.fromJson(rootObject, Movie[].class);
                return posts;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Movie[] result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            //ArrayList에 차례대로 집어넣음
            if (result.length > 0) {
                for (Movie p : result) {
                    movieList.add(p);
                }
            }
            //Adapter 설정
            adapter = new MyRecyclerViewAdapter(MainActivity.this, movieList);
            recyclerView.setAdapter(adapter);
        }
    }
}

