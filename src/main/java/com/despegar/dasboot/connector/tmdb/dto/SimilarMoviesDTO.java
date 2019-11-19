package com.despegar.dasboot.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimilarMoviesDTO {

    private Integer id;
    private String title;
    private String releaseDate;

    @JsonCreator
    public SimilarMoviesDTO(
            @JsonProperty("id") Integer id,
            @JsonProperty("title") String title,
            @JsonProperty("releaseDate") String releaseDate) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
