package com.despegar.dasboot.service.movie;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.MovieDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MovieService {

    private TMDBConnector tmdbConnector;
    private MovieTransformer movieTransformer;

    @Autowired
    public MovieService(TMDBConnector tmdbConnector
            , MovieTransformer movieTransformer) {
        this.tmdbConnector = tmdbConnector;
        this.movieTransformer = movieTransformer;
    }

    public MovieDataDTO getMovie(String id) {
        return this.tmdbConnector.getMovie(id);
    }
}
