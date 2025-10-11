package com.epam.rd.autotasks.figures;

class Triangle extends Figure {

    private final Point a;
    private final Point b;
    private final Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
        points = new Point[]{this.a, this.b, this.c};
    }

    @Override
    public double area() {
        return ( Math.abs(
                (a.getX() * (b.getY() - c.getY())) +
                (b.getX() * (c.getY() - a.getY())) +
                (c.getX() * (a.getY() - b.getY()))
            ) * 0.5 );
    }

    @Override
    public String pointsToString() {
        StringBuilder sb = new StringBuilder();
        for (Point p : points)
            sb.append(p.toString());
        return (sb.toString());
    }

    @Override
    public Point leftmostPoint() {
        double minX = a.getX();
        int idx = 0;
        for (int i = 1; i < points.length; i++) {
            if (points[i].getX() < minX) {
                minX = points[i].getX();
                idx = i;
            }
        }
        return (points[idx]);
    }

}
