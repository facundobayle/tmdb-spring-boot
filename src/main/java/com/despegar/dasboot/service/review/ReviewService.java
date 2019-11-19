package com.despegar.dasboot.service.review;

import com.despegar.dasboot.connector.exception.APIException;
import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.ReviewsResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private TMDBConnector tmdbConnector;

    @Autowired
    public ReviewService(TMDBConnector tmdbConnector) {
        this.tmdbConnector = tmdbConnector;
    }

    public Optional<ReviewsResultDTO> getMovieReviews(String id) {
        try {
            return Optional.ofNullable(this.tmdbConnector.getReviews(id));
        } catch (APIException e) {
            return Optional.empty();
        }
    }
}
