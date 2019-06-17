package com.example.android.movies.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewResponse {

    @SerializedName("id")
    private final Integer id;
    @SerializedName("page")
    private final Integer page;
    @SerializedName("results")
    private final ArrayList<Review> reviews;
    @SerializedName("total_pages")
    private final Integer totalPages;
    @SerializedName("total_results")
    private final Integer totalResults;


    public ReviewResponse(Integer id, Integer page, ArrayList<Review> reviews, Integer totalPages, Integer totalResults) {
        this.id = id;
        this.page = page;
        this.reviews = reviews;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPage() {
        return page;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }
}
