package com.despegar.dasboot.connector.tmdb.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreditsDTO {

    private Integer id;
    private List<CastDTO> cast;
    private List<CrewDTO> crew;

    @JsonCreator
    public CreditsDTO(
            @JsonProperty("id") Integer id,
            @JsonProperty("cast") List<CastDTO> cast,
            @JsonProperty("crew") List<CrewDTO> crew) {
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public List<CastDTO> getCast() {
        return cast;
    }

    public List<CrewDTO> getCrew() {
        return crew;
    }
}
