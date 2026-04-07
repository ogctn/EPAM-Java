package com.epam.training.food;

import com.epam.training.food.domain.*;
import com.epam.training.food.repository.CustomerRepository;
import com.epam.training.food.repository.FoodRepository;
import com.epam.training.food.service.AuthenticationException;
import com.epam.training.food.service.DefaultFoodDeliveryService;
import com.epam.training.food.service.LowBalanceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@EnableAutoConfiguration
@SpringBootTest(classes = SpringApplicationStarter.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FoodDeliveryIntegrationTest {

    // order table is empty
    private static final long NEXT_ORDER_ID = 1L;
    private static final long SMITH_CUSTOMER_ID = 1L;
    private static final long JOHN_CUSTOMER_ID = 3L;

    @MockitoBean
    private Application application;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private DefaultFoodDeliveryService foodDeliveryService;

    @Test
    @DisplayName("authenticate should return the correct customer when given correct credentials")
    public void testAuthenticateShouldReturnTheCorrectCustomerWhenGivenCorrectCredentials() {
        // GIVEN
        Credentials quickLoginCredentials = createQuickLoginCredentials();
        long expectedId = 1;

        // WHEN
        Customer customer = foodDeliveryService.authenticate(quickLoginCredentials);

        // THEN
        assertEquals(expectedId, customer.getId(), "Wrong customer customer returned, IDs do not match.");
    }

    @Test
    @DisplayName("authenticate should throw AuthenticationException when called with incorrect credentials")
    public void testAuthenticateShouldThrowAuthenticationExceptionWhenGivenIncorrectCredentials() {
        // GIVEN
        Credentials credentials = new Credentials();
        credentials.setUserName("a");
        credentials.setPassword("incorrect");

        // WHEN

        // THEN
        assertThrows(AuthenticationException.class, () -> foodDeliveryService.authenticate(credentials),
                "authenticate did not throw AuthenticationException when called with"
                        + " email: 'a' and password: 'incorrect'.");
    }

    @Test
    @DisplayName("createOrder should create order with correct values and save it")
    public void testCreateOrderShouldCreateOrderWithCorrectValuesAndSaveIt() {
        // GIVEN
        Customer customer = getCustomerWithCart(SMITH_CUSTOMER_ID, getOneItemCartWithOneFideua());

        // WHEN
        Order actual = foodDeliveryService.createOrder(customer);

        // THEN
        assertAll("Verify Order attributes",
            () -> assertEquals(1, actual.getOrderId(), "Generated orderId should be 1."),
            () -> assertNotNull(actual.getCustomer(), "customer relation must be set."),
            () -> assertEquals(1, actual.getCustomer().getId(), "customerId must be 1."),
            () -> assertNotNull(actual.getOrderItems(), "orderItems collection should not be null."),
            () -> assertEquals(1, actual.getOrderItems().size(), "Number of elements in OrderItems collection must be 1."),
            () -> assertNotNull(actual.getTimestampCreated(), "timestampCreated field must be set."),
            () -> assertEquals(new BigDecimal("15.00"), actual.getPrice(), "price field must be 15.00")
        );

        OrderItem orderItem = actual.getOrderItems().get(0);
        assertAll("Verify OrderItem attributes",
            () -> assertNotNull(orderItem.getFood(), "food relation must be set."),
            () -> assertEquals("Fideua", orderItem.getFood().getName(), "Referenced food name must be 'Fideua'."),
            () -> assertEquals(1, orderItem.getPieces(), "pieces must be 1"),
            () -> assertEquals(new BigDecimal("15.00"), orderItem.getPrice(), "price must be 15.00")
        );
    }

    @Test
    @DisplayName("createOrder should empty the cart if the order creation was successful")
    public void testCreateOrderShouldEmptyTheCartIfOrderCreationWasSuccessful() {
        // GIVEN
        Customer customer = getCustomerWithCart(SMITH_CUSTOMER_ID, getOneItemCartWithOneFideua());

        // WHEN
        Order order = foodDeliveryService.createOrder(customer);

        // THEN
        assertEquals(0, order.getCustomer().getCart().getOrderItems().size(),
                "The shopping cart still contains items.");
    }

    @Test
    @DisplayName("createOrder should deduct from the customer's balance if the order creation was successful")
    public void testCreateOrderShouldDeductFromTheCustomersBalanceIfOrderCreationWasSuccessful() {
        // GIVEN
        Customer customer = getCustomerWithCart(SMITH_CUSTOMER_ID, getOneItemCartWithOneFideua());

        // WHEN
        Order order = foodDeliveryService.createOrder(customer);

        // THEN
        assertEquals(new BigDecimal("85.00"), order.getCustomer().getBalance(),
                "Wrong balance after order creation. Balance before: 100, price of order: 15.");
    }

    @Test
    @DisplayName("createOrder should throw LowBalanceException when the customer's balance is too low")
    public void testCreateOrderShouldThrowLowBalanceExceptionWhenTheCustomersBalanceIsTooLow() {
        // GIVEN
        Customer customer = getCustomerWithCart(JOHN_CUSTOMER_ID, getOneItemCartWithOneFideua());

        // WHEN

        // THEN
        Assertions.assertThrows(LowBalanceException.class, () -> foodDeliveryService.createOrder(customer),
                "createOrder did not throw LowBalanceException!"
                        + " Customer's balance: " + customer.getBalance()
                        + ", total price of cart: " + customer.getCart().getPrice() + ".");
    }

    @Test
    @DisplayName("createOrder should throw IllegalStateException when the cart is empty")
    public void testCreateOrderShouldThrowIllegalStateExceptionWhenTheCartIsEmpty() {
        // GIVEN
        Customer customer = getCustomerWithCart(SMITH_CUSTOMER_ID, getEmptyCart());

        // WHEN

        // THEN
        Assertions.assertThrows(IllegalStateException.class, () -> foodDeliveryService.createOrder(customer),
                "createOrder did not throw IllegalStateException when called with"
                        + " cart: " + customer.getCart());
    }

    @Test
    @DisplayName("list all available foods")
    void testListAllFoodsShouldListAllFoods() throws Exception {

        // GIVEN
        List<Food> expected = getAllFood();

        // WHEN
        List<Food> actual = foodDeliveryService.listAllFood();

        // THEN
        Assertions.assertEquals(expected, actual, "The implementation of listAllFood() is incorrect!");
    }

    @Test
    @DisplayName("updateCart should add a new item when the Food is not in the cart and pieces is positive")
    public void testUpdateCartShouldAddANewItemWhenFoodIsNotInCartAndPiecesIsPositive() {
        // GIVEN
        Customer customer = getCustomerWithCart(SMITH_CUSTOMER_ID, getEmptyCart());
        Cart expectedCart = getOneItemCartWithOneFideua();

        // WHEN
        foodDeliveryService.updateCart(customer, getFideua(), 1);

        // THEN
        Cart actualCart = customer.getCart();
        assertAll("The cart has one or more incorrect values after updateCart was called with"
                        + " a customer with cart: " + customer.getCart() + " and food: Fideua!",
                () -> assertEquals(expectedCart.getOrderItems(), actualCart.getOrderItems(),
                        "The content of the cart if incorrect!"),
                () -> assertEquals(expectedCart.getPrice(), actualCart.getPrice(),
                        "The price of the cart is incorrect!")
        );
    }

    @Test
    @DisplayName("updateCart should update the item when the Food is already in the cart"
            + " and pieces is positive")
    public void testUpdateCartShouldUpdateTheItemWhenTheFoodIsAlreadyInTheCartAndPiecesIsPositive() {
        // GIVEN
        Customer customer = getCustomerWithCart(SMITH_CUSTOMER_ID, getOneItemCartWithTwoFideua());
        Cart expectedCart = getOneItemCartWithOneFideua();

        // WHEN
        foodDeliveryService.updateCart(customer, getFideua(), 1);

        // THEN
        Cart actualCart = customer.getCart();
        assertAll("The cart has one or more incorrect values after updateCart was called with"
                        + " a customer with cart: " + customer.getCart() + " and food: Fideua!",
                () -> assertEquals(expectedCart.getOrderItems(), actualCart.getOrderItems(),
                        "The content of the cart if incorrect!"),
                () -> assertEquals(expectedCart.getPrice(), actualCart.getPrice(),
                        "The price of the cart is incorrect!")
        );
    }

    @Test
    @DisplayName("updateCart should remove the item when the Food is already in the cart and pieces is zero")
    public void testUpdateCartShouldRemoveTheItemWhenTheFoodIsAlreadyInTheCartAndPiecesIsZero() {
        // GIVEN
        Customer customer = getCustomerWithCart(SMITH_CUSTOMER_ID, getOneItemCartWithTwoFideua());
        Cart expectedCart = getEmptyCart();

        // WHEN
        foodDeliveryService.updateCart(customer, getFideua(), 0);

        // THEN
        Cart actualCart = customer.getCart();
        assertAll("The cart has one or more incorrect values after updateCart was called with"
                        + " a customer with cart: " + customer.getCart() + " and food: Fideua!",
                () -> assertEquals(expectedCart.getOrderItems(), actualCart.getOrderItems(),
                        "The content of the cart if incorrect!"),
                () -> assertEquals(expectedCart.getPrice(), actualCart.getPrice(),
                        "The price of the cart is incorrect!")
        );
    }

    @Test
    @DisplayName("updateCart should throw IllegalArgumentException when the Food is not in the cart and pieces is zero")
    public void updateCartShouldThrowIllegalArgumentExceptionWhenFoodIsNotInTheCartAndPiecesIsZero() {
        // GIVEN
        Customer customer = getCustomerWithCart(SMITH_CUSTOMER_ID, getEmptyCart());

        // WHEN

        // THEN
        assertThrows(IllegalArgumentException.class, () -> foodDeliveryService.updateCart(customer, getFideua(), 0),
                "updateCart did not throw IllegalArgumentException when"
                        + " the customer's cart was: " + customer.getCart()
                        + " and the food was: Fideua!");
    }

    @Test
    @DisplayName("updateCart should throw IllegalArgumentException when pieces is negative")
    public void updateCartShouldThrowIllegalArgumentExceptionWhenPiecesIsNegative() {
        // GIVEN
        Customer customer = getCustomerWithCart(SMITH_CUSTOMER_ID, getEmptyCart());

        // WHEN

        // THEN
        assertThrows(IllegalArgumentException.class, () -> foodDeliveryService.updateCart(customer, getFideua(), -1),
                "updateCart did not throw IllegalArgumentException when called with -1 pieces!");
    }

    private Credentials createQuickLoginCredentials() {
        Credentials quickLoginCredentials = new Credentials();
        quickLoginCredentials.setUserName("Smith");
        quickLoginCredentials.setPassword("SmithSecret");

        return quickLoginCredentials;
    }

    private Customer getCustomerWithCart(Long customerId, Cart cart) {
        Customer customer = customerRepository.findById(customerId).get();
        customer.setCart(cart);
        return customer;
    }

    private Cart getEmptyCart() {
        var cart = new Cart();
        cart.setOrderItems(new ArrayList<>());
        cart.setPrice(new BigDecimal("0.00"));
        return cart;
    }

    private Cart getCart(List<OrderItem> orderItemList, BigDecimal price) {
        Cart cartWithOneFood = new Cart();
        cartWithOneFood.setOrderItems(orderItemList);
        cartWithOneFood.setPrice(price);

        return cartWithOneFood;
    }

    private Food getFideua() {
        return foodRepository.findById(1L).get();
    }

    private OrderItem getOrderItemWithFideua(int pieces, BigDecimal price) {
        OrderItem testOrderItem = new OrderItem();
        testOrderItem.setFood(getFideua());
        testOrderItem.setPieces(pieces);
        testOrderItem.setPrice(price);

        return testOrderItem;
    }

    private Cart getOneItemCartWithOneFideua() {
        BigDecimal price = new BigDecimal("15.00");
        OrderItem orderItemWithOnePiece = getOrderItemWithFideua(1, price);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemWithOnePiece);

        return getCart(orderItems, price);
    }

    private Cart getOneItemCartWithTwoFideua() {
        BigDecimal price = new BigDecimal("30.00");
        OrderItem orderItemWithTwoPieces = getOrderItemWithFideua(2, price);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItemWithTwoPieces);

        return getCart(orderItems, price);
    }

    private List<Food> getAllFood() {
        return List.of(
            new Food(1L, "Fideua", new BigDecimal("558.00"), "Noodles gone wild in a seafood fiesta", new BigDecimal("15.00")),
            new Food(2L, "Paella", new BigDecimal("379.00"), "Rice party with a saffron twist", new BigDecimal("13.00")),
            new Food(3L, "Tortilla", new BigDecimal("278.00"), "A scrumptious flat floury flavor", new BigDecimal("10.00")),
            new Food(4L, "Gazpacho", new BigDecimal("162.00"), "Soup's cold revenge for scorching summers", new BigDecimal("8.00")),
            new Food(5L, "Quesadilla", new BigDecimal("470.00"), "Cheesy tortilla hug with flavorful fillings", new BigDecimal("13.00"))
        );
    }

}
