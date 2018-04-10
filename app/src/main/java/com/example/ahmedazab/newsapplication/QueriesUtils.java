package com.example.ahmedazab.newsapplication;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.ahmedazab.newsapplication.MainActivity.TAG;


public final class QueriesUtils {

    //constructor
    private QueriesUtils() {

    }

    private static List<NewsDetails> extractFeatureFromJson(String newsData) {

        // check  string if it is empty or not .
        if (TextUtils.isEmpty(newsData)) {
            return null;
        }

        // used to add earthquakes
        List<NewsDetails> earthquakeNews = new ArrayList<>();

        try {

            JSONObject response = new JSONObject(newsData);

            JSONObject baseResponse = response.getJSONObject("response");

            JSONArray eqFeatures = baseResponse.getJSONArray("results");

            for (int i = 0; i < eqFeatures.length(); i++) {

                JSONObject modernNews = eqFeatures.getJSONObject(i);
                String title = modernNews.getString("sectionName");
                String information = modernNews.getString("webTitle");
                String time = modernNews.getString("webPublicationDate");
                // get data from URL
                String url = modernNews.getString("webUrl");
                //*********************this is used to add author name *****************
                JSONArray name = modernNews.getJSONArray("tags");
                String authorName = "";
                Log.d("sizeofname", "" + name.length());
                for (int j = 0; j < name.length(); j++) {
                    JSONObject object = name.getJSONObject(j);
                    authorName = object.getString("webTitle");
                    NewsDetails details = new NewsDetails(title, information, time, url, authorName);
                    earthquakeNews.add(details);

                }
                //**********************************************************************
            }


        } catch (JSONException e) {

            Log.e("QueriesUtils", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakeNews;
    }


    private static URL createUrl(String myUrl) {
        URL url = null;
        try {
            url = new URL(myUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error in URL ", e);
        }
        return url;
    }


    //send request
    private static String sendHttpsRequest(URL url) throws IOException {
        String jsResponses = "";

        // If the URL is empty.
        if (url == null) {
            return jsResponses;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successfully sent,
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsResponses = getDataStream(inputStream);
            } else {
                Log.e(TAG, "Error in response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Error in retrieving results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsResponses;
    }


    private static String getDataStream(InputStream inputStream) throws IOException {
        StringBuilder outputBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader buffer = new BufferedReader(inputStreamReader);
            String dataLine = buffer.readLine();
            while (dataLine != null) {
                outputBuilder.append(dataLine);
                dataLine = buffer.readLine();
            }
        }
        return outputBuilder.toString();
    }


    public static List<NewsDetails> getDataNews(String requestUrl) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create URL
        URL url = createUrl(requestUrl);

        // send HTTP request to the URL
        String responsesJS = null;
        try {
            responsesJS = sendHttpsRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Error in HTTP request.", e);
        }

        // retrive fields from the  response .
        List<NewsDetails> mNews = extractFeatureFromJson(responsesJS);

        // Return the list of {@link Earthquake}s
        return mNews;
    }
}

