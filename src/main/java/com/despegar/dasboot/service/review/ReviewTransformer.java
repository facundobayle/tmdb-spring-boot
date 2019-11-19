package com.despegar.dasboot.service.review;

import com.despegar.dasboot.connector.tmdb.dto.ReviewDTO;
import com.despegar.dasboot.connector.tmdb.dto.ReviewsResultDTO;
import com.despegar.dasboot.model.review.MovieReview;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ReviewTransformer {

    public List<MovieReview> convertMovieReviews(Optional<ReviewsResultDTO> reviewsResultDTO) {
        return reviewsResultDTO.map(reviewResult -> reviewResult.getResults().stream()
                .map(this::transform)
                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private MovieReview transform(ReviewDTO reviewDTO) {
        return new MovieReview(reviewDTO.getId(), reviewDTO.getAuthor(), reviewDTO.getContent());
    }
}
