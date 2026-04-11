package com.epam.training.food.service;

import com.epam.training.food.domain.*;

import com.epam.training.food.repository.CustomerRepository;
import com.epam.training.food.repository.FoodRepository;
import com.epam.training.food.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultOrderService implements FoodDeliveryService {

    private final CustomerRepository customerRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;

    public DefaultOrderService(CustomerRepository customerRepository,
                               FoodRepository foodRepository,
                               OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.foodRepository = foodRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer authenticate(Credentials credentials) throws AuthenticationException {
        return customerRepository.findByEmail(credentials.getUserName())
                .filter(customer -> customer.getPassword().equals(credentials.getPassword()))
                .orElseThrow(() -> new AuthenticationException(""));
    }

    @Transactional
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Food> listAllFood() {
        return foodRepository.findAll();
    }

    @Override
    public void updateCart(Customer customer, Food food, int pieces) throws LowBalanceException {
        if (customer.getCart() == null)
            customer.setCart(new Cart());
        BigDecimal itemTotalPrice = food.getPrice().multiply(BigDecimal.valueOf(pieces));
        BigDecimal currentCartTotal = customer.getCart().getPrice() != null ?
                customer.getCart().getPrice() :
                BigDecimal.ZERO;
        BigDecimal newTotal = currentCartTotal.add(itemTotalPrice);
        if (customer.getBalance().compareTo(newTotal) < 0)
            throw new LowBalanceException("");
        OrderItem cartItem = new OrderItem();
        cartItem.setFood(food);
        cartItem.setPieces(pieces);
        cartItem.setPrice(itemTotalPrice);
        customer.getCart().addOrderItem(cartItem);
    }

    @Override
    @Transactional
    public Order createOrder(Customer customer) throws IllegalStateException {
        Cart cart = customer.getCart();
        if (cart == null || cart.getOrderItems().isEmpty())
            throw new IllegalStateException("");
        BigDecimal orderTotal = cart.getPrice();
        if (customer.getBalance().compareTo(orderTotal) < 0)
            throw new IllegalStateException("");
        customer.setBalance(customer.getBalance().subtract(orderTotal));
        customerRepository.save(customer);
        Order order = new Order();
        order.setCustomer(customer);
        order.setTimestamp(LocalDateTime.now());
        order.setPrice(orderTotal);
        for (OrderItem cartItem : cart.getOrderItems()) {
            OrderItem newOrderItem = new OrderItem();
            newOrderItem.setFood(cartItem.getFood());
            newOrderItem.setPieces(cartItem.getPieces());
            newOrderItem.setPrice(cartItem.getPrice());
            order.addOrderItem(newOrderItem);
        }
        Order savedOrder = orderRepository.save(order);
        customer.setCart(new Cart());

        return savedOrder;
    }
}