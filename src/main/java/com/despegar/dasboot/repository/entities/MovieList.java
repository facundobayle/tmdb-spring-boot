package com.despegar.dasboot.repository.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Set;

@Document("movielists")
public class MovieList {
    private String id;
    private String user;
    private String name;
    @JsonProperty
    private Set<MovieItem> items;
    private LocalDate created;

    @JsonCreator
    public MovieList(String user, String name, Set<MovieItem> items, LocalDate created) {
        this.user = user;
        this.name = name;
        this.items = items;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreated() {
        return created;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public Set<MovieItem> getItems() {
        return items;
    }
}
