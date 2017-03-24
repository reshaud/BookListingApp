package com.example.android.booklistingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private BooksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView booksListView = (ListView) findViewById(R.id.book_list);
        ImageButton searchButton = (ImageButton) findViewById(R.id.search_icon);
        final EditText searchText = (EditText) findViewById(R.id.search_text);

        //Create new adapter with an empty list of books
        mAdapter = new BooksAdapter(this, new ArrayList<Books>());

        //Set adapter on the list view
        booksListView.setAdapter(mAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), searchText.getText(), Toast.LENGTH_SHORT).show();

                //Get inputManager
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                //Hide Keyboard
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

    }
}
