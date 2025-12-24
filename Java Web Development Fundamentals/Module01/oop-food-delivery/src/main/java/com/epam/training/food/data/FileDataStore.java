package com.epam.training.food.data;

import com.epam.training.food.data.DataStore;
import com.epam.training.food.domain.Customer;
import com.epam.training.food.domain.Food;
import com.epam.training.food.domain.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileDataStore implements DataStore {

    private final String baseDirPath;
    private List<Customer> customers;
    private List<Food> foods;
    private List<Order> orders;


    public FileDataStore(String inputFolderPath) {
        baseDirPath = inputFolderPath;
    }

    public void init() {
        customers = (new CustomerReader()).read(this.baseDirPath + File.separator +  "customers.csv");
        foods = (new FoodReader()).read(this.baseDirPath + File.separator + "foods.csv");
        orders = new ArrayList<>();
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public List<Food> getFoods() {
        return foods;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    private long getNextIdLong() {
        return orders.stream()
                .mapToLong(o -> o.getOrderId() == null ? -1L : o.getOrderId())
                .max()
                .orElse(-1L) + 1;
    }

    private Customer getCustomerById(long id) {
        return customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Order createOrder(Order order) {

        order.setOrderId(getNextIdLong());
        orders.add(order);
        Customer owner = getCustomerById(order.getCustomerId());
        if (owner != null)
            owner.getOrders().add(order);
        return (order);
    }

    @Override
    public void writeOrders() {
        (new OrderWriter()).writeOrders(orders, baseDirPath + File.separator + "orders.csv");
    }
}
