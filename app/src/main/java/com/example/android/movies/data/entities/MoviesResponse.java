package com.example.android.movies.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MoviesResponse {
    @SerializedName("page")
    private final Integer page;
    @SerializedName("total_results")
    private final Integer totalResults;
    @SerializedName("total_pages")
    private final Integer totalPages;
    @SerializedName("results")
    private final Movie[] movies;

    public MoviesResponse(
            Integer page,
            Integer totalResults,
            Integer totalPages,
            Movie[] movies) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.movies = movies;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Movie[] getMovies() {
        return movies;
    }
}
