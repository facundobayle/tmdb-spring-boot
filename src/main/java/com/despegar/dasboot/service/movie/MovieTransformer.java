package com.despegar.dasboot.service.movie;

import com.despegar.dasboot.connector.tmdb.dto.*;
import com.despegar.dasboot.model.movie.Movie;
import com.despegar.dasboot.model.movie.MovieCast;
import com.despegar.dasboot.model.movie.MovieCrew;
import com.despegar.dasboot.model.movie.MovieInfo;
import com.despegar.dasboot.model.review.MovieReview;
import com.despegar.dasboot.service.review.ReviewTransformer;
import com.despegar.dasboot.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieTransformer {

    private ReviewTransformer reviewTransformer;
    private DateUtils dateUtils;

    private static final String DIRECTOR = "director";

    @Autowired
    public MovieTransformer(ReviewTransformer reviewTransformer,
                            DateUtils dateUtils) {
        this.reviewTransformer = reviewTransformer;
        this.dateUtils = dateUtils;
    }

    public Movie convertMovieData(MovieDataDTO movieDataDTO,
                                  Optional<CreditsDTO> creditsDTO,
                                  Optional<ReviewsResultDTO> reviewsDTO,
                                  Optional<SimilarMoviesResultDTO> similarMoviesResultDTO) {

        List<String> genres = movieDataDTO.getGenres()
                .stream()
                .map(GenreDTO::getName)
                .collect(Collectors.toList());
        List<MovieCast> cast = this.getCast(creditsDTO);
        List<MovieCrew> crew = this.getCrew(creditsDTO);
        List<MovieReview> reviews = this.reviewTransformer.convertMovieReviews(reviewsDTO);
        List<MovieInfo> similarMovies = this.getSimilarMovies(similarMoviesResultDTO);

        return new Movie(
                String.valueOf(movieDataDTO.getId()),
                movieDataDTO.getTitle(),
                movieDataDTO.getOverview(),
                genres,
                cast,
                crew,
                reviews,
                similarMovies
        );
    }

    private List<MovieCast> getCast(Optional<CreditsDTO> creditsDTO) {
        return creditsDTO
                .map(credits -> credits.getCast().stream()
                                .map(this::transform)
                                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private List<MovieCrew> getCrew(Optional<CreditsDTO> creditsDTO) {
        return creditsDTO
                .map(credits -> credits.getCrew().stream()
                        .filter(cr -> DIRECTOR.equals(cr.getJob().toLowerCase())) // for now only director
                        .map(this::transform)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private List<MovieInfo> getSimilarMovies(Optional<SimilarMoviesResultDTO> similarMoviesResultDTO) {
        return similarMoviesResultDTO
                .map(similarMoviesResult -> similarMoviesResult.getResults().stream()
                        .limit(5)
                        .map(this::transform)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    private MovieCast transform(CastDTO castDTO) {
        return new MovieCast(castDTO.getId(), castDTO.getCharacter(), castDTO.getName(), castDTO.getProfilePath());

    }

    private MovieCrew transform(CrewDTO crewDTO) {
        return new MovieCrew(crewDTO.getId(), crewDTO.getJob(), crewDTO.getName(), crewDTO.getProfilePath());
    }

    private MovieInfo transform(SimilarMoviesDTO similarMoviesDTO) {
        Optional<String> releaseDate = Optional.ofNullable(similarMoviesDTO.getReleaseDate());
        String releaseYear = this.dateUtils.getYearFromDate(releaseDate);
        return new MovieInfo(similarMoviesDTO.getId(), similarMoviesDTO.getTitle(), releaseYear);
    }
}
