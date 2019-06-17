package com.example.android.movies.data.network;

import com.example.android.movies.data.entities.MoviesResponse;
import com.example.android.movies.data.entities.ReviewResponse;
import com.example.android.movies.data.entities.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDbApi {

    String BASE_URL = "https://api.themoviedb.org/3/movie/";

    @GET("popular")
    Call<MoviesResponse> getPopularMovies(
            @Query("api_key") String apiKey
    );

    @GET("top_rated")
    Call<MoviesResponse> getTopRatedMovies(
            @Query("api_key") String apiKey
    );

    @GET("{movie_id}/reviews")
    Call<ReviewResponse> getReviews(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("{movie_id}/videos")
    Call<TrailerResponse> getTrailers(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
