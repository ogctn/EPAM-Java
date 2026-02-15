package com.epam.training.products;

import com.epam.training.products.domain.Product;

import java.util.List;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;;

public class ProductQueries {

    private final List<Product> products;

    public ProductQueries(List<Product> products) {
        if (products == null)
            this.products = List.of();
        else
            this.products = List.copyOf(products);
    }

    private static Stream<String> nextSubCategories(String[] categories, String categoryName) {
        return IntStream.range(0, categories.length - 1)
                .filter(i -> categoryName.equals(categories[i]))
                .mapToObj(i -> categories[i + 1]);
    }

    public List<String> getSubCategoriesOf(String categoryName) {
        if (categoryName == null || categoryName.isBlank())
            return List.of();

        return products.stream()
                .map(Product::getCategories)
                .filter(Objects::nonNull)
                .flatMap(categories -> nextSubCategories(categories, categoryName))
                .distinct()
                .toList();
    }

    private static boolean hasSubCategoryUnder(Product product, String categoryName) {
        String[] categories = product.getCategories();
        if (categories == null || categories.length < 2)
            return false;
        return IntStream.range(0, categories.length - 1)
                .anyMatch(i -> Objects.equals(categories[i], categoryName));
    }

    public List<Product> getSweetsWherePriceIsLowerThan(double price) {
        return products.stream()
                .filter(Product::hasCategory)
                .filter(Product::hasSubCategory)
                .filter(product -> hasSubCategoryUnder(product, "sweets"))
                .filter(product -> product.getPrice() < price)
                .collect(Collectors.toList());
    }

    public Product getTheMostExpensiveProduct() {
        return products.stream()
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);
    }
}
