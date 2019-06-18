package com.example.android.movies.ui.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.movies.data.MoviesRepository;

public class DetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final MoviesRepository mMoviesRepository;
    private final int mMovieId;

    public DetailsViewModelFactory(MoviesRepository moviesRepository, int movieId) {
        this.mMoviesRepository = moviesRepository;
        this.mMovieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailsViewModel(mMoviesRepository, mMovieId);
    }
}
