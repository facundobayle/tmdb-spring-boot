package com.despegar.dasboot.service;

import com.despegar.dasboot.model.Movie;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MovieService {

    public Movie getMovie(String id) {
        return new Movie(id, "", "", Collections.singletonList("Drama"));
    }
}
