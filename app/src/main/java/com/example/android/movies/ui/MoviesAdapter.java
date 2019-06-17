package com.example.android.movies.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.movies.R;
import com.example.android.movies.data.entities.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.android.movies.data.network.NetworkUtils.buildImageUrl;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private static final String TAG = MoviesAdapter.class.getSimpleName();

    private final Context mContext;
    private final List<Movie> mMovieList;
    private final MovieAdapterOnClickHandler mClickHandler;

    public MoviesAdapter(Context context, List<Movie> movieList, MovieAdapterOnClickHandler onClickHandler) {
        mContext = context;
        mMovieList = movieList;
        mClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.movie_list_item, null);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        String path = buildImageUrl(movie.getPosterPath()).toString();
        //Log.i(TAG, "onBindViewHolder: "+path);

        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.build().load(path).into(holder.imageViewPoster);
    }

    @Override
    public int getItemCount() {
        if (mMovieList == null) {
            return 0;
        } else
            return mMovieList.size();
    }

    public interface MovieAdapterOnClickHandler {
        void OnClick(Movie movie, Bitmap poster);
    }

    /*
     * MovieViewHolder
     */
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView imageViewPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

}
