package com.example.android.movies.data.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.data.entities.Review;
import com.example.android.movies.data.entities.Trailer;

import static androidx.room.Room.*;

@Database(entities = {Movie.class, Trailer.class, Review.class}, version = 1, exportSchema = true)
@TypeConverters(DateConverter.class)
public abstract class MoviesDatabase extends RoomDatabase {

    private static final String LOG_TAG = MoviesDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "movies";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MoviesDatabase sInstance;

    public static MoviesDatabase getInstance(Context context) {
        Log.d(LOG_TAG, "Getting the database");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = databaseBuilder(
                        context.getApplicationContext(),
                        MoviesDatabase.class,
                        MoviesDatabase.DATABASE_NAME)
                        .build();
                Log.d(LOG_TAG, "Made new database");
            }
        }
        return sInstance;
    }

    // The associated DAOs for the database
    public abstract MoviesDao movieDao();
}
