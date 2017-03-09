package com.example.android.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bjoern on 14.02.17.
 * Version:
 *
 * @author <a href="mailto:mail@bjoern.cologne">Bjoern Gam</a>
 * @link <a href="http://bjoern.cologne">Webpage </a>
 * <p>
 * Description: Our NewsAdapter its responsiable for setting the information at our news_list
 */

public class NewsAdapter extends ArrayAdapter<News>
{

    /** Tag for log messages */
    private static final String LOG_TAG = NewsAdapter.class.getName();

    /**
     * Our default constructor for the NewsAdapter
     * @param context
     * @param newsList
     */
    public NewsAdapter (Context context, List<News> newsList) {
        super(context, 0, newsList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);
            holder.webTitle = (TextView) convertView.findViewById(R.id.webTitle);
            holder.webPublicationDate = (TextView) convertView.findViewById(R.id.webPublicationDate);
            holder.sectionName = (TextView) convertView.findViewById(R.id.sectionName);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        // Find the current news at the given position in the list of news
        News currentNews = getItem(position);
        holder.webPublicationDate.setText(currentNews.getMwebPublicationDate());
        holder.webTitle.setText(currentNews.getMwebTitle());
        holder.sectionName.setText(currentNews.getMsectionName());
        return convertView;
    }

    static class ViewHolder {
        // The ViewHolder design pattern
        TextView webPublicationDate;
        TextView webTitle;
        TextView sectionName;
    }
}
