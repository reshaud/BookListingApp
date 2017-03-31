package com.example.android.booklistingapp;

/**
 * Created by Reshaud Ally on 3/21/2017.
 */

public class Books {
    private String mTitle;
    private String mPublishedDate;
    private String mAuthors;
    private String mPreview;

    public Books(String title, String publishedDate, String authors, String preview) {
        this.mTitle = title;
        this.mPublishedDate = publishedDate;
        this.mAuthors = authors;
        this.mPreview = preview;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public String getmPreview() {
        return mPreview;
    }
}
