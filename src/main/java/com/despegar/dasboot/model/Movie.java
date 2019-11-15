package com.despegar.dasboot.model;

import java.util.List;

public class Movie {

    private String id;
    private String name;
    private String description;
    private List<String> genderList;

    public Movie(String id, String name, String description, List<String> genderList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.genderList = genderList;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getGenderList() {
        return genderList;
    }
}
