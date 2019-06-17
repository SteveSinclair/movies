package com.example.android.movies.data.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.movies.BuildConfig;
import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.data.entities.MoviesResponse;
import com.example.android.movies.utilities.AppExecutors;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

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
        Driver driver = new GooglePlayDriver(mContext);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        // Create the Job to periodically sync Movies
        Job syncMoviesJob = dispatcher.newJobBuilder()
                /* The Service that will be used to sync Movie's data */
                .setService(MoviesFirebaseJobService.class)
                /* Set the UNIQUE tag used to identify this Job */
                .setTag(MOVIES_SYNC_TAG)
                /*
                 * Network constraints on which this Job should run. We choose to run on any
                 * network, but you can also choose to run only on un-metered networks or when the
                 * device is charging. It might be a good idea to include a preference for this,
                 * as some users may not want to download any data on their mobile plan. ($$$)
                 */
                .setConstraints(Constraint.ON_ANY_NETWORK)
                /*
                 * setLifetime sets how long this job should persist. The options are to keep the
                 * Job "forever" or to have it die the next time the device boots up.
                 */
                .setLifetime(Lifetime.FOREVER)
                /*
                 * We want Sunshine's weather data to stay up to date, so we tell this Job to recur.
                 */
                .setRecurring(true)
                /*
                 * We want the weather data to be synced every 3 to 4 hours. The first argument for
                 * Trigger's static executionWindow method is the start of the time frame when the
                 * sync should be performed. The second argument is the latest point in time at
                 * which the data should be synced. Please note that this end time is not
                 * guaranteed, but is more of a guideline for FirebaseJobDispatcher to go off of.
                 */
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                /*
                 * If a Job with the tag with provided already exists, this new job will replace
                 * the old one.
                 */
                .setReplaceCurrent(true)
                /* Once the Job is ready, call the builder's build method to return the Job */
                .build();

        // Schedule the Job with the dispatcher
        dispatcher.schedule(syncMoviesJob);
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
                loadMovies(SORT_BY_POPULARITY);
                loadMovies(SORT_BY_RATING);
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
                call = (Call<MoviesResponse>) theMovieDbApi.getTopRatedMovies(BuildConfig.MOVIE_DB_API_KEY);
                break;
            default:
                call = (Call<MoviesResponse>) theMovieDbApi.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY);
        }

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                MoviesResponse movieResponse = response.body();
                mDownloadedMovies.postValue(movieResponse.getMovies());

                //List<Movie> movies;
                //movies = movieResponse != null ? movieResponse.getMovies() : null;
                //Log.i(LOG_TAG, "loadMovies - onResponse Movies Size:" + movies.size());
//                if (movies != null) {
//                    mDownloadedMovies.postValue(movies);
//                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

}
