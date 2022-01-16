package com.example.movies2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    @SerializedName("poster_path")
    @Expose
    private String poster_path;
    @SerializedName("title")
    @Expose
    private String title;
    private String backdrop_path;
    private String overview;
    private String release_date;
    private List<Genre> genres = new ArrayList<Genre>();
    private int[] genre_ids;

    public Movie(int id, String poster_path, String backdrop_path, String title, String overview, String release_date, List<Genre> genres, int[] genre_ids) {
        this.id = id;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.genres = genres;
        this.genre_ids = genre_ids;
    }

    public Movie(int id, String poster_path, String backdrop_path, String title, String overview, String release_date) {
        this.id = id;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }
}

