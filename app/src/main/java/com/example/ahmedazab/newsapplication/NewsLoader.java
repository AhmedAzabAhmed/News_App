package com.example.ahmedazab.newsapplication;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsDetails>> {

    //Data field
    private String newsUrl;


    //costructor
    public NewsLoader(Context context, String url) {
        super(context);
        newsUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsDetails> loadInBackground() {
        if (newsUrl == null) {
            return null;
        }
        // send request
        List<NewsDetails> newsDetails = QueriesUtils.getDataNews(newsUrl);
        return newsDetails;
    }
}
