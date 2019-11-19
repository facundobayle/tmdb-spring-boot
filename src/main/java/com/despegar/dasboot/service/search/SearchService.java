package com.despegar.dasboot.service.search;

import com.despegar.dasboot.model.movie.MovieData;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    public List<MovieData> searchMovie(String query, Integer page) {
        return Collections.singletonList(
                new MovieData(
                        "1",
                        "The Godfather",
                        "The godfather",
                        Collections.singletonList("Drama")));
    }
}
