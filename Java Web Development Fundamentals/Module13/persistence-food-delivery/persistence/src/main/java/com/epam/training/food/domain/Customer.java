package com.epam.training.food.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Customer extends Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private BigDecimal balance;

    @Transient
    private Cart cart;

    public Customer() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(name, customer.name);
    }

    @Override
    public int hashCode() { return Objects.hash(super.hashCode(), id, name); }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + getUserName() + '\'' +
                '}';
    }
}