package com.epam.rd.autotasks;

public class CarouselRun {

    private int[] elements;
    private int index = 0;

    public CarouselRun(int[] elements) {
        this.elements = elements;
    }

    public int next() {
        if (isFinished() == true)
            return (-1);

        for (int it = 0; it < elements.length; it++) {
            if (elements[index] > 0) {
                int oldVal = elements[index];
                --elements[index];
                index = (index + 1) % elements.length;
                return (oldVal);
            }
            index = (index + 1) % elements.length;
        }
        return (-1);
    }

    public boolean isFinished() {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] > 0)
                return (false);
        }
        return (true);
    }
}

