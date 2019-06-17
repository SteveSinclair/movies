package com.example.android.movies.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.movies.data.entities.Movie;

import java.util.Date;
import java.util.List;

@Dao
public interface MoviesDao {

    @Query("select * from movies")
    LiveData<List<Movie>> getMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(Movie... movies);

}
