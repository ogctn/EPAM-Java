package com.epam.training.food.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {

    private List<OrderItem> orderItems = new ArrayList<>();
    private BigDecimal price = BigDecimal.ZERO;

    public Cart() {}

    private Cart(List<OrderItem> orderItems, BigDecimal price) {
        this.orderItems = orderItems;
        this.price = price;
    }

    public static Cart getEmptyCart() { return new Cart(new ArrayList<>(), BigDecimal.ZERO); }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public void addOrderItem(OrderItem item) {
        if (this.orderItems == null)
            this.orderItems = new ArrayList<>();
        if (this.price == null)
            this.price = BigDecimal.ZERO;
        this.orderItems.add(item);
        this.price = this.price.add(item.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cart cart = (Cart) o;
        return Objects.equals(orderItems, cart.orderItems) && Objects.equals(price, cart.price);
    }

    @Override
    public int hashCode() { return Objects.hash(orderItems, price); }
}