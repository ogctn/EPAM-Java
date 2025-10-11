package com.epam.rd.qa.classes;

public class ArrayRectangles {

    private Rectangle[] rectangleArray;
    private int size, capacity;

    public ArrayRectangles(int size) {
        if (size <= 0)
            throw new IllegalArgumentException();
        this.rectangleArray = new Rectangle[size];
        this.capacity = size;
        this.size = 0;
    }

    public ArrayRectangles(Rectangle... rectangles) {
        if (rectangles == null || rectangles.length == 0)
            throw new IllegalArgumentException();
        this.rectangleArray = rectangles;
        this.capacity = rectangles.length;
        this.size = rectangles.length;
    }

    public boolean addRectangle(Rectangle rectangle) {
        int i = 0;

        if (rectangle == null)
            throw new IllegalArgumentException();
        if (this.size < this.capacity) {
            while (i < this.capacity && this.rectangleArray[i] != null)
                i++;
            this.rectangleArray[i] = rectangle;
            ++this.size;
            return (true);
        }
        return (false);
    }

    public int size() {
        return (this.size);
    }

    public int indexMaxArea() {
        int idx = 0;
        double max = this.rectangleArray[0].area();

        for(int i = 1; i < this.capacity; i++) {
            if (this.rectangleArray[i] == null)
                continue;
            if (this.rectangleArray[i].area() > max) {
                max = this.rectangleArray[i].area();
                idx = i;
            }
        }
        return (idx);
    }

    public int indexMinPerimeter() {
        int idx = 0;
        double min = this.rectangleArray[0].perimeter();

        for(int i = 1; i < this.capacity; i++) {
            if (this.rectangleArray[i] == null)
                continue;
            if (this.rectangleArray[i].perimeter() < min) {
                min = this.rectangleArray[i].perimeter();
                idx = i;
            }
        }
        return (idx);
    }

    public int numberSquares() {
        int count = 0;

        for (int i = 0; i < this.capacity; i++) {
            if (!(this.rectangleArray[i] == null) && this.rectangleArray[i].isSquare())
                count++;
        }
        return (count);
    }

}
