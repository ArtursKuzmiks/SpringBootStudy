package demo.Entity;


import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Artur Kuzmik on 18.29.5
 */

@Entity
@Component
@BatchSize(size = 5)
@Table(name = "md_2DB")
public class CustomerImpl implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Surname")
    private String surname;

    @Column(name = "orderDate")
    private String orderDate;

    @Column(name = "cost")
    private double cost;

    @Column(name = "paid")
    private double paid;

    public CustomerImpl() {
    }

    public CustomerImpl(String name, String surname, String orderDate, double cost, double paid) {
        this.name = name;
        this.surname = surname;
        this.orderDate = orderDate;
        this.cost = cost;
        this.paid = paid;

    }


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return String.format("%-5s\t%-15s\t%-15s\t%-15s\t%.2f\t%.2f",
                ID, name, surname, orderDate, cost, paid);
    }
}
