package com.epam.rd.autotasks;

public class GraduallyDecreasingCarousel extends DecrementingCarousel {
    public GraduallyDecreasingCarousel(final int capacity) {
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
            private int processed = 0;
            private boolean modified = false;

            @Override
            public int operator(int val) {
                return val - step;
            }

            @Override
            public int next() {
                if (isFinished())
                    return (-1);

                int len = elements.length;
                int attempts = 0;

                while (attempts < len) {
                    if (elements[index] > 0) {
                        int oldVal = elements[index];
                        elements[index] = operator(elements[index]);
                        index = (index + 1) % len;

                        modified = true;
                        processed++;

                        if (processed == len) {
                            if (modified)
                                step++;
                            processed = 0;
                            modified = false;
                        }

                        return (oldVal);
                    } else {
                        index = (index + 1) % len;
                        processed++;
                        attempts++;
                    }

                    if (processed == len) {
                        if (modified)
                            step++;
                        processed = 0;
                        modified = false;
                    }
                }

                return (-1);
            }
        };

        return (carousel);
    }
}