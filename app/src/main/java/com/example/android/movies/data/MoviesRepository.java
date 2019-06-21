package com.example.android.movies.data;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.android.movies.data.database.MoviesDao;
import com.example.android.movies.data.entities.Review;
import com.example.android.movies.data.entities.Trailer;
import com.example.android.movies.data.network.MoviesNetworkDataSource;
import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.utilities.AppExecutors;

import java.util.List;

public class MoviesRepository {

    private static final String TAG = MoviesRepository.class.getSimpleName();

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
        LiveData<Movie[]> networkMovies = mMoviesNetworkDataSource.getDownloadedMovies();

        networkMovies.observeForever(newDownloadedMovies -> {
            mExecutors.diskIO().execute(() -> {
                // Insert our new movies data into Movies's database
                mMoviesDao.bulkInsertMovies(newDownloadedMovies);
                Log.i(TAG, "Movies Repository: inserted downloaded movies");

            });
        });

        LiveData<Review[]> networkReviews = mMoviesNetworkDataSource.getDownloadedReviews();

        networkReviews.observeForever(newDownloadedReviews -> {
            mExecutors.diskIO().execute(() -> {
                moviesDao.bulkInsertReviews(newDownloadedReviews);
                Log.i(TAG, "MoviesRepository: insert downloaded reviews ");
            });
        });

        LiveData<Trailer[]> networkTrailers = mMoviesNetworkDataSource.getDownloadedTrailers();

        networkTrailers.observeForever(newDownloadedTrailers -> {
            mExecutors.diskIO().execute(() -> {
                moviesDao.bulkInsertTrailers(newDownloadedTrailers);
                Log.i(TAG, "MoviesRepository: insert downloaded trailers ");
            });
        });

    }

    public synchronized static MoviesRepository getInstance(
            MoviesDao moviesDao,
            MoviesNetworkDataSource moviesNetworkDataSource,
            AppExecutors executors) {
        Log.d(TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MoviesRepository(moviesDao, moviesNetworkDataSource, executors);
                Log.d(TAG, "Made new repository");
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

    public LiveData<Movie> getMovie(int moiveId) {
        return mMoviesDao.getMovie(moiveId);
    }

    private boolean isFetchNeeded() {
        return (true);  // TODO: 2019-06-14  
    }

    public LiveData<List<Review>> getReviews(int movieId) {
        return mMoviesDao.getReviews(movieId);
    }

    public LiveData<List<Trailer>> getTrailers(int movieId) {
        return mMoviesDao.getTrailers(movieId);
    }

    public void setFavorite(Boolean isFavorite, int movieId) {
        mExecutors.diskIO().execute(() -> {
            mMoviesDao.setFavorite(isFavorite, movieId);
        });
    }

    /**
     * Network related operation
     */

    private void startFetchMoviesService() {

        mMoviesNetworkDataSource.startFetchMoviesService();
    }
}

