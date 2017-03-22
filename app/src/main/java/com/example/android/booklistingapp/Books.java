package com.example.android.booklistingapp;

import java.util.List;

/**
 * Created by Reshaud Ally on 3/21/2017.
 */

public class Books {
    private String mTitle;
    private String mPublishedDate;
    private List<String> mAuthors;

    public Books(String title,String publishedDate, List<String> authors){
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

    public List<String> getmAuthors() {
        return mAuthors;
    }

    public void setmAuthors(List<String> mAuthors) {
        this.mAuthors = mAuthors;
    }
}
