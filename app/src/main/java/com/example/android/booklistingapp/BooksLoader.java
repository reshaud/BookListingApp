package com.example.android.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Reshaud Ally on 3/28/2017.
 */

public class BooksLoader extends AsyncTaskLoader<List<Books>> {
    private String mSearchRequest;

    public BooksLoader(Context context, String searchRequest) {
        super(context);
        this.mSearchRequest = searchRequest;
    }

    //start loading data
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Books> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null
        if (mSearchRequest == null) {
            return null;
        }

        List<Books> result = QueryUtils.fetchBooks(mSearchRequest);
        return result;
    }
}
