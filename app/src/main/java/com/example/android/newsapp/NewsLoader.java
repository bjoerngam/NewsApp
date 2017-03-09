package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.newsapp.Utils.NewsUtils;

import java.util.List;

/**
 * Created by bjoern on 14.02.17.
 * Version:
 *
 * @author <a href="mailto:mail@bjoern.cologne">Bjoern Gam</a>
 * @link <a href="http://bjoern.cologne">Webpage </a>
 * <p>
 * Description:
 */

public class NewsLoader extends AsyncTaskLoader<List<News>>
{
    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    private String mURL;

    public NewsLoader(Context context, String url){
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mURL == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of news.
        List<News> news = NewsUtils.fetchNewsData(mURL);
        return news;
    }
}
