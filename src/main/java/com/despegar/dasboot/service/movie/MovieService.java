package com.despegar.dasboot.service.movie;

import com.despegar.dasboot.aop.Performance;
import com.despegar.dasboot.connector.exception.APIException;
import com.despegar.dasboot.connector.exception.ServiceException;
import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.*;
import com.despegar.dasboot.model.movie.Movie;
import com.despegar.dasboot.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class MovieService {

    private TMDBConnector tmdbConnector;
    private ReviewService reviewService;
    private MovieTransformer movieTransformer;
    private AsyncTaskExecutor asyncTaskExecutor;

    @Autowired
    public MovieService(TMDBConnector tmdbConnector,
                        ReviewService reviewService,
                        MovieTransformer movieTransformer,
                        AsyncTaskExecutor commonThreadPoolTaskExecutor) {
        this.tmdbConnector = tmdbConnector;
        this.reviewService = reviewService;
        this.movieTransformer = movieTransformer;
        this.asyncTaskExecutor = commonThreadPoolTaskExecutor;
    }

    @Performance
    @Cacheable(value = "movies", unless = "!#result.topRated")
    public Optional<Movie> getMovie(String id) {
        MovieDataDTO movieData =  this.tmdbConnector.getMovie(id);
        CompletableFuture<Optional<CreditsDTO>> credits = CompletableFuture.supplyAsync(() -> getCredits(id), asyncTaskExecutor);
        CompletableFuture<Optional<SimilarMoviesResultDTO>> similarMovies = CompletableFuture.supplyAsync(() -> getSimilarMovies(id), asyncTaskExecutor);
        CompletableFuture<Optional<ReviewsResultDTO>> reviews = CompletableFuture.supplyAsync(() -> reviewService.getMovieReviews(id), asyncTaskExecutor);
        
        try {
            Movie movie = credits
                    .thenCompose(c ->
                            similarMovies.thenCombine(reviews, (s, r) ->
                                    this.movieTransformer.convertMovieData(movieData, c, r, s)))
                            .get();
            return Optional.of(movie);
        } catch (InterruptedException | ExecutionException e) {
            throw new ServiceException("Error retrieving movie: ", e);
        }
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
