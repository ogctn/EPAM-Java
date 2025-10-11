package com.epam.rd.qa.classes;

import java.util.Objects;

public class Rectangle {

    private double sideA, sideB;

    public Rectangle (double a, double b) {
        if (a <= 0 || b <= 0)
            throw new IllegalArgumentException();
        this.sideA = a;
        this.sideB = b;
    }

    public Rectangle (double a) {
        if (a <= 0)
            throw new IllegalArgumentException();
        this.sideA = a;
        this.sideB = a;
    }

    public Rectangle () {
        this.sideA = 4;
        this.sideB = 3;
    }

    public double getSideA() {
        return (this.sideA);
    }

    public double getSideB() {
        return (this.sideB);
    }

    public double area() {
        return (this.sideA * this.sideB);
    }

    public double perimeter() {
        return (2 * (this.sideA + this.sideB));
    }

    public boolean isSquare() {
        return (this.sideA == this.sideB);
    }

    public void replaceSides() {
        double tmp = this.sideA;
        this.sideA = this.sideB;
        this.sideB = tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return (true);
        if (!(o instanceof Rectangle))
            return (false);
        Rectangle other = (Rectangle) o;
        return ((Double.compare(other.sideA, this.sideA) == 0) &&
                (Double.compare(other.sideB, this.sideB) == 0));
    }

    @Override public int hashCode() {
        return (Objects.hash(this.sideA, this.sideB));
    }

    @Override
    public String toString() {
        return "Rectangle: sideA=" + getSideA() +
                " , sideB=" + getSideB() + " .";
    }

}
