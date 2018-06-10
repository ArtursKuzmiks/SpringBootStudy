package demo.Dao;

import demo.Entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;

/**
 * @author Artur Kuzmik on 18.29.5
 */

public interface CustomerDao extends PagingAndSortingRepository<Customer, Long> {


    @Modifying
    @Query("FROM Customer c order by c.orderDate asc,c.surname asc ")
    List<Customer> sortDateSurname();

    @Modifying
    @Query("FROM Customer c where c.paid < c.cost")
    List<Customer> debtors();

}
