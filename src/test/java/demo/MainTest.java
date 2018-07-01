package demo;

import demo.controller.Dao.CounterDao;
import demo.controller.Dao.CustomerDao;
import demo.controller.Entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Artur Kuzmik on 18.8.6
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppTestConfig.class})
@ActiveProfiles("test")
@EnableMongoRepositories
public class MainTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CounterDao counterDao;


    private int count = 5;


    @Before
    public void setUp() {

        for (int i = 0; i < count; i++) {
            Customer customer = new Customer();
            customer.setName("testName" + i);
            customer.setSurname("testSurname" + i);
            customer.setOrderDate("testOrderDate" + i);
            customer.setCost(i);
            customer.setPaid(i);
            customer.setId(counterDao.getNextSequence("customers"));
            customerDao.save(customer);
        }


    }

    @Test
    public void testFindAll() {
        Iterable<Customer> customers = customerDao.findAll();
        assertThat(customers).hasSize(count);
        customerDao.deleteAll();
    }

    @Test
    public void testCount() {
        List<Customer> customers =  customerDao.findAll();
        assertEquals(customers.size(), count);
    }

    @Test
    public void testDelete() {
        customerDao.deleteAll();
        List<Customer> customers = customerDao.findAll();
        assertThat(customers).isEmpty();
    }

    @Test
    public void testStoreCustomer() {
        Customer customer = new Customer("Jack", "Smith", "test", 1, 1);
        customer.setId(counterDao.getNextSequence("customers"));

        customerDao.save(customer);
        assertThat(customer).hasFieldOrPropertyWithValue("name", "Jack");
        assertThat(customer).hasFieldOrPropertyWithValue("surname", "Smith");
    }

    @Test
    public void testUpdate() {
        Customer customer;
        List<Customer> customers = customerDao.findAll();
        customer = customers.get(count - 1);
        customer.setName("testUpdate");
        customerDao.save(customer);
        assertThat(customer).hasFieldOrPropertyWithValue("name", customer.getName());
        assertThat(customers).hasSize(count);
        customerDao.deleteAll();
    }

}