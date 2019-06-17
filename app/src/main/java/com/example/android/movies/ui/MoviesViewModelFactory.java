package com.example.android.movies.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

public class MoviesViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {
    public MoviesViewModelFactory(@NonNull Application application) {
        super(application);
    }
}
