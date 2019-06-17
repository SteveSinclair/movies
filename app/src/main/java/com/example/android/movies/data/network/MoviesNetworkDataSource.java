package com.example.android.movies.data.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.movies.BuildConfig;
import com.example.android.movies.R;
import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.data.entities.MoviesResponse;
import com.example.android.movies.utilities.AppExecutors;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesNetworkDataSource {
    private static final int SORT_BY_POPULARITY = 0;
    private static final int SORT_BY_RATING = 1;

    private static final String LOG_TAG = MoviesNetworkDataSource.class.getSimpleName();

    // Interval at which to sync with The Movie DB. Use TimeUnit for convenience, rather than
    // writing out a bunch of multiplication ourselves and risk making a silly mistake.
    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static final String MOVIES_SYNC_TAG = "movies-sync";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MoviesNetworkDataSource sInstance;
    private final Context mContext;

    // LiveData storing the latest downloaded movies
    private final MutableLiveData<Movie[]> mDownloadedMovies;
    private final AppExecutors mExecutors;

    public MoviesNetworkDataSource(Context context, AppExecutors appExecutors) {
        this.mContext = context;
        this.mExecutors = appExecutors;
        this.mDownloadedMovies = new MutableLiveData<Movie[]>();
    }

    /**
     * Get the singleton for this class
     */
    public static MoviesNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MoviesNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public LiveData<Movie[]> getCurrentMovies() {
        return mDownloadedMovies;
    }

    /**
     * Starts an intent service to fetch movies.
     */
    public void startFetchMoviesService() {
        Intent intentToFetch = new Intent(mContext, MoviesSyncIntentService.class);
        mContext.startService(intentToFetch);
        Log.d(LOG_TAG, "Service created");
    }

    /**
     * Schedules a repeating job service which fetches movies.
     */
    public void scheduleRecurringFetchMoviesSync() {
        // TODO: 2019-06-14
        Log.d(LOG_TAG, "Job scheduled");
    }

    /**
     * Gets the latest movies
     */
    void fetchMovies() {
        Log.d(LOG_TAG, "Fetch movies started");
        mExecutors.networkIO().execute(() -> {
            try {
                // TODO: 2019-06-14
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void loadMovies(int sortBy) {
        final TheMovieDbApi theMovieDbApi = RetrofitClientInstance.getRetrofitInstance().create(TheMovieDbApi.class);


        Call<MoviesResponse> call;
        switch (sortBy) {
            case SORT_BY_POPULARITY:
                call = (Call<MoviesResponse>) theMovieDbApi.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY);
                break;
            case SORT_BY_RATING:
                call = theMovieDbApi.getTopRatedMovies(BuildConfig.MOVIE_DB_API_KEY);
                break;
            default:
                call = theMovieDbApi.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY);
        }

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                MoviesResponse movieResponse = response.body();

                List<Movie> movies;
                movies = movieResponse != null ? movieResponse.getMovies() : null;
                if (movies != null) {
                    mDownloadedMovies.postValue(movies);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

}
