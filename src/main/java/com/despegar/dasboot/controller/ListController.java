package com.despegar.dasboot.controller;

import com.despegar.dasboot.model.list.ListInfo;
import com.despegar.dasboot.model.MovieList;
import com.despegar.dasboot.model.list.Movies;
import org.springframework.web.bind.annotation.*;

@RestController //@Controller + @ResponseBody
public class ListController {

    @GetMapping(value = "/lists/{id}")
    public MovieList getList(@PathVariable String id) {
        return new MovieList();
    }

    @PostMapping(value = "/lists")
    public MovieList create(@RequestBody ListInfo listInfo) {
        return new MovieList();
    }

    @PostMapping(value = "/lists/{id}")
    public MovieList delete(@PathVariable(value = "id") String listId) {
        return new MovieList();
    }

    @DeleteMapping(value = "/lists/{listId}/movies")
    public MovieList deleteFromList(@RequestBody Movies movies, @PathVariable(value = "listId") String listId) {
        return new MovieList();
    }

    @PostMapping(value = "/lists/{listId}/movies")
    public MovieList addToList(@RequestBody Movies movies, @PathVariable(value = "listId") String listId) {
        return new MovieList();
    }

}
