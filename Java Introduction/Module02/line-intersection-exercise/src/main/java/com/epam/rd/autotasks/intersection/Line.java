package com.epam.rd.autotasks.intersection;

public class Line {
    final int k;
    final int b;

    public Line(int k, int b) {
        this.k = k;
        this.b = b;
    }

    public Point intersection(Line other) {
        int x;
        int y;

        if (this.k == other.k)
            return (null);

        x = ((other.b - this.b) / (this.k - other.k));
        y = (this.k * x) + this.b;
        Point point = new Point(x, y);
        return (point);
        }

}
