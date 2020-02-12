package com.despegar.dasboot.service.list;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.model.list.ListInfo;
import com.despegar.dasboot.model.list.UserList;
import com.despegar.dasboot.model.list.UserListItem;
import com.despegar.dasboot.repository.ListRepository;
import com.despegar.dasboot.repository.entities.MovieItem;
import com.despegar.dasboot.repository.entities.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ListService {
    private ListRepository listRepository;
    private TMDBConnector connector;
    private AsyncTaskExecutor asyncTaskExecutor;
    private ListTransformer listTransformer;

    @Autowired
    public ListService(ListRepository listRepository,
                       TMDBConnector connector,
                       AsyncTaskExecutor commonThreadPoolTaskExecutor,
                       ListTransformer listTransformer) {
        this.listRepository = listRepository;
        this.connector = connector;
        this.asyncTaskExecutor = commonThreadPoolTaskExecutor;
        this.listTransformer = listTransformer;
    }

    public Optional<UserList> getList(String id) {
        return listRepository.findById(id).map(this::convert);
    }

    public Optional<UserList> addToList(String listId, List<String> movieIds) {
        List<MovieItem> movieItemList = movieIds.stream().map(listTransformer::convert).collect(Collectors.toList());
        listRepository.addMovieToList(listId, movieItemList);
        return getList(listId);
    }

    public Optional<UserList> deleteFromList(String listId, List<String> movieIds) {
        listRepository.removeMovieFromList(listId, movieIds);
        return getList(listId);
    }

    public void delete(String listId) {
        listRepository.deleteById(listId);
    }

    public UserList create(ListInfo listInfo) {
        MovieList list = listTransformer.convert(listInfo);
        MovieList insertedList = listRepository.insert(list);
        return convert(insertedList);
    }

    private UserList convert(MovieList list) {
        List<UserListItem> items =
                list.getItems().stream()
                        .map(i ->
                                CompletableFuture.supplyAsync(() -> connector.getMovie(i.getId()), asyncTaskExecutor)
                                        .thenApply(m -> listTransformer.convert(i, m)))
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        return new UserList(list.getId(), list.getUser(), list.getName(), items, list.getCreated());
    }
}
