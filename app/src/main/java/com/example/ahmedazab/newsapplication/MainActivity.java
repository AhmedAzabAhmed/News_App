package com.example.ahmedazab.newsapplication;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsDetails>> {

    public static final String TAG = MainActivity.class.getName();
    private static final int NEWS_ID = 1;
    private static final String URL_REQUEST = "https://content.guardianapis.com/search?q=debate";
    //Data fields
    private TextView tvState;
    private NewsControllerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // select view
        ListView lvNews = (ListView) findViewById(R.id.lv_News);

        // start adapter
        adapter = new NewsControllerAdapter(this, new ArrayList<NewsDetails>());
        lvNews.setAdapter(adapter);

        //select view
        tvState = (TextView) findViewById(R.id.tv_Loader);
        //set data
        lvNews.setEmptyView(tvState);

        //add action to list items
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // get earthquake details
                NewsDetails earthquakeDetails = adapter.getItem(i);

                // convert string url to url object
                Uri uriNews = Uri.parse(earthquakeDetails.getNewsUrl());

                // move to website
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, uriNews);

                //  launch the website
                startActivity(websiteIntent);
            }
        });

        // check the state of the network
        ConnectivityManager connectmanager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details of network
        NetworkInfo networkInformation = connectmanager.getActiveNetworkInfo();

        // get data
        if (networkInformation != null && networkInformation.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            // start the loader.
            loaderManager.initLoader(NEWS_ID, null, this);
        } else {
            // show the error
            View loadingIndicator = findViewById(R.id.pb_loader);
            loadingIndicator.setVisibility(View.GONE);
            // Update state of connection
            tvState.setText(R.string.nonConnection);
        }
    }

    @Override
    public Loader<List<NewsDetails>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String order_By = sharedPrefs.getString(
                getString(R.string.order_by_key),
                getString(R.string.order_by_most_recent_value));

        String orderTypes = sharedPrefs.getString(
                getString(R.string.types_key),
                getString(R.string.settings_type_sport_value));

        Uri uri = Uri.parse(URL_REQUEST);
        Uri.Builder uriBuilder = uri.buildUpon();
        if (!orderTypes.isEmpty()) {
            uriBuilder.appendQueryParameter("section", orderTypes);
            uriBuilder.appendQueryParameter("order-by", order_By);
            uriBuilder.appendQueryParameter("show-tags", "contributor");
            uriBuilder.appendQueryParameter("api-key", "test");
        }
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsDetails>> loader, List<NewsDetails> loadNews) {

        View loadingIndicator = findViewById(R.id.pb_loader);
        loadingIndicator.setVisibility(View.GONE);

        // update the text
        tvState.setText(R.string.emptyNews);

        // Clear the adapter
        adapter.clear();

        if (loadNews != null && !loadNews.isEmpty()) {
            adapter.addAll(loadNews);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<NewsDetails>> loader) {
        // reset.
        adapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
