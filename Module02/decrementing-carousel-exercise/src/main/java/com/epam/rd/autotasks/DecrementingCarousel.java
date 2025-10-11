package com.epam.rd.autotasks;

import com.epam.rd.autotasks.CarouselRun;

public class DecrementingCarousel {
    private int capacity;
    private int size = 0;
    private final int[] elements;
    private boolean isRunning = false;
    private CarouselRun carousel = null;

    public DecrementingCarousel(int capacity) {
        if (capacity <= 0)
            throw new UnsupportedOperationException("");
        else {
            this.capacity = capacity;
            this.elements = new int[capacity];
        }
    }

    public boolean addElement(int element){
        if (isRunning || element <= 0 || size >= capacity)
            return (false);
        elements[size++] = element;
        return (true);
    }

    public CarouselRun run(){
        if (isRunning == true || this.carousel != null)
            return (null);

        this.isRunning = true;

        int [] tmp = new int[this.size];
        System.arraycopy(elements, 0, tmp, 0, size);

        this.carousel = new CarouselRun(tmp);
        return (this.carousel);
    }
}
