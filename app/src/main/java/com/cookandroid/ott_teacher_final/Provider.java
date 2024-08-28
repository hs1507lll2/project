package com.cookandroid.ott_teacher_final;

public class Provider {
    private String link;
    private String logo_path;

    public Provider(String link, String logo_path) {
        this.link = link;
        this.logo_path = logo_path;
    }

    public String getLink() {return link;}
    public String getLogo_path() {return logo_path;}
}
