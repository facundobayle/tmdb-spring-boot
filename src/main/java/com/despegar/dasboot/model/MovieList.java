package com.despegar.dasboot.model;


import com.despegar.dasboot.model.movie.Movie;

import java.util.List;

public class MovieList {

    private String user;
    private String name;
    private List<Movie> movies ;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
