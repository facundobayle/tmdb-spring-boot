package com.despegar.dasboot.service.search;

import com.despegar.dasboot.model.movie.Movie;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SearchService {

    public List<Movie> searchMovie(String query, Integer page) {
        return new ArrayList<>();
    }
}
