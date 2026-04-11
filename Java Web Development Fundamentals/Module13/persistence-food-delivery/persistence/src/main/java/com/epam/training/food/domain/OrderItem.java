package com.epam.training.food.domain;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private int pieces;
    private BigDecimal price;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
    public Food getFood() { return food; }
    public void setFood(Food food) { this.food = food; }
    public int getPieces() { return pieces; }
    public void setPieces(int pieces) { this.pieces = pieces; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", pieces=" + pieces +
                ", price=" + price +
                '}';
    }

}
