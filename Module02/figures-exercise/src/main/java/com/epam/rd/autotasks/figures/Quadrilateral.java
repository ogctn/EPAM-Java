package com.epam.rd.autotasks.figures;

class Quadrilateral extends Figure {

    private final Point a;
    private final Point b;
    private final Point c;
    private final Point d;

    public Quadrilateral(Point a, Point b, Point c, Point d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        points = new Point[]{this.a, this.b, this.c, this.d};
    }

    @Override
    public double area() {
        return (0.5 * Math.abs(
                (a.getX() * b.getY()) + (b.getX() * c.getY()) +
                (c.getX() * d.getY()) + (d.getX() * a.getY()) -
                ((a.getY() * b.getX()) + (b.getY() * c.getX()) +
                 (c.getY() * d.getX()) + (d.getY() * a.getX()))
        ));
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
