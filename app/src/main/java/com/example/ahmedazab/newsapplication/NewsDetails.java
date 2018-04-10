package com.example.ahmedazab.newsapplication;


public class NewsDetails {

    //Data fields
    private String newsTitle;
    private String newsInformation;
    private String newsDate;
    private String authorName;
    private String newsUrl;

    //constructor
    public NewsDetails(String newsTitle, String newsInformation, String newsDate, String newsUrl, String authorName) {
        this.newsTitle = newsTitle;
        this.newsInformation = newsInformation;
        this.newsDate = newsDate;
        this.newsUrl = newsUrl;
        this.authorName = authorName;
    }

    //Getter methods
    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getNewsInformation() {
        return newsInformation;
    }

    public void setNewsInformation(String newsInformation) {
        this.newsInformation = newsInformation;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }
}
