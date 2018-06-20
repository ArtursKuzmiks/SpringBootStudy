package demo.model;

import demo.controller.Dao.CounterDao;
import demo.controller.Dao.CustomerDao;
import demo.controller.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private CustomerDao customerDao;
    private CounterDao counterDao;


    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao, CounterDao counterDao) {
        this.customerDao = customerDao;
        this.counterDao = counterDao;
    }

    @Override
    public void addCustomer() throws IOException {
        Date data = null;
        Customer customer = new Customer();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

        try {

            System.out.println("Add Customer");

            System.out.print("Name: ");
            customer.setName(stringFormat(reader.readLine()));

            System.out.print("Surname: ");
            customer.setSurname(stringFormat(reader.readLine()));

            System.out.print("Date(yyyy-MM-dd):");

            try {

                boolean end = false;
                while (!end) {
                    data = new SimpleDateFormat("yyyy-MM-dd").parse(reader.readLine());
                    if ((data != null ? data.compareTo(new Date()) : 0) < 0) {
                        System.out.println("We do not have a time machine :)");
                        System.out.print("Date(yyyy-MM-dd):");
                    } else {
                        end = true;
                    }
                }

            } catch (ParseException e) {
                System.out.println("Incorrect stringFormat");
            }

            customer.setOrderDate(sim.format(data));

            System.out.print("Cost: ");
            customer.setCost(Float.parseFloat(reader.readLine()));

            System.out.print("Paid: ");
            customer.setPaid(Float.parseFloat(reader.readLine()));

            customer.setId(counterDao.getNextSequence("customers"));

            customerDao.save(customer);


        } catch (IllegalArgumentException e) {
            System.out.println("Input error");
        }

    }

    @Override
    public void editCustomer() throws IOException {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");

        try {
            System.out.print("Input Customer ID: ");

            Customer customer = find(Long.parseLong(reader.readLine()));


            if (customer != null) {
                System.out.println("Customer:");
                tableTitle();
                System.out.println(customer.toString());
                System.out.println();

                try {
                    System.out.println("Editing menu");
                    System.out.println("1: Name");
                    System.out.println("2: Surname");
                    System.out.println("3: Order date");
                    System.out.println("4: Cost");
                    System.out.println("5: Paid");
                    System.out.println("0: End");

                    loop:
                    for (; ; ) {

                        System.out.print("Menu: ");
                        int menu = Integer.parseInt(reader.readLine());

                        switch (menu) {
                            case 1:
                                System.out.print("Input Name: ");
                                customer.setName(stringFormat(reader.readLine()));
                                break;
                            case 2:
                                System.out.print("Input Surname: ");
                                customer.setSurname(stringFormat(reader.readLine()));
                                break;
                            case 3:
                                System.out.print("Input date (yyyy-MM-dd): ");

                                try {
                                    Date date = new SimpleDateFormat("yyyy-MM-dd").
                                            parse(reader.readLine());

                                    if (date.compareTo(new Date()) < 0)
                                        System.out.println("We do not have a time machine :)");
                                    else
                                        customer.setOrderDate(sim.format(date));
                                } catch (ParseException e) {
                                    System.out.println("Incorrect date stringFormat");
                                }
                                break;
                            case 4:
                                System.out.print("Input cost: ");
                                customer.setCost(Float.parseFloat(reader.readLine()));
                                break;
                            case 5:
                                System.out.print("Input paid: ");
                                float paid = Float.parseFloat(reader.readLine());

                                if (paid > customer.getCost())
                                    System.out.println("Paid greater then cost, error");
                                else
                                    customer.setPaid(paid);
                                break;
                            case 0:
                                break loop;
                            default:
                                System.out.println("There is no such option, try again: ");
                        }
                    }

                    customerDao.save(customer);

                } catch (IllegalArgumentException e) {
                    System.out.println("Input error");
                }

            } else
                System.out.println("There is no such ID");

        } catch (IllegalArgumentException e) {
            System.out.println("Input error");
        }
    }

    @Override
    public void deleteCustomer() throws IOException {

        try {

            System.out.print("Input Customer ID: ");

            long customerId = Long.parseLong(reader.readLine());

            if (find(customerId) != null)
                customerDao.deleteById(customerId);
            else
                System.out.println("There is no such ID");

        } catch (IllegalArgumentException e) {
            System.out.println("Input error");
        }

    }

    @Override
    public void sortDateSurname() {
        tableTitle();
        customerDao.findAll(new Sort(Sort.Direction.ASC, "orderDate")
                .and(new Sort(Sort.Direction.ASC, "surname")))
                .forEach(customer -> System.out.println(customer.toString()));

        System.out.println();

    }

    @Override
    public void debtors() {
        tableTitle();
        customerDao.findAll().stream()
                .filter(customer -> customer.getPaid() < customer.getCost())
                .forEach(customer -> System.out.println(customer.toString()));
        System.out.println();
    }

    @Override
    public void allPrice() {
        double amount = 0;
        amount += findAll().stream().mapToDouble(Customer::getCost).sum();
        System.out.printf("Cost of all orders: %.2f\n", amount);

    }

    @Override
    public Customer find(long customerId) {
        try {
            Optional<Customer> customer = customerDao.findById(customerId);
            return customer.get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public void printCustomers() {
        tableTitle();
        findAll().forEach(customer -> System.out.println(customer.toString()));
        System.out.println();

    }

    private void tableTitle() {
        System.out.printf("\n%-5s\t%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                "ID", "Name", "Surname", "Date", "Cost", "Paid");

        System.out.printf("%-5s\t%-15s\t%-15s\t%-15s\t%-5s\t%s\n",
                "----", "-------", "--------", "----------", "-----", "-----");
    }

    private String stringFormat(String a) {
        String temp = a.trim();
        return temp.substring(0, 1).toUpperCase() + temp.substring(1);

    }

}
