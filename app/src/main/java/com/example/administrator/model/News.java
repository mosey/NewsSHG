package com.example.administrator.model;

/**
 * Created by shenhonggui1 on 2017-04-16.
 */

public class News {
    private String time;
    private String title;
    private String imgUrl;
    private String contentUrl;

    public News(){
    }

    public News(String time, String title, String imgUrl, String contentUrl) {
        this.time = time;
        this.title = title;
        this.imgUrl = imgUrl;
        this.contentUrl = contentUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
