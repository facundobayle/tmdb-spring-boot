package com.despegar.dasboot.controller;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.model.movie.Movie;
import com.despegar.dasboot.service.movie.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //@Controller + @ResponseBody
public class MovieController {

    private Logger logger = LoggerFactory.getLogger(MovieController.class);

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = "/movies/{id}")
    public Movie getMovie(@PathVariable String id) {
        logger.info("getMovie request received for id {}", id);
        return this.movieService.getMovie(id);
    }
}
