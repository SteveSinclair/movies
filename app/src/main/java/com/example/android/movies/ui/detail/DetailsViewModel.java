package com.example.android.movies.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.movies.data.MoviesRepository;
import com.example.android.movies.data.entities.Movie;

public class DetailsViewModel extends ViewModel {

    private LiveData<Movie> mMovie;
    private MoviesRepository mRepository;

    public DetailsViewModel(MoviesRepository moviesRepository, int movieId) {
        this.mRepository = moviesRepository;
        mMovie = mRepository.getMovie(movieId);
    }

    public LiveData<Movie> getMovie(){
        return mMovie;
    }

}
