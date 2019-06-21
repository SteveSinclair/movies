package com.example.android.movies.data.entities;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TrailerResponse {
    @SerializedName("id")
    private final Integer id;
    @SerializedName("results")
    private final Trailer[] trailers;

    public TrailerResponse(Integer id, Trailer[] trailers) {
        this.id = id;
        this.trailers = trailers;
    }

    public Integer getId() {
        return id;
    }

    public Trailer[] getTrailers() {
        return trailers;
    }
}
