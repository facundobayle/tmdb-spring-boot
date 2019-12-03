package com.despegar.dasboot.controller;

import com.despegar.dasboot.model.list.ListInfo;
import com.despegar.dasboot.model.MovieList;
import com.despegar.dasboot.model.list.Movies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController //@Controller + @ResponseBody
public class ListController {

    private Logger logger = LoggerFactory.getLogger(ListController.class);

    @GetMapping(value = "/lists/{id}")
    public MovieList getList(@PathVariable String id) {
        logger.info("getList request received for id {}", id);
        return new MovieList();
    }

    @PostMapping(value = "/lists")
    public MovieList create(@RequestBody ListInfo listInfo) {
        logger.info("createList request received with body: {}", listInfo);
        return new MovieList();
    }

    @PostMapping(value = "/lists/{id}")
    public MovieList delete(@PathVariable(value = "id") String listId) {
        logger.info("deleteList request received for listId {}", listId);
        return new MovieList();
    }

    @DeleteMapping(value = "/lists/{listId}/movies")
    public MovieList deleteFromList(@RequestBody Movies movies, @PathVariable(value = "listId") String listId) {
        logger.info("deleteMovieFromList request received for listId {}, with body: {}", listId, movies);
        return new MovieList();
    }

    @PostMapping(value = "/lists/{listId}/movies")
    public MovieList addToList(@RequestBody Movies movies, @PathVariable(value = "listId") String listId) {
        logger.info("addMovieToList request received for listId {}, with body: {}", listId, movies);
        return new MovieList();
    }

}
