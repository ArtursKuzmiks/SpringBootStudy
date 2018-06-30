package demo.model.service;

import demo.controller.Entity.Customer;

import java.util.List;

/**
 * @author Artur Kuzmik on 18.29.5
 */

public interface CustomerService {

    void addCustomer(Customer customer);

    void deleteCustomer(Long id);

    double allPrice();

    Customer find(long customerId);

    List<Customer> findAll();

    String stringFormat(String string);
}
