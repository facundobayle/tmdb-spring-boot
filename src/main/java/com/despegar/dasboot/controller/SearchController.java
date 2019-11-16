package com.despegar.dasboot.controller;

import com.despegar.dasboot.model.movie.MovieData;
import com.despegar.dasboot.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController //@Controller + @ResponseBody
public class SearchController {

    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "/search")
    public List<MovieData> search(@RequestParam(value = "q") String query, @RequestParam Optional<Integer> page) {
        return this.searchService.searchMovie(query, page.orElse(1));
    }
}
