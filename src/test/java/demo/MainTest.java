package demo;

import demo.Dao.CustomerDao;
import demo.Entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Artur Kuzmik on 18.8.6
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Main.class, AppConfig.class})
@Transactional
public class MainTest {

    @Autowired
    private CustomerDao customerDao;

    @Before
    @Rollback(false)
    public void setUp(){

        Customer customer1 = new Customer("Test","Test","2018-06-29",25, 25);
        Customer customer2 = new Customer("Test2","Test2","2018-06-28",25, 23.03);
        Customer customer3 = new Customer("Test3","Test3","2018-06-27",25, 0);

        customerDao.saveAll(Arrays.asList(customer1,customer2,customer3));

    }

    @Test
    public void testFindAll(){
        Iterable<Customer> customers = customerDao.findAll();
        customers.forEach(customer -> System.out.println(customer.toString()));
    }

    @Test
    public void testCount(){
        List<Customer> customers = (List<Customer>) customerDao.findAll();
        assertEquals(customers.size(),3);
    }

    @Test
    public void testDelete(){
        customerDao.deleteAll();
        List<Customer> customers = (List<Customer>) customerDao.findAll();
        assertEquals(customers.size(),0);
    }

}