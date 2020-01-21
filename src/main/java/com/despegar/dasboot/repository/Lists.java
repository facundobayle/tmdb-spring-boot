package com.despegar.dasboot.repository;

import com.despegar.dasboot.model.list.ListInfo;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class Lists {
    private Map<String, MovieList> lists;

    public Lists() {
        this.lists = new ConcurrentHashMap<>();
    }

    public List<String> getListsIds(String user) {
        return lists.values().stream()
                .filter(v -> v.getUser().equalsIgnoreCase(user))
                .map(MovieList::getId)
                .collect(Collectors.toList());
    }

    public Optional<MovieList> getList(String id) {
        return Optional.ofNullable(lists.get(id));
    }

    public Optional<MovieList> addToList(String listId, List<String> movies) {
        return Optional.ofNullable(lists.get(listId))
                .map(
                        l -> {
                            movies.forEach(m -> l.addItem(new MovieItem(m, LocalDate.now())));
                            return l;
                        });
    }

    public MovieList createList(ListInfo info) {
        String id = UUID.randomUUID().toString();
        MovieList movieList =
                new MovieList(id, info.getUser(), info.getName(), new HashSet<>(), LocalDate.now());
        lists.put(id, movieList);
        return movieList;
    }

    public Optional<MovieList> removeFromList(String listId, List<String> movies) {
        return Optional.ofNullable(lists.get(listId))
                .map(
                        l -> {
                            movies.forEach(l::removeItem);
                            return l;
                        });
    }

    public Optional<MovieList> removeList(String listId) {
        return Optional.ofNullable(lists.remove(listId));
    }
}
