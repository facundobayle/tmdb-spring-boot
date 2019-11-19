package com.despegar.dasboot.connector.tmdb;

import com.despegar.dasboot.connector.tmdb.config.TMDBConfig;
import com.despegar.dasboot.connector.tmdb.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TMDBConnector {

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
        return client.getForObject(MOVIE_URL, MovieDataDTO.class, id, token);
    }

    public ReviewsResultDTO getReviews(String id) { return client.getForObject(REVIEWS_URL, ReviewsResultDTO.class, id, token); }

    public SimilarMoviewsResultDTO getSimilarMovies(String id) { return client.getForObject(SIMILAR_URL, SimilarMoviewsResultDTO.class, id, token); }

    public CreditsDTO getCredits(String id) { return client.getForObject(CREDITS_URL, CreditsDTO.class, id, token); }
}
