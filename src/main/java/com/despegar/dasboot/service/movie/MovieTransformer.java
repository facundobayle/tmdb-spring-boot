package com.despegar.dasboot.service.movie;

import com.despegar.dasboot.connector.tmdb.dto.*;
import com.despegar.dasboot.model.movie.Movie;
import com.despegar.dasboot.model.movie.MovieCast;
import com.despegar.dasboot.model.movie.MovieCrew;
import com.despegar.dasboot.model.movie.MovieInfo;
import com.despegar.dasboot.model.review.MovieReview;
import com.despegar.dasboot.service.review.ReviewTransformer;
import com.despegar.dasboot.snapshot.TopRatedSnapshot;
import com.despegar.dasboot.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MovieTransformer {

    private Logger logger = LoggerFactory.getLogger(MovieTransformer.class);

    private ReviewTransformer reviewTransformer;
    private DateUtils dateUtils;
    private TopRatedSnapshot topRatedSnapshot;

    private static final Set<String> jobs = new HashSet<>(Arrays.asList("director", "novel", "screenplay"));

    @Autowired
    public MovieTransformer(ReviewTransformer reviewTransformer,
                            DateUtils dateUtils,
                            TopRatedSnapshot topRatedSnapshot) {
        this.reviewTransformer = reviewTransformer;
        this.dateUtils = dateUtils;
        this.topRatedSnapshot = topRatedSnapshot;
    }

    public Movie convertMovieData(MovieDataDTO movieDataDTO,
                                  Optional<CreditsDTO> creditsDTO,
                                  Optional<ReviewsResultDTO> reviewsDTO,
                                  Optional<SimilarMoviesResultDTO> similarMoviesResultDTO) {

        logger.info("Transforming movie data...");

        List<String> genres = movieDataDTO.getGenres()
                .stream()
                .map(GenreDTO::getName)
                .collect(Collectors.toList());
        List<MovieCast> cast = this.getCast(creditsDTO);
        List<MovieCrew> crew = this.getCrew(creditsDTO);
        List<MovieReview> reviews = this.reviewTransformer.convertMovieReviews(reviewsDTO);
        List<MovieInfo> similarMovies = this.getSimilarMovies(similarMoviesResultDTO);
        boolean topRated = topRatedSnapshot.isTopRated(movieDataDTO.getId());

        logger.info("Movie data transformed. Send back movie information.");
        return new Movie(
                String.valueOf(movieDataDTO.getId()),
                movieDataDTO.getTitle(),
                movieDataDTO.getOverview(),
                genres,
                cast,
                crew,
                reviews,
                similarMovies,
                topRated
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
                        .filter(cr -> jobs.contains(cr.getJob().toLowerCase())) // for now only director
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
