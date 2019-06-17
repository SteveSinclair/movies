package com.example.android.movies.data;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.android.movies.data.database.MoviesDao;
import com.example.android.movies.data.network.MoviesNetworkDataSource;
import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.utilities.AppExecutors;

import java.util.List;

public class MoviesRepository {

    private static final String LOG_TAG = MoviesRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MoviesRepository sInstance;
    private final MoviesDao mMoviesDao;
    private final MoviesNetworkDataSource mMoviesNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;


    private MoviesRepository(
            MoviesDao moviesDao,
            MoviesNetworkDataSource moviesNetworkDataSource,
            AppExecutors appExecutors) {

        this.mMoviesDao = moviesDao;
        this.mMoviesNetworkDataSource = moviesNetworkDataSource;
        this.mExecutors = appExecutors;

        // As long as the repository exists, observe the network LiveData.
        // If that LiveData changes, update the database.
        LiveData<Movie[]> networkData = mMoviesNetworkDataSource.getCurrentMovies();

        networkData.observeForever(newMoviesFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                // Insert our new movies data into Movies's database
                // TODO: 2019-06-17 this looks wrong
                mMoviesDao.bulkInsert(newMoviesFromNetwork);
                Log.d(LOG_TAG, "New values inserted");
            });
        });
    }

    public synchronized static MoviesRepository getInstance(
            MoviesDao moviesDao,
            MoviesNetworkDataSource moviesNetworkDataSource,
            AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MoviesRepository(moviesDao, moviesNetworkDataSource, executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    private synchronized void initializeData() {

        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        // This method call triggers Sunshine to create its task to synchronize weather data
        // periodically.
        mMoviesNetworkDataSource.scheduleRecurringFetchMoviesSync();

        mExecutors.diskIO().execute(() -> {
            if (isFetchNeeded()) {
                startFetchMoviesService();
            }
        });
    }


    /**
     * Database related operations
     **/

    public LiveData<List<Movie>> getMovies() {
        initializeData();
        return mMoviesDao.getMovies();
    }

    public LiveData<List<Movie>> getMoviesByPopularity() {
        initializeData();
        return mMoviesDao.getMoviesByPopularity();
    }
    public LiveData<List<Movie>> getMoviesByVoteCount() {
        initializeData();
        return mMoviesDao.getMoviesByVoteCount();
    }
    public LiveData<List<Movie>> getMoviesFavorites() {
        initializeData();
        return mMoviesDao.getMoviesFavorites();
    }
    /**
     * Checks if there are enough days of future weather for the app to display all the needed data.
     *
     * @return Whether a fetch is needed
     */
    private boolean isFetchNeeded() {
        return (true);  // TODO: 2019-06-14  
    }

    /**
     * Network related operation
     */

    private void startFetchMoviesService() {

        mMoviesNetworkDataSource.startFetchMoviesService();
    }
}
