package com.despegar.dasboot.service.list;

import com.despegar.dasboot.connector.tmdb.TMDBConnector;
import com.despegar.dasboot.connector.tmdb.dto.MovieDataDTO;
import com.despegar.dasboot.model.list.ListInfo;
import com.despegar.dasboot.model.list.UserList;
import com.despegar.dasboot.model.list.UserListItem;
import com.despegar.dasboot.model.movie.MovieInfo;
import com.despegar.dasboot.repository.Lists;
import com.despegar.dasboot.repository.MovieItem;
import com.despegar.dasboot.repository.MovieList;
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
    private Lists lists;
    private TMDBConnector connector;
    private AsyncTaskExecutor asyncTaskExecutor;


    @Autowired
    public ListService(Lists lists, TMDBConnector connector, AsyncTaskExecutor commonThreadPoolTaskExecutor) {
        this.lists = lists;
        this.connector = connector;
        this.asyncTaskExecutor = commonThreadPoolTaskExecutor;
    }

    public Optional<UserList> getList(String id) {
        return lists.getList(id).map(this::convert);
    }

    public Optional<UserList> addToList(String listId, List<String> movieIds) {
        return lists.addToList(listId, movieIds).map(this::convert);
    }

    public Optional<UserList> deleteFromList(String listId, List<String> movieIds) {
        return lists.removeFromList(listId, movieIds).map(this::convert);
    }

    public Optional<UserList> delete(String listId) {
        return lists.removeList(listId).map(this::convert);
    }

    public UserList create(ListInfo listInfo) {
        MovieList list = lists.createList(listInfo);
        return convert(list);
    }

    private UserList convert(MovieList list) {
        List<UserListItem> items =
                list.getItems().stream()
                        .map(i ->
                                CompletableFuture.supplyAsync(() -> connector.getMovie(i.getId()), asyncTaskExecutor)
                                        .thenApply(m -> convert(i, m)))
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        return new UserList(list.getId(), list.getUser(), list.getName(), items, list.getCreated());
    }

    private UserListItem convert(MovieItem item, MovieDataDTO movie) {
        MovieInfo info = new MovieInfo(movie.getId(), movie.getTitle(), movie.getReleaseDate());
        return new UserListItem(info, item.getAdded());
    }
}
