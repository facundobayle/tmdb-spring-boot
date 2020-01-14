package com.despegar.dasboot.connector.tmdb.dto;

import com.despegar.dasboot.model.movie.MovieInfo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TopRatedDTO {
    private List<MovieInfo> results;

    @JsonCreator
    public TopRatedDTO(@JsonProperty("results") List<MovieInfo> results) {
        this.results = results;
    }

    public List<MovieInfo> getResults() {
        return results;
    }
}
