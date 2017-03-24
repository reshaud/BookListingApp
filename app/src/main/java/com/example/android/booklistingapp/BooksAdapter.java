package com.example.android.booklistingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Reshaud Ally on 3/22/2017.
 */

public class BooksAdapter extends ArrayAdapter<Books> {

    //Constructor
    public BooksAdapter(Context context, List<Books> booksList) {
        super(context, 0, booksList);
    }


    //Returns a list item view that displays information about a book at a given location
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        //if there is an existing list item view reuse otherwise inflate a new list item layout
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_item_layout, parent, false);
        }

        //Get book at current position
        Books currentBook = getItem(position);

        //find views to display book information
        TextView bookTitle = (TextView) listItemView.findViewById(R.id.book_title);
        TextView bookAuthor = (TextView) listItemView.findViewById(R.id.book_author);
        TextView publishedDate = (TextView) listItemView.findViewById(R.id.published_date);

        //set data to display
        bookTitle.setText(currentBook.getmTitle());
        publishedDate.setText(currentBook.getmPublishedDate());
        bookAuthor.setText(currentBook.getmAuthors());

        return listItemView;
    }
}
