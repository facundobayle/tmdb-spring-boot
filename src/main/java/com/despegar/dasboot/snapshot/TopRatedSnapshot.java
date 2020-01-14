package com.despegar.dasboot.snapshot;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.TopRatedDTO;
import com.despegar.dasboot.controller.context.Context;
import com.despegar.dasboot.controller.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.despegar.dasboot.controller.Headers.UOW;

@Component
public class TopRatedSnapshot {
    private static final Logger logger = LoggerFactory.getLogger(TopRatedSnapshot.class);
    private TMDBConnector connector;
    private TopRatedTransformer transformer;
    private AtomicReference<Set<Long>> movies;

    @Autowired
    public TopRatedSnapshot(TMDBConnector connector, TopRatedTransformer transformer) {
        this.movies = new AtomicReference<>(new HashSet<>());
        this.connector = connector;
        this.transformer = transformer;
    }

    @Scheduled(fixedDelay = 60000)
    public void refresh() {
        logger.info("Refreshing top rated movies...");
        Context.setRequestContext(this.setContext());
        TopRatedDTO topMovies = connector.getTopRated();
        movies.set(transformer.transformTopRatedMovies(topMovies));
    }

    public boolean isTopRated(Long movieId) {
        return movies.get().contains(movieId);
    }

    private RequestContext setContext() {
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put(UOW, "top-rated-snapshot");

        return new RequestContext(customHeaders);
    }

}

