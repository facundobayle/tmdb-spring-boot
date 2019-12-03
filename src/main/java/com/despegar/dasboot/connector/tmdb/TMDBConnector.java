package com.despegar.dasboot.connector.tmdb;

import com.despegar.dasboot.connector.tmdb.config.TMDBConfig;
import com.despegar.dasboot.connector.tmdb.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TMDBConnector {

    private Logger logger = LoggerFactory.getLogger(TMDBConnector.class);
    private static final String MOVIE_URL = "/movie/{id}?api_key={token}";
    private static final String REVIEWS_URL = "/movie/{id}/reviews?api_key={token}";
    private static final String SIMILAR_URL = "/movie/{id}/similar?api_key={token}";
    private static final String CREDITS_URL = "/movie/{id}/credits?api_key={token}";
    private static final String SEARCH_MOVIE_URL = "/search/movie?api_key={token}&query={query}&page={page}";

    private RestTemplate client;
    private String token;

    @Autowired
    public TMDBConnector(RestTemplate tmdbClient, TMDBConfig config) {
        this.client = tmdbClient;
        this.token = config.getToken();
    }

    public MovieDataDTO getMovie(String id) {
        logger.info("Calling TMDB movie service with id {}", id);
        return client.getForObject(MOVIE_URL, MovieDataDTO.class, id, token);
    }

    public ReviewsResultDTO getReviews(String id) {
        logger.info("Calling TMDB reviews service with id {}", id);
        return client.getForObject(REVIEWS_URL, ReviewsResultDTO.class, id, token);
    }

    public SimilarMoviesResultDTO getSimilarMovies(String id) {
        logger.info("Calling TMDB similar movies service with id {}", id);
        return client.getForObject(SIMILAR_URL, SimilarMoviesResultDTO.class, id, token);
    }

    public CreditsDTO getCredits(String id) {
        logger.info("Calling TMDB credits service with id {}", id);
        return client.getForObject(CREDITS_URL, CreditsDTO.class, id, token);
    }

    public SearchResultsDTO getSearchResults(String query, Integer page) {
        logger.info("Calling TMDB search result service with query: {}", query);
        return client.getForObject(SEARCH_MOVIE_URL, SearchResultsDTO.class, token, query, page);
    }
}
