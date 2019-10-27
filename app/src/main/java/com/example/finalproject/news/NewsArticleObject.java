package com.example.finalproject.news;

import java.io.Serializable;

public class NewsArticleObject  {
    //instance fields for my news articles
    private long id;
    private String title;
    private String articleUrl;
    private String imageUrl;
    private String description;


    public NewsArticleObject() {
        //empty constructor

    }

    public NewsArticleObject(long id, String title, String articleUrl, String imageUrl, String description) {
        this.id = id;
        this.title=title;
        this.articleUrl=articleUrl;
        this.imageUrl=imageUrl;
        this.description=description;

    }

    public long getId() {return this.id;}
    public String getTitle() {return this.title;}
    public String getArticleUrl() {return this.articleUrl;}
    public String getDescription() {return this.description;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

