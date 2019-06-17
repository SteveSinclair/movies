package com.example.android.movies.data.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Trailer implements Serializable {
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

    public Trailer(String id, String language, String country, String key, String name, String site, String resolution, String type) {
        this.id = id;
        this.language = language;
        this.country = country;
        this.key = key;
        this.name = name;
        this.site = site;
        this.resolution = resolution;
        this.type = type;
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
}
