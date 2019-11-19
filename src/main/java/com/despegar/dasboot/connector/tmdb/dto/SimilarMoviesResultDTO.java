package com.despegar.dasboot.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SimilarMoviesResultDTO {

    private List<SimilarMoviesDTO> results;

    public SimilarMoviesResultDTO(@JsonProperty("results") List<SimilarMoviesDTO> results) {
        this.results = results;
    }

    public List<SimilarMoviesDTO> getResults() {
        return results;
    }
}
