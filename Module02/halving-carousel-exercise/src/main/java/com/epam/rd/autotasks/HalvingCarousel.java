package com.epam.rd.autotasks;

public class HalvingCarousel extends DecrementingCarousel {

    public HalvingCarousel(final int capacity) {
        super(capacity);
    }

    @Override
    public CarouselRun run() {
        if (isRunning || carousel != null)
            return null;
        isRunning = true;

        int[] tmp = new int[size];
        System.arraycopy(elements, 0, tmp, 0, size);

        carousel = new CarouselRun(tmp) {
            @Override
            public int operator(int val) {
                return (val / 2);
            }
        };
        return carousel;
    }
}
