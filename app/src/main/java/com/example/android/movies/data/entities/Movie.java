package com.example.android.movies.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "movies")
public class Movie implements Serializable {
    @SerializedName("id")
    @PrimaryKey()
    private final Integer id;
    @SerializedName("vote_count")
    private final Integer voteCount;
    @SerializedName("video")
    private final Boolean video;
    @SerializedName("vote_average")
    private final Double voteAverage;
    @SerializedName("title")
    private final String title;
    @SerializedName("popularity")
    private final Double popularity;
    @SerializedName("poster_path")
    private final String posterPath;
    @SerializedName("original_language")
    private final String originalLanguage;
    @SerializedName("original_title")
    private final String originalTitle;
    //    @SerializedName("genre_ids")
//    private final ArrayList<Integer> genreIds;
    @SerializedName("backdrop_path")
    private final String backdropPath;
    @SerializedName("adult")
    private final Boolean adult;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("release_date")
    private final String releaseDate;

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    @SerializedName("favorite")
    private Boolean favorite;

    public Movie(
            Integer id,
            Integer voteCount,
            Boolean video,
            Double voteAverage,
            String title,
            Double popularity,
            String posterPath,
            String originalLanguage,
            String originalTitle,
            String backdropPath,
            Boolean adult,
            String overview,
            String releaseDate) {
        this.id = id;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
        this.title = title;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
//        ArrayList<Integer> genreIds;
//        this.genreIds = new ArrayList<>();
        this.backdropPath = backdropPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.favorite = false;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getVideo() {
        return video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

//    public ArrayList<Integer> getGenreIds() {
//        return genreIds;
//    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }



}


