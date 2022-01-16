package com.example.movies2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Genre implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Integer genreId) {
        id = genreId;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String genreName) {
        name = genreName;
    }

    public String getName() {
        return name;
    }
}
