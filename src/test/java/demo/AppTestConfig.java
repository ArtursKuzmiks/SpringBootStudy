package demo;

import com.mongodb.MongoClient;
import demo.controller.Dao.CounterDao;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;



/**
 * @author Artur Kuzmik on 18.29.5
 */

@TestConfiguration
@Profile("test")
public class AppTestConfig {

    @Bean
    public MongoDbFactory mongoDbFactory(){
        MongoClient mongoClient = new MongoClient("localhost");

        return new SimpleMongoDbFactory(mongoClient,"test");
    }

    @Bean
    public MongoTemplate mongoTemplate(){
        return new MongoTemplate(mongoDbFactory());
    }

    @Bean
    public CounterDao counterDao(){
        return new CounterDao();
    }
}
