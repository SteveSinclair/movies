package com.example.android.movies.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.movies.R;
import com.example.android.movies.data.entities.Movie;
import com.example.android.movies.databinding.ActivityDetailsBinding;
import com.example.android.movies.utilities.InjectorUtils;
import com.squareup.picasso.Picasso;


import static com.example.android.movies.utilities.NetworkUtils.buildImageUrl;
import static com.example.android.movies.utilities.NetworkUtils.buildTrailerUri;

// is this extending from the right class?
public class DetailsActivity extends AppCompatActivity implements TrailersAdapter.TrailersAdapterOnItemClickHandler {
    public static final String MOVIE_ID_EXTRA = "MOVIE_ID_EXTRA";
    private int mPosition = RecyclerView.NO_POSITION;
    private static final String TAG = "DetailsActivity";
    private DetailsViewModel mViewModel;
    private int mMovieId;
    private ActivityDetailsBinding mDetailBinding;

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

        mDetailBinding.imageViewFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.toggleFavorite();
            }
        });



    }

    private void bindMovieToUI(Movie movie) {

        String path = buildImageUrl(movie.getPosterPath()).toString();
        //Picasso.Builder builder = new Picasso.Builder(this);
        //builder.build().load(path).into(mDetailBinding.imageViewPoster);
        Picasso.get()
                .load(path)
                .placeholder(R.color.colorPrimaryDark)
                .error(R.drawable.ic_launcher_foreground)
                .into(mDetailBinding.imageViewPoster);

        mDetailBinding.textViewTitle.setText(movie.getTitle());
        mDetailBinding.textViewReleaseDate.setText(movie.getReleaseDate());
        mDetailBinding.textViewVoteAverage.setText(movie.getVoteAverage().toString());
        mDetailBinding.textViewOverview.setText(movie.getOverview());
        if (movie.getFavorite() == null)
            mDetailBinding.imageViewFavorite.setImageResource(R.drawable.ic_star_border_yellow_48dp);
        else if (movie.getFavorite())
            mDetailBinding.imageViewFavorite.setImageResource(R.drawable.ic_star_yellow_48dp);
        else
            mDetailBinding.imageViewFavorite.setImageResource(R.drawable.ic_star_border_yellow_48dp);


        setupRecyclerViewReviews();
        setupRecyclerViewTrailers();
    }

    private void setupRecyclerViewReviews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mDetailBinding.recyclerViewReviews.setLayoutManager(layoutManager);
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(this);
        mDetailBinding.recyclerViewReviews.setAdapter(reviewsAdapter);
        mDetailBinding.recyclerViewReviews.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mViewModel.getReviews().observe(this, reviewsList -> {
            reviewsAdapter.swapReviews(reviewsList);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            mDetailBinding.recyclerViewReviews.smoothScrollToPosition(mPosition);
        });
    }

    private void setupRecyclerViewTrailers() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mDetailBinding.recyclerViewTrailers.setLayoutManager(layoutManager);
        TrailersAdapter trailersAdapter = new TrailersAdapter(this, this);
        mDetailBinding.recyclerViewTrailers.setAdapter(trailersAdapter);
        mDetailBinding.recyclerViewTrailers.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mViewModel.getTrailers().observe(this, trailersList -> {
            trailersAdapter.swapTrailers(trailersList);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            mDetailBinding.recyclerViewTrailers.smoothScrollToPosition(mPosition);
        });
    }

    @Override
    public void OnItemClick(String trailerKey) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(buildTrailerUri(trailerKey));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
