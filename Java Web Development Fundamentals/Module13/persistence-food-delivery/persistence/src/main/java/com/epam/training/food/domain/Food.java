package com.epam.training.food.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private BigDecimal price;
    private BigDecimal calorie;
    private String description;

    public Food() {}

    public Food(Long id, String name, BigDecimal calorie, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.calorie = calorie;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;}
    public String getName() { return name;    }
    public void setName(String name) { this.name = name;    }
    public BigDecimal getPrice() { return price;    }
    public void setPrice(BigDecimal price) { this.price = price;    }
    public BigDecimal getCalories() { return calorie; }
    public void setCalories(BigDecimal calories) { this.calorie = calories;    }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(id, food.id) &&
                Objects.equals(name, food.name);
    }

    @Override
    public int hashCode() { return Objects.hash(id, name); }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}