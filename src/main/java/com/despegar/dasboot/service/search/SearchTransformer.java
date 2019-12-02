package com.despegar.dasboot.service.search;

import com.despegar.dasboot.connector.tmdb.dto.SearchMovieDTO;
import com.despegar.dasboot.model.movie.MovieInfo;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchTransformer {

    public List<MovieInfo> convertSearchMovieResult(List<SearchMovieDTO> searchMovieDTOList) {
        return searchMovieDTOList.stream().map(this::transform).collect(Collectors.toList());
    }

    private MovieInfo transform(SearchMovieDTO searchMovie) {
        String releaseYear = Optional.ofNullable(searchMovie.getReleaseDate())
                .map(LocalDate::parse)
                .map(d -> String.valueOf(d.getYear()))
                .orElse("");
        return new MovieInfo(searchMovie.getId(), searchMovie.getTitle(), releaseYear);
    }
}
