package com.example.android.movies.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "reviews")
public class Review implements Serializable {
    //  "author": "garethmb",
    //  "content": "The epic Marvel Saga that started over..."
    //  "id": "5cbf94670e0a266b96fba5ef",
    //  "url": "https://www.themoviedb.org/review/5cbf94670e0a266b96fba5ef"
    @PrimaryKey()
    @NonNull()
    @SerializedName("id")
    private final String id;
    @SerializedName("author")
    private final String author;
    @SerializedName("content")
    private final String content;
    @SerializedName("url")
    private final String url;
    @SerializedName("movie_id")
    private int movieId;

    public Review(@NonNull String id, String author, String content, String url, int movieId) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
        this.movieId = movieId;
    }

    @Ignore()
    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
        this.movieId = 0;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
