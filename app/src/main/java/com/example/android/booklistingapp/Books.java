package com.example.android.booklistingapp;

/**
 * Created by Reshaud Ally on 3/21/2017.
 */

public class Books {
    private String mTitle;
    private String mPublishedDate;
    private String mAuthors;

    public Books(String title, String publishedDate, String authors) {
        this.mTitle = title;
        this.mPublishedDate = publishedDate;
        this.mAuthors = authors;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPublishedDate() {
        return mPublishedDate;
    }

    public void setmPublishedDate(String mPublishedDate) {
        this.mPublishedDate = mPublishedDate;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public void setmAuthors(String mAuthors) {
        this.mAuthors = mAuthors;
    }
}
