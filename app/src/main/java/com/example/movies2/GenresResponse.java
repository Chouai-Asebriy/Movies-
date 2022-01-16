package com.example.movies2;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GenresResponse {
    @SerializedName("genres")
    private List<Genre> results = new ArrayList<Genre>();

    public List<Genre> getResults() {
        return results;
    }

    public void setResults(List<Genre> results) {
        this.results = results;
    }
}

