package demo;

import demo.Dao.CustomerDao;
import demo.Entity.CustomerImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Artur Kuzmik on 18.8.6
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Main.class, AppTestConfig.class})
@ActiveProfiles("test")
@Transactional
public class MainTest {

    @Autowired
    private CustomerDao customerDao;


    private int count = 5;


    @Before
    @Rollback(false)
    public void setUp() {

        for (int i = 0; i < count; i++) {
            CustomerImpl customer = new CustomerImpl();
            customer.setName("testName" + i);
            customer.setSurname("testSurname" + i);
            customer.setOrderDate("testOrderDate" + i);
            customer.setCost(i);
            customer.setPaid(i);
            customerDao.save(customer);
        }


    }

    @Test
    public void testFindAll() {
        Iterable<CustomerImpl> customers = customerDao.findAll();
        assertThat(customers).hasSize(count);
    }

    @Test
    public void testCount() {
        List<CustomerImpl> customers = (List<CustomerImpl>) customerDao.findAll();
        assertEquals(customers.size(), count);
    }

    @Test
    public void testDelete() {
        customerDao.deleteAll();
        List<CustomerImpl> customers = (List<CustomerImpl>) customerDao.findAll();
        assertThat(customers).isEmpty();
    }

    @Test
    public void testStoreCustomer() {
        CustomerImpl customer = new CustomerImpl("Jack", "Smith", "test", 1, 1);
        customerDao.save(customer);
        assertThat(customer).hasFieldOrPropertyWithValue("name", "Jack");
        assertThat(customer).hasFieldOrPropertyWithValue("surname", "Smith");
    }

    @Test
    @Transactional
    public void testUpdate() {
        CustomerImpl customer;
        List<CustomerImpl> customers = (List<CustomerImpl>) customerDao.findAll();
        customer = customers.get(count - 1);
        customer.setName("testUpdate");
        customerDao.save(customer);
        assertThat(customer).hasFieldOrPropertyWithValue("name", customer.getName());
    }

}