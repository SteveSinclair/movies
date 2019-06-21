package com.example.android.movies.utilities;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    private final static String imageSize = "w185";
    private final static String basePath = "https://image.tmdb.org/t/p/";

    public static URL buildImageUrl(String posterPath) {
        Uri builtUri = Uri.parse(basePath)
                .buildUpon()
                .appendPath(imageSize)
                .appendEncodedPath(posterPath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    public static Uri buildTrailerUri(String trailerKey) {
        Uri uri = Uri.parse("https://www.youtube.com/watch").buildUpon().appendQueryParameter("v", trailerKey).build();
        return uri;
    }
}
