package com.example.android.movies.ui.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.movies.data.MoviesRepository;

public class MoviesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MoviesRepository mMoviesRepository;

    public MoviesViewModelFactory(MoviesRepository moviesRepository) {
        this.mMoviesRepository = moviesRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MoviesViewModel(mMoviesRepository);
    }
}
