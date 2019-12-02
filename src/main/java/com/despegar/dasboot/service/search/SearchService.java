package com.despegar.dasboot.service.search;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.SearchMovieDTO;
import com.despegar.dasboot.connector.tmdb.dto.SearchResultsDTO;
import com.despegar.dasboot.model.movie.MovieInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SearchService {

    private TMDBConnector tmdbConnector;
    private SearchTransformer searchTransformer;

    @Autowired
    public SearchService(TMDBConnector tmdbConnector, SearchTransformer searchTransformer) {
        this.tmdbConnector = tmdbConnector;
        this.searchTransformer = searchTransformer;
    }

    public List<MovieInfo> searchMovie(String query, Integer page) {
        SearchResultsDTO searchResults = this.tmdbConnector.getSearchResults(query, page);
        return this.searchTransformer.convertSearchMovieResult(searchResults.getResults());
    }
}
