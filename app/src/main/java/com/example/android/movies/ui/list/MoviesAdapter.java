package com.example.android.movies.ui.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.movies.R;
import com.example.android.movies.data.entities.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.android.movies.data.network.NetworkUtils.buildImageUrl;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private static final String TAG = MoviesAdapter.class.getSimpleName();

    private final Context mContext;
    private List<Movie> mMovieList;
    private final MovieAdapterOnClickHandler mClickHandler;

    public MoviesAdapter(Context context, MovieAdapterOnClickHandler onClickHandler) {
        mContext = context;
        mClickHandler = onClickHandler;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.movie_list_item, null);
        itemView.setFocusable(true);
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
        void OnClick(int movieId);
    }


    /**
     * Swaps the list used by the MoviesAdapter for its movie data. This method is called by
     * {@link MainActivity} after a load has finished. When this method is called, we assume we have
     * a new set of data, so we call notifyDataSetChanged to tell the RecyclerView to update.
     *
     * @param newMovies the new list of movies to use as MoviesAdapter's data source
     */
    void swapMovies(final List<Movie> newMovies) {
        // If there was no movies data, then recreate all of the list
        if (mMovieList == null) {
            mMovieList = newMovies;
            notifyDataSetChanged();
        } else {
            /*
             * Otherwise we use DiffUtil to calculate the changes and update accordingly. This
             * shows the four methods you need to override to return a DiffUtil callback. The
             * old list is the current list stored in mForecast, where the new list is the new
             * values passed in from the observing the database.
             */

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mMovieList.size();
                }

                @Override
                public int getNewListSize() {
                    return newMovies.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mMovieList.get(oldItemPosition).getId() ==
                            newMovies.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Movie newMovie = newMovies.get(newItemPosition);
                    Movie oldMovie = mMovieList.get(oldItemPosition);
                    return newMovie.getId() == oldMovie.getId();
                }
            });
            mMovieList = newMovies;
            result.dispatchUpdatesTo(this);
        }
    }

    /*
     * MovieViewHolder
     */
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // TODO: 2019-06-17  
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
