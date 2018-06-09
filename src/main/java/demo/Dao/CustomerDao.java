package demo.Dao;

import demo.Entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Artur Kuzmik on 18.29.5
 */

public interface CustomerDao extends PagingAndSortingRepository<Customer, Long> {

    @Modifying
    @Query(value = "UPDATE Customer c set c.name=:name,c.surname=:surname," +
            "c.orderDate=:orderDate,c.cost=:cost,c.paid=:paid where c.ID=:id")
    @Transactional
    void update(@Param("id") Long id, @Param("name") String name, @Param("surname") String surname,
                @Param("orderDate") String orderDate, @Param("cost") double cost, @Param("paid") double paid);
    @Modifying
    @Query("FROM Customer c order by c.orderDate asc,c.surname asc ")
    List<Customer> sortDateSurname();

    @Modifying
    @Query("FROM Customer c where c.paid < c.cost")
    List<Customer> debtors();

}
