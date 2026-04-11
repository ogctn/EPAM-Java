package com.epam.training.food.service;

import com.epam.training.food.aspect.EnableArgumentLogging;
import com.epam.training.food.aspect.EnableExecutionTimeLogging;
import com.epam.training.food.aspect.EnableReturnValueLogging;
import com.epam.training.food.domain.*;
import com.epam.training.food.repository.CustomerRepository;
import com.epam.training.food.repository.FoodRepository;
import com.epam.training.food.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultFoodDeliveryService implements FoodDeliveryService {

    private final CustomerRepository customerRepository;
    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;

    public DefaultFoodDeliveryService(CustomerRepository customerRepository,
                                      FoodRepository foodRepository,
                                      OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.foodRepository = foodRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @EnableArgumentLogging
    @EnableReturnValueLogging
    @EnableExecutionTimeLogging
    @Transactional(readOnly = true)
    public Customer authenticate(Credentials credentials) throws AuthenticationException {
        return customerRepository.findAll().stream()
                .filter(c -> c.getUserName().equals(credentials.getUserName()) &&
                        c.getPassword().equals(credentials.getPassword()))
                .findFirst()
                .orElseThrow(() -> new AuthenticationException("Credentials failed"));
    }

    @Override
    @EnableReturnValueLogging
    @EnableExecutionTimeLogging
    @Transactional(readOnly = true)
    public List<Food> listAllFood() {
        return foodRepository.findAll();
    }

    private Optional<OrderItem> getItemInCart(Cart cart, Food food) {
        if (cart.getOrderItems() == null) return Optional.empty();
        return cart.getOrderItems().stream()
                .filter(item -> item.getFood().getName().equals(food.getName()))
                .findFirst();
    }

    private void validateUCartArgs(Customer customer, Food food, int pieces) {
        if (pieces < 0)
            throw new IllegalArgumentException("Pieces negative");

        boolean exists = getItemInCart(customer.getCart(), food).isPresent();
        if (pieces == 0 && !exists)
            throw new IllegalArgumentException("!");
    }

    private void checkBalance(Customer customer, Food food, BigDecimal newItemPrice) {
        BigDecimal othersTotal = BigDecimal.ZERO.setScale(2);
        if (customer.getCart().getOrderItems() != null) {
            othersTotal = customer.getCart().getOrderItems().stream()
                    .filter(item -> !item.getFood().getName().equals(food.getName()))
                    .map(OrderItem::getPrice)
                    .reduce(BigDecimal.ZERO.setScale(2), BigDecimal::add);
        }

        if (othersTotal.add(newItemPrice).compareTo(customer.getBalance()) > 0) {
            throw new LowBalanceException("Unable to add current order for " + food.getName() + ", as with current cart content it would exceed available balance!");
        }
    }

    private void recalculateCartTotal(Cart cart) {
        if (cart.getOrderItems() == null || cart.getOrderItems().isEmpty()) {
            cart.setPrice(BigDecimal.ZERO.setScale(2));
            return;
        }

        cart.setPrice(
                cart.getOrderItems().stream()
                        .map(OrderItem::getPrice)
                        .reduce(BigDecimal.ZERO.setScale(2), BigDecimal::add)
        );
    }

    @Override
    public void updateCart(Customer customer, Food food, int pieces) throws LowBalanceException {
        if (customer.getCart() == null) customer.setCart(new Cart());
        Cart cart = customer.getCart();
        if (cart.getOrderItems() == null) cart.setOrderItems(new ArrayList<>());

        validateUCartArgs(customer, food, pieces);
        Optional<OrderItem> existing = getItemInCart(cart, food);

        if (pieces == 0) {
            existing.ifPresent(item -> cart.getOrderItems().remove(item));
        } else {
            BigDecimal newItemPrice = food.getPrice().multiply(BigDecimal.valueOf(pieces));
            checkBalance(customer, food, newItemPrice);

            if (existing.isPresent()) {
                existing.get().setPieces(pieces);
                existing.get().setPrice(newItemPrice);
            } else {
                OrderItem newItem = new OrderItem();
                newItem.setFood(food);
                newItem.setPieces(pieces);
                newItem.setPrice(newItemPrice);
                cart.getOrderItems().add(newItem);
            }
        }
        recalculateCartTotal(cart);
    }

    @Override
    @Transactional
    public Order createOrder(Customer customer) throws IllegalStateException {
        Cart cart = customer.getCart();
        if (cart == null || cart.getOrderItems() == null || cart.getOrderItems().isEmpty())
            throw new IllegalStateException("Empty card");

        if (customer.getBalance().compareTo(cart.getPrice()) < 0)
            throw new LowBalanceException("");

        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        newOrder.setPrice(cart.getPrice());
        newOrder.setTimestamp(LocalDateTime.now());

        for (OrderItem cartItem : cart.getOrderItems()) {
            cartItem.setOrder(newOrder);
            newOrder.getOrderItems().add(cartItem);
        }

        customer.setBalance(customer.getBalance().subtract(newOrder.getPrice()));
        customerRepository.save(customer);
        Order savedOrder = orderRepository.save(newOrder);

        cart.setOrderItems(new ArrayList<>());
        cart.setPrice(BigDecimal.ZERO.setScale(2));

        return savedOrder;
    }
}