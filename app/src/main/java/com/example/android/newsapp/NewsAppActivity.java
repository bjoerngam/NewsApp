package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjoern on 14.02.17.
 * Version:
 *
 * @author <a href="mailto:mail@bjoern.cologne">Bjoern Gam</a>
 * @link <a href="http://bjoern.cologne">Webpage </a>
 * <p>
 * Description: Our Main Activity class
 */

public class NewsAppActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>
{
    /** Tag for log messages */
    private static final String LOG_TAG = NewsAppActivity.class.getName();

    // The basic URL for the The gurdains news API
    //private String mNews_API_LINK = "http://content.guardianapis.com/search?q=debates&api-key=test";
    private static String mNews_API_LINK = "http://content.guardianapis.com/search?";
    private static String mAPI_key ="&api-key=test";

    private NewsAdapter mAdapter;

    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_app);
        // Create a new adapter that takes an empty list of bookinglisting as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        // Find a reference to the {@link ListView} in the layout
        ListView newsList = (ListView) findViewById(R.id.list);
        newsList.setAdapter(mAdapter);

        if(isNetworkConnected()) {
            newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current earthquake that was clicked on
                    News currentNews = mAdapter.getItem(position);
                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri newsUri = Uri.parse(currentNews.getMwebUrl());
                    // Create a new intent to view the news URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, NewsAppActivity.this);
        }else{
            TextView myEmptyText = (TextView) findViewById(R.id.emptyView);
            myEmptyText.setText(getString(R.string.internet_connection));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String strElement = sharedPrefs.getString(getString(R.string.default_settings_key), getString(R.string.default_settings));
        String mComplete_URL = mNews_API_LINK+"q="+strElement+mAPI_key;
        return new NewsLoader(this, mComplete_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        // Clear the adapter of previous news data
        ProgressBar myProgress = (ProgressBar) findViewById(R.id.progressBar);
        myProgress.setVisibility(View.GONE);
        TextView emptySpace = (TextView) findViewById(R.id.emptyView);
        emptySpace.setVisibility(View.INVISIBLE);
        mAdapter.clear();

        // If there is a valid list of {@link BookingListing}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    private boolean isNetworkConnected() {
        // The helper function for checking if the network connection is working.
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
