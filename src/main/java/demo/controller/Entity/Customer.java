package demo.controller.Entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Artur Kuzmik on 18.29.5
 */
@Entity
@Document(collection = "customers")
public class Customer implements Serializable {


    @Id private Long id;

    private String name;

    private String surname;

    private String orderDate;

    private double cost;

    private double paid;

    public Customer() {
    }

    public Customer(String name, String surname, String orderDate, double cost, double paid) {
        this.name = name;
        this.surname = surname;
        this.orderDate = orderDate;
        this.cost = cost;
        this.paid = paid;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
                id, name, surname, orderDate, cost, paid);
    }
}
