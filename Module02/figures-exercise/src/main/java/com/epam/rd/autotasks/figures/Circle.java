package com.epam.rd.autotasks.figures;

import java.util.Arrays;

class Circle extends Figure {

    private final Point o;
    private final double r;

    public Circle(Point origin, double r) {
        this.o = origin;
        this.r = r;
        points = new Point[]{this.o};
    }

    @Override
    public double area() { return (Math.PI * this.r * this.r); }

    @Override
    public String pointsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.o.toString());
        return (sb.toString());
    }

    @Override
    public String toString() {
        return (this.getClass().getSimpleName() + "[" + pointsToString() + r + "]");
    }

    @Override
    public Point leftmostPoint() {
        return (new Point(this.o.getX() - r, this.o.getY()));
    }
}
