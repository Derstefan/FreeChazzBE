package com.freechess.server.persist;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface ItemRepository extends MongoRepository<GameEntry, String> {

    @Query("{name:'?0'}")
    GameEntry findItemByName(String name);

    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    List<GameEntry> findAll(String category);

    public long count();
}