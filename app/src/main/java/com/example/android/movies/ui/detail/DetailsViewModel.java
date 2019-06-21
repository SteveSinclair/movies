package com.example.android.movies.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.movies.data.MoviesRepository;
import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.data.entities.Review;
import com.example.android.movies.data.entities.Trailer;

import java.util.List;

public class DetailsViewModel extends ViewModel {

    private LiveData<List<Trailer>> mTrailers;
    private LiveData<Movie> mMovie;
    private LiveData<List<Review>> mReviews;
    private MoviesRepository mRepository;

    public DetailsViewModel(MoviesRepository moviesRepository, int movieId) {
        this.mRepository = moviesRepository;
        mMovie = mRepository.getMovie(movieId);
        mReviews = mRepository.getReviews(movieId);
        mTrailers = mRepository.getTrailers(movieId);
    }

    public LiveData<Movie> getMovie(){
        return mMovie;
    }

    public LiveData<List<Trailer>> getTrailers() {
        return mTrailers;
    }

    public LiveData<List<Review>> getReviews() {
        return mReviews;
    }


    public void toggleFavorite() {
      Boolean isFavorite = mMovie.getValue().getFavorite();
        if (isFavorite == null) {
            isFavorite = false;
        }
      mRepository.setFavorite(!isFavorite, mMovie.getValue().getId());
    }
}
