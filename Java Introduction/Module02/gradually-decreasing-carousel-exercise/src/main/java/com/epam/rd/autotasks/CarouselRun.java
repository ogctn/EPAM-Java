package com.epam.rd.autotasks;

public class CarouselRun {
    protected int[] elements;
    protected int index = 0;

    int step = 1;

    public CarouselRun(int[] elements) {
        this.elements = elements;
    }

    public int operator(int val) {
        return (--val);
    }

    public int next() {
        if (isFinished() == true)
            return (-1);

        for (int it = 0; it < elements.length; it++) {
            if (elements[index] > 0) {
                int oldVal = elements[index];
                elements[index] = this.operator(elements[index]);
                index = (index + 1) % elements.length;

                if (index == 0)
                    step++;

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
