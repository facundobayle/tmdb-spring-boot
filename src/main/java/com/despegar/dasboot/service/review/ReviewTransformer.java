package com.despegar.dasboot.service.review;

import com.despegar.dasboot.connector.tmdb.dto.ReviewDTO;
import com.despegar.dasboot.connector.tmdb.dto.ReviewsResultDTO;
import com.despegar.dasboot.model.review.MovieReview;
import com.despegar.dasboot.service.movie.MovieTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReviewTransformer {

    private Logger logger = LoggerFactory.getLogger(ReviewTransformer.class);

    public List<MovieReview> convertMovieReviews(Optional<ReviewsResultDTO> reviewsResultDTO) {
        logger.info("Transform movie reviews.");

        return reviewsResultDTO.map(reviewResult -> reviewResult.getResults().stream()
                .map(this::transform)
                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private MovieReview transform(ReviewDTO reviewDTO) {
        return new MovieReview(reviewDTO.getId(), reviewDTO.getAuthor(), reviewDTO.getContent());
    }
}
