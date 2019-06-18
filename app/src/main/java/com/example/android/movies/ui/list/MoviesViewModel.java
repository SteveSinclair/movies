package com.example.android.movies.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.movies.data.MoviesRepository;
import com.example.android.movies.data.entities.Movie;

import java.util.List;

public class MoviesViewModel extends ViewModel {
    private final MoviesRepository mRepository;
    private LiveData<List<Movie>> mMovies;

    public MoviesViewModel(MoviesRepository repository) {
        mRepository = repository;
        mMovies = mRepository.getMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    public LiveData<List<Movie>> getMoviesByPopularity() {
        mMovies = mRepository.getMoviesByPopularity();
        return mMovies;
    }
    public LiveData<List<Movie>> getMoviesByVoteCount() {
        mMovies = mRepository.getMoviesByVoteCount();
        return mMovies;
    }
    public LiveData<List<Movie>> getFavoriteMovies() {
        mMovies = mRepository.getMoviesFavorites();
        return mMovies;
    }
}
