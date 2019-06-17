package com.example.android.movies.ui;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.example.android.movies.BuildConfig;
import com.example.android.movies.R;
import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.data.entities.MoviesResponse;
import com.example.android.movies.data.network.TheMovieDbApi;
import com.example.android.movies.data.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieAdapterOnClickHandler{

    private static final int SORT_BY_POPULARITY = 0;
    private static final int SORT_BY_RATING = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private List<Movie> mMovies;
    private final TheMovieDbApi theMovieDbApi = RetrofitClientInstance.getRetrofitInstance().create(TheMovieDbApi.class);
    MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.moviesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);


        loadMovies(SORT_BY_POPULARITY);
    }

    private void loadMovies(int sortBy) {
        
        Call<MoviesResponse> call;
        switch (sortBy) {
            case SORT_BY_POPULARITY:
                call = theMovieDbApi.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY);
                setTitle(R.string.titlePopular);
                break;
            case SORT_BY_RATING:
                call = theMovieDbApi.getTopRatedMovies(BuildConfig.MOVIE_DB_API_KEY);
                setTitle(R.string.titleRating);
                break;
            default:
                call = theMovieDbApi.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY);
                setTitle(R.string.titlePopular);
        }

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                MoviesResponse movieResponse = response.body();


                mMovies = movieResponse != null ? movieResponse.getMovies() : null;
                if (mMovies != null) {
                    showMovies(mMovies);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showMovies(List<Movie> mMovies) {
        mMoviesAdapter = new MoviesAdapter(this, mMovies, this);
        mRecyclerView.setAdapter(mMoviesAdapter);
    }

    @Override
    public void OnClick(Movie movie, Bitmap poster) {
        // TODO: 2019-06-13  
    }
}
