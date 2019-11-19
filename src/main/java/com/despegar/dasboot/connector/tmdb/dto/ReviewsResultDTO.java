package com.despegar.dasboot.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReviewsResultDTO {

    private List<ReviewDTO> results;

    @JsonCreator
    public ReviewsResultDTO(@JsonProperty("results") List<ReviewDTO> results) {
        this.results = results;
    }

    public List<ReviewDTO> getResults() {
        return results;
    }
}
