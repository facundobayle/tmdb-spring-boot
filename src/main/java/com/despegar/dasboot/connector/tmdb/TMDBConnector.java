package com.despegar.dasboot.connector.tmdb;

import com.despegar.dasboot.Performance;
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
    private static final String TOP_RATED_URL = "/movie/top_rated?api_key={token}";

    private RestTemplate client;
    private String token;

    @Autowired
    public TMDBConnector(RestTemplate tmdbClient, TMDBConfig config) {
        this.client = tmdbClient;
        this.token = config.getToken();
    }

    @Performance
    public MovieDataDTO getMovie(String id) {
        return client.getForObject(MOVIE_URL, MovieDataDTO.class, id, token);
    }

    @Performance
    public ReviewsResultDTO getReviews(String id) {
        return client.getForObject(REVIEWS_URL, ReviewsResultDTO.class, id, token);
    }

    @Performance
    public SimilarMoviesResultDTO getSimilarMovies(String id) {
        return client.getForObject(SIMILAR_URL, SimilarMoviesResultDTO.class, id, token);
    }

    @Performance
    public CreditsDTO getCredits(String id) {
        return client.getForObject(CREDITS_URL, CreditsDTO.class, id, token);
    }

    @Performance
    public SearchResultsDTO getSearchResults(String query, Integer page) {
        return client.getForObject(SEARCH_MOVIE_URL, SearchResultsDTO.class, token, query, page);
    }

    @Performance
    public TopRatedDTO getTopRated() {
        return client.getForObject(TOP_RATED_URL, TopRatedDTO.class, token);
    }
}
