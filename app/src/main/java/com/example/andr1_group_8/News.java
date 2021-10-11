package com.example.andr1_group_8;

public class News {

    private String pubDate;
    private String title;
    private String thumbnail;
    private String link;
    private String content;

    public News(String date, String title, String thumbnail, String link, String content){
        this.pubDate = date;
        this.title = title;
        this.thumbnail = thumbnail;
        this.link = link;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getLink() {
        return link;
    }

    public String getContent() {
        return content;
    }
}
