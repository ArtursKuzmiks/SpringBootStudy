package demo.controller.Dao;

import demo.controller.Entity.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


/**
 * @author Artur Kuzmik on 18.20.6
 */
@Repository
public class CounterDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public long getNextSequence(String collectionName){
        Counter counter = mongoTemplate.findAndModify(
                query(where("_id").is(collectionName)),
                new Update().inc("seq", 1),
                options().returnNew(true), Counter.class);

        return counter.getSeq();
    }
}