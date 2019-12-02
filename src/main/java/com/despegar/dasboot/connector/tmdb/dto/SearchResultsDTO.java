package com.despegar.dasboot.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchResultsDTO {

    private List<SearchMovieDTO> results;

    @JsonCreator
    public SearchResultsDTO(@JsonProperty("results") List<SearchMovieDTO> results) {
        this.results = results;
    }

    public List<SearchMovieDTO> getResults() {
        return results;
    }
}
