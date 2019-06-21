package com.example.android.movies.ui.list;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.android.movies.R;
import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.ui.detail.DetailsActivity;
import com.example.android.movies.utilities.InjectorUtils;


public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieAdapterOnItemClickHandler {
    public static final int SORT_BY_POPULARITY = 0;
    public static final int SORT_BY_RATING = 1;
    public static final int SORT_BY_FAVORITE = 2;

    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar mprogressBar;
    private MoviesAdapter mMoviesAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;
    private MoviesViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Movies");

        mprogressBar = findViewById(R.id.pb_loading_indicator);

        mRecyclerView = findViewById(R.id.moviesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this, this);
        mRecyclerView.setAdapter(mMoviesAdapter);

        MoviesViewModelFactory factory = InjectorUtils.provideMoviesViewModelFactory(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(MoviesViewModel.class);

        mViewModel.getMovies().observe(this, moviesList -> {
            Log.i(TAG, "onCreate: mViewModel.getMovies().observe");
            mMoviesAdapter.swapMovies(moviesList);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            mRecyclerView.smoothScrollToPosition(mPosition);

            // Show the movies list or the loading screen based on whether the movies data exists
            // and is loaded
            if (moviesList != null && moviesList.size() != 0)
                showMoviesDataView();
            else
                showLoading();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.miFavorites:
                setTitle("Favorite Movies");
                mViewModel.getFavoriteMovies().observe(this, moviesList -> {
                    Log.i(TAG, "onCreate: mViewModel.getFavoriteMovies().observe");
                    mMoviesAdapter.swapMovies(moviesList);
                    if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
                    mRecyclerView.smoothScrollToPosition(mPosition);

                    // Show the movies list or the loading screen based on whether the movies data exists
                    // and is loaded
                    if (moviesList != null && moviesList.size() != 0)
                        showMoviesDataView();
                    else
                        showLoading();
                });
                return true;
            case R.id.miHighestRated:
                setTitle("Highest Rated Movies");
                mViewModel.getMoviesByVoteCount().observe(this, moviesList -> {
                    Log.i(TAG, "onCreate: mViewModel.getMoviesByVoteCount().observe");
                    mMoviesAdapter.swapMovies(moviesList);
                    if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
                    mRecyclerView.smoothScrollToPosition(mPosition);

                    // Show the movies list or the loading screen based on whether the movies data exists
                    // and is loaded
                    if (moviesList != null && moviesList.size() != 0)
                        showMoviesDataView();
                    else
                        showLoading();
                });
                return true;
            case R.id.miMostPopular:
                setTitle("Popular Movies");
                mViewModel.getMoviesByPopularity().observe(this, moviesList -> {
                    Log.i(TAG, "onCreate: mViewModel.getMoviesByPopularity().observe");
                    mMoviesAdapter.swapMovies(moviesList);
                    if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
                    mRecyclerView.smoothScrollToPosition(mPosition);

                    // Show the movies list or the loading screen based on whether the movies data exists
                    // and is loaded
                    if (moviesList != null && moviesList.size() != 0)
                        showMoviesDataView();
                    else
                        showLoading();
                });
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showMoviesDataView() {
        mprogressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mprogressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void OnItemClick(int movieId) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.MOVIE_ID_EXTRA, movieId);
        startActivity(intent);

    }
}
