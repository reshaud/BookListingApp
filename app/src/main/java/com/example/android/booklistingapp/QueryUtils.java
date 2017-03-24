package com.example.android.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reshaud Ally on 3/22/2017.
 */

public final class QueryUtils {

    //Log tag
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    //private Constructor
    private QueryUtils() {

    }

    public static List<Books> fetchBooks(String searchRequest) {

        String jsonResponse = null;

        //Get request string
        String requestString = createRequestString(searchRequest);

        // Create URL object
        URL requestURL = createUrl(requestString);

        try {
            //Make HTTP request and receive JSON Response
            jsonResponse = makeHttpRequest(requestURL);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request", e);
        }

        //Extract books from json response
        List<Books> booksList = extractFromJSON(jsonResponse);

        return booksList;
    }

    //return list of books extracted from JSON
    private static List<Books> extractFromJSON(String booksJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(booksJSON)) {
            return null;
        }

        // Create an empty booksList
        List<Books> booksList = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            String authors = null;

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(booksJSON);

            // Extract the JSONArray associated with the key called "items"
            JSONArray itemsArray = baseJsonResponse.getJSONArray("items");

            //Cycle through every item in array
            for (int i = 0; i < itemsArray.length(); i++) {

                // Get book object inside array at position i
                JSONObject currentBook = itemsArray.getJSONObject(i);

                //Get JSONObject associated with the key called "volumeinfo"
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                //Get book title associated with the key called "title"
                String title = volumeInfo.getString("title");

                //Get JSONArray associated with the key called "authors"
                JSONArray authorsArray = volumeInfo.getJSONArray("authors");

                for (int j = 0; j < authorsArray.length(); j++) {
                    //Add all authors
                    authors = authors + authorsArray.getString(j) + " ";
                }

                //Get published date associated with the key called "publishedDate"
                String publishedDate = volumeInfo.getString("publishedDate");

                // Create a new Book
                Books books = new Books(title, publishedDate, authors);

                // Add the new book to the booksList
                //booksList.add(books);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the Books JSON results", e);
        }

        // Return the list of books
        return booksList;
    }

    //Make Http request using the given url
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000); //milliseconds
            urlConnection.setConnectTimeout(15000); //milliseconds;
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //create URL from string
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            //Create URL from given string
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    //return json response as a string
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //create request String combing search string + query url
    private static String createRequestString(String requestString) {
        String queryURL = "https://www.googleapis.com/books/v1/volumes?q=";

        //Add search string and max results
        queryURL = queryURL + requestString + "&maxResults=15"; //return first 15 results

        return queryURL;
    }
}
