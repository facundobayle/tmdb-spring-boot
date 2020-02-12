package com.despegar.dasboot.repository;

import com.despegar.dasboot.repository.entities.MovieItem;
import com.despegar.dasboot.repository.entities.MovieList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListRepositoryImpl implements MovieListOperations {
    private MongoTemplate template;

    private static final String ID = "_id";
    private static final String ITEMS = "items";

    @Autowired
    public ListRepositoryImpl(MongoTemplate template) {
        this.template = template;
    }

    @Override
    public long addMovieToList(String listId, List<MovieItem> movieIds) {
        Query query = Query.query(Criteria.where(ID).is(listId));
        Update update = new Update().push(ITEMS).each(movieIds);
        return template.updateFirst(query, update, MovieList.class).getModifiedCount();
    }

    @Override
    public long removeMovieFromList(String listId, List<String> movieIds) {
        Query query = Query.query(Criteria.where(ID).is(listId));
        Update update = new Update().pull(ITEMS, Query.query(Criteria.where(ID).in(movieIds)));
        return template.updateFirst(query, update, MovieList.class).getModifiedCount();
    }

}
