package com.example.ahmedazab.newsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class NewsControllerAdapter extends ArrayAdapter<NewsDetails> {

    public NewsControllerAdapter(Context context, List<NewsDetails> aNews) {
        super(context, 0, aNews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //check for list
        View lvItems = convertView;
        if (lvItems == null) {
            lvItems = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        // get news
        NewsDetails newsInfo = getItem(position);

        // select text views to set title
        TextView titleView = lvItems.findViewById(R.id.tv_Title);
        titleView.setText(newsInfo.getNewsTitle());

        // select text views to set information
        TextView informationView = lvItems.findViewById(R.id.tv_INFO);
        informationView.setText(newsInfo.getNewsInformation());


        //***********************author name***************************
        TextView author = lvItems.findViewById(R.id.tv_Author);
        author.setText(newsInfo.getAuthorName());
        //*************************************************************

        // select text views to set date
        TextView dateView = lvItems.findViewById(R.id.tv_Date);
        dateView.setText("News Date is " + newsInfo.getNewsDate());

        return lvItems;

    }
}
