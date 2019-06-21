package com.example.android.movies.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "trailers")
public class Trailer implements Serializable {
    @PrimaryKey()
    @NonNull()
    @SerializedName("id")
    private final String id; //: "5c40c5ef9251416b02b10d44",
    @SerializedName("iso_639_1") //: "en",
    private final String language;
    @SerializedName("iso_3166_1")//: "US",
    private final String country;
    @SerializedName("key")//: "M7XM597XO94",
    private final String key;
    @SerializedName("name")//: "Official Trailer",
    private final String name;
    @SerializedName("site")//: "YouTube",
    private final String site;
    @SerializedName("size")//: 1080,
    private final String resolution;
    @SerializedName("type")//: "Trailer"
    private final String type;
    @SerializedName("movie_id")
    private int movieId;

    public Trailer(String id, String language, String country, String key, String name, String site, String resolution, String type, int movieId) {
        this.id = id;
        this.language = language;
        this.country = country;
        this.key = key;
        this.name = name;
        this.site = site;
        this.resolution = resolution;
        this.type = type;
        this.movieId = movieId;
    }
    @Ignore()
    public Trailer(String id, String language, String country, String key, String name, String site, String resolution, String type) {
        this.id = id;
        this.language = language;
        this.country = country;
        this.key = key;
        this.name = name;
        this.site = site;
        this.resolution = resolution;
        this.type = type;
        movieId = 0;
    }
    public String getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getResolution() {
        return resolution;
    }

    public String getType() {
        return type;
    }
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}
