package com.despegar.dasboot.service;

import com.despegar.dasboot.model.Movie;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    public List<Movie> searchMovie(String query, Integer page) {
        return Collections.singletonList(
                new Movie(
                        "1",
                        "The Godfather",
                        "The godfather",
                        Collections.singletonList("Drama")));
    }
}
