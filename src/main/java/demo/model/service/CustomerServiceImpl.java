package demo.model.service;

import demo.controller.Dao.CounterDao;
import demo.controller.Dao.CustomerDao;
import demo.controller.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


/**
 * @author Artur Kuzmik on 18.29.5
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;
    private CounterDao counterDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao, CounterDao counterDao) {
        this.customerDao = customerDao;
        this.counterDao = counterDao;
    }

    @Override
    public void addCustomer(Customer customer) {
        Date data;

        try {
            data = new SimpleDateFormat("yyyy-MM-dd").parse(customer.getOrderDate());

            if ((data != null ? data.compareTo(new Date()) : 0) < 0) {
                throw new IllegalArgumentException();
            }

            if (customer.getId() == null)
                customer.setId(counterDao.getNextSequence("customers"));

            customer.setName(stringFormat(customer.getName()));
            customer.setSurname(stringFormat(customer.getSurname()));

            customerDao.save(customer);

        } catch (ParseException e) {
            System.out.println("Incorrect stringFormat");
        }

    }

    @Override
    public void deleteCustomer(Long id) {
        if (find(id) == null)
            throw new NoSuchElementException();
        else
            customerDao.deleteById(id);
    }

    @Override
    public double allPrice() {
        return findAll().stream().mapToDouble(Customer::getCost).sum();

    }

    @Override
    public Customer find(long customerId) {
        try {
            Optional<Customer> customer = customerDao.findById(customerId);
            return customer.get();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public String stringFormat(String a) {
        String temp = a.trim();
        return temp.substring(0, 1).toUpperCase() + temp.substring(1);
    }

}
