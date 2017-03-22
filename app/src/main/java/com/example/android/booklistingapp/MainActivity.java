package com.example.android.booklistingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BooksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView booksListView = (ListView) findViewById(R.id.book_list);

        //Create new adapter with an empty list of books
        mAdapter = new BooksAdapter(this,new ArrayList<Books>());

        //Set adapter on the list view
        booksListView.setAdapter(mAdapter);

    }
}
