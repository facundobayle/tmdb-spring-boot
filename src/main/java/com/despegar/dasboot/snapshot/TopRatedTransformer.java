package com.despegar.dasboot.snapshot;

import com.despegar.dasboot.connector.tmdb.dto.TopRatedDTO;
import com.despegar.dasboot.model.movie.MovieInfo;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TopRatedTransformer {

    public Set<Long> transformTopRatedMovies(TopRatedDTO topRated) {
        return topRated.getResults().stream().map(MovieInfo::getId).collect(Collectors.toSet());
    }

}

