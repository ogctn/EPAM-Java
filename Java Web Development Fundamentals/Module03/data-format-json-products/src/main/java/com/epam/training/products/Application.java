package com.epam.training.products;

import com.epam.training.products.domain.Product;

import java.io.File;
import java.util.List;

public class Application {

    private final ProductQueries productQueries;

    public static void main(String[] args) {
        Application application = new Application();
        application.queryProducts();
    }

    private Application() {
        ProductsReader shopDataReader = new ProductsReader(new File("input/products.json"));
        List<Product> products = shopDataReader.read();
        productQueries = new ProductQueries(products);
    }

    private void queryProducts() {
        printSubCategories();
        printSweets();
        printExpensive();
    }

    private void printSubCategories() {
        final String category = "drinks";
        System.out.printf("Sub categories of %s%n", category);
        List<String> drinkSubCategories = productQueries.getSubCategoriesOf(category);
        drinkSubCategories.forEach(subCategory -> System.out.println("- " + subCategory));
    }

    private void printSweets() {
        final double maxPrice = 3.0;
        System.out.printf("Sweets, price is lower than %f%n", maxPrice);
        List<Product> sweets = productQueries.getSweetsWherePriceIsLowerThan(maxPrice);
        sweets.forEach(sweet -> System.out.println("- " + sweet));
    }

    private void printExpensive() {
        Product mostExpensiveProduct = productQueries.getTheMostExpensiveProduct();
        System.out.println("The most Expensive Product: " + mostExpensiveProduct.toString());
    }

}
