package com.despegar.dasboot.service.movie;

import com.despegar.dasboot.connector.exception.APIException;
import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.*;
import com.despegar.dasboot.model.movie.Movie;
import com.despegar.dasboot.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    private TMDBConnector tmdbConnector;
    private ReviewService reviewService;
    private MovieTransformer movieTransformer;

    @Autowired
    public MovieService(TMDBConnector tmdbConnector,
                        ReviewService reviewService,
                        MovieTransformer movieTransformer) {
        this.tmdbConnector = tmdbConnector;
        this.reviewService = reviewService;
        this.movieTransformer = movieTransformer;
    }

    public Movie getMovie(String id) {
        MovieDataDTO movieData =  this.tmdbConnector.getMovie(id);
        Optional<CreditsDTO> credits = getCredits(id);
        Optional<SimilarMoviesResultDTO> similarMovies = getSimilarMovies(id);
        Optional<ReviewsResultDTO> reviews = reviewService.getMovieReviews(id);
        return this.movieTransformer.convertMovieData(movieData, credits, reviews, similarMovies);
    }

    private Optional<CreditsDTO> getCredits(String id) {
        try {
            return Optional.ofNullable(this.tmdbConnector.getCredits(id));
        } catch (APIException e) {
            return Optional.empty();
        }
    }

    private Optional<SimilarMoviesResultDTO> getSimilarMovies(String id) {
        try {
            return Optional.ofNullable(this.tmdbConnector.getSimilarMovies(id));
        } catch (APIException e) {
            return Optional.empty();
        }
    }
}
