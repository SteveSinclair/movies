package com.example.android.movies.data.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.android.movies.utilities.InjectorUtils;

public class MoviesSyncIntentService extends IntentService {

    private static final String LOG_TAG = MoviesSyncIntentService.class.getSimpleName();

    public MoviesSyncIntentService() {
        super("MoviesSyncIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(LOG_TAG, "Intent service started");
        MoviesNetworkDataSource networkDataSource = InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchMovies();
    }
}
