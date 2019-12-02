package com.despegar.dasboot.service.search;

import com.despegar.dasboot.connector.tmdb.dto.SearchMovieDTO;
import com.despegar.dasboot.model.movie.MovieInfo;
import com.despegar.dasboot.utils.DateUtils;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SearchTransformer {

    private DateUtils dateUtils;

    @Autowired
    public SearchTransformer(DateUtils dateUtils) {
        this.dateUtils = dateUtils;
    }

    public List<MovieInfo> convertSearchMovieResult(List<SearchMovieDTO> searchMovieDTOList) {
        return searchMovieDTOList.stream().map(this::transform).collect(Collectors.toList());
    }

    private MovieInfo transform(SearchMovieDTO searchMovie) {
        Optional<String> releaseDate = Optional.ofNullable(searchMovie.getReleaseDate());
        String releaseYear = this.dateUtils.getYearFromDate(releaseDate);
        return new MovieInfo(searchMovie.getId(), searchMovie.getTitle(), releaseYear);
    }
}
