package com.example.android.movies.data.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

    @Query("select * from movies where favorite = 1 order by popularity desc ")
    LiveData<List<Movie>> getMoviesFavorites();

    @Query("select * from movies order by popularity desc")
    LiveData<List<Movie>> getMoviesByPopularity();

    @Query("select * from movies order by voteCount desc")
    LiveData<List<Movie>> getMoviesByVoteCount();

    @Query("select * from movies where id = :id")
    LiveData<Movie> getMovie(int id);

    @Query("UPDATE movies SET favorite=:favorite WHERE id = :id")
    void setFavorite(boolean favorite, int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkInsert(Movie... movies);

}
