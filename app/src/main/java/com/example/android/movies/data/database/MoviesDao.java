package com.example.android.movies.data.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.data.entities.Review;
import com.example.android.movies.data.entities.Trailer;

import java.util.Date;
import java.util.List;

@Dao
public interface MoviesDao {

    // Movies
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
    void bulkInsertMovies(Movie... movies);

    // Trailers
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkInsertTrailers(Trailer... trailer);

    @Query("select * from trailers where movieId = :movieId")
    LiveData<List<Trailer>> getTrailers(int movieId);

    //Reviews
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void bulkInsertReviews(Review... review);

    @Query("select * from reviews where movieId = :movieId")
    LiveData<List<Review>> getReviews(int movieId);

}
