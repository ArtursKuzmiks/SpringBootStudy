package demo.controller.Dao;

import demo.controller.Entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * @author Artur Kuzmik on 18.29.5
 */
@Repository
public interface CustomerDao extends MongoRepository<Customer, Long> {


}
