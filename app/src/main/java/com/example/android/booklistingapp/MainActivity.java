package com.example.android.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<Books>> {

    private static final int LOADER_ID = 0;
    private static final String STATE_LIST = "booksArrayList";
    private BooksAdapter mAdapter;
    private String searchURL;
    private ProgressBar progressBar;
    private TextView emptyView;
    private ArrayList<Books> booksArrayList;
    private boolean restoreData = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView booksListView = (ListView) findViewById(R.id.book_list);
        emptyView = (TextView) findViewById(R.id.empty_list);

        //Set view for when list is empty
        booksListView.setEmptyView(emptyView);

        if (!(hasNetworkConnectivity(this))) {
            //No network connectivity
            emptyView.setText(R.string.connection_error);
        } else {
            emptyView.setText(R.string.initial_empty_list);
        }

        //Create new adapter with an empty list of books
        mAdapter = new BooksAdapter(this, new ArrayList<Books>());

        //Set adapter on the list view
        booksListView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            //If saved state exists restore it
            booksArrayList = (ArrayList<Books>) savedInstanceState.getSerializable(STATE_LIST);

            //if books array list is not empty add them back to the adapter
            if (booksArrayList != null) {
                mAdapter.addAll(booksArrayList);
            }
        }

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Books currentBook = mAdapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookPreview = Uri.parse(currentBook.getmPreview());

                //Activity will move to restoreData state
                restoreData = true;

                //Intent to view book preview
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookPreview);
                startActivity(websiteIntent);
            }
        });
    }

    @Override
    public android.content.Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
        return new BooksLoader(this, searchURL);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<Books>> loader, List<Books> booksList) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        progressBar.setVisibility(View.GONE);

        // If there is a valid list of Books, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (booksList != null && !booksList.isEmpty()) {
            booksArrayList = (ArrayList<Books>) booksList;
            mAdapter.addAll(booksArrayList);
        } else {
            emptyView.setText(R.string.empty_list);
            booksArrayList.clear();
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<Books>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    public void onSearchIconClicked(View view) {
        EditText searchText = (EditText) findViewById(R.id.search_text);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //Get inputManager
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //Hide Keyboard
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //If network connectivity exists then search for books
        if (hasNetworkConnectivity(this)) {
            //Start progessBar
            progressBar.setVisibility(View.VISIBLE);

            //Clear text in emptyView since we are initiating a new search
            emptyView.setText("");

            //Remove all spaces from user inputted text and new lines
            searchURL = String.valueOf(searchText.getText());
            searchURL = searchURL.replace(" ", "");
            searchURL = searchURL.replace("\n", "");


            if (getLoaderManager().getLoader(LOADER_ID) == null) {
                getLoaderManager().initLoader(LOADER_ID, null, this);
            } else {
                mAdapter.clear();
                getLoaderManager().restartLoader(LOADER_ID, null, this);
            }

        } else {
            //No Network connectivity. Clear adapter and let user know
            mAdapter.clear();
            booksArrayList.clear();
            emptyView.setText(R.string.connection_error);
        }
    }

    //Checks Network Connectivity
    public boolean hasNetworkConnectivity(Context context) {
        //Init connectivity manager which is used to check if we have internet access
        ConnectivityManager check = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Get network information
        NetworkInfo networkInfo = check.getActiveNetworkInfo();

        return networkInfo != null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //Call super method to save state of views
        super.onSaveInstanceState(outState);

        if (!restoreData) {
            outState.putSerializable(STATE_LIST, booksArrayList);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        restoreData = false;
    }
}
