package com.cookandroid.ott_teacher_final;

import java.util.List;

public class Movie {
    //리퀘스로 받아 오는 Json 데이터를 참조해서 만들자.
    private String title;
    private String original_title;
    private String poster_path;
    private String overview;
    private String backdrop_path;
    private String release_date;
    private List<Integer> genre_ids;
    private int id;

    public Movie(String title, String original_title, String poster_path, String overview,
                 String backdrop_path, String release_date, List<Integer> genre_ids, int id) {
        this.title = title;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public String getOriginal_title() {
        return original_title;
    }
    public String getPoster_path() {
        return poster_path;
    }
    public String getOverview() {
        return overview;
    }
    public String getBackdrop_path() {
        return backdrop_path;
    }
    public String getRelease_date() {
        return release_date;
    }
    public List<Integer> getGenre_Ids() {
        return genre_ids;
    }
    public int getId() {return id;}
}

