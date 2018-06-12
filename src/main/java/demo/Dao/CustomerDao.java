package demo.Dao;

import demo.Entity.CustomerImpl;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Artur Kuzmik on 18.29.5
 */
@Repository
public interface CustomerDao extends PagingAndSortingRepository<CustomerImpl, Long> {

    @PostConstruct
    default void warmCache(){
        findAll();
    }



    @Modifying
    @Query("FROM CustomerImpl c order by c.orderDate asc,c.surname asc ")
    List<CustomerImpl> sortDateSurname();

    @Modifying
    @Query("FROM CustomerImpl c where c.paid < c.cost")
    List<CustomerImpl> debtors();

}
