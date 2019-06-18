package com.example.android.movies.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.android.movies.R;
import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.utilities.InjectorUtils;

// is this extending from the right class?
public class DetailsActivity extends AppCompatActivity {
    public static final String MOVIE_ID_EXTRA = "MOVIE_ID_EXTRA";

    private DetailsViewModel mViewModel;
    private int mMovieId;
    private ViewDataBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        int movieId = getIntent().getIntExtra(MOVIE_ID_EXTRA, -1);

        // Get the ViewModel from the factory
        DetailsViewModelFactory factory = InjectorUtils.provideDetailsViewModelFactory(this.getApplicationContext(), movieId);
        mViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel.class);


        // Observers changes in the Movie
        mViewModel.getMovie().observe(this, movie -> {
            // If the movie details change, update the UI
            if (movie != null) bindMovieToUI(movie);
        });

    }

    private void bindMovieToUI(Movie movie) {


    }

}
