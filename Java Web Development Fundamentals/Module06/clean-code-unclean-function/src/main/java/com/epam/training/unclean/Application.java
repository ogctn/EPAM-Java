package com.epam.training.unclean;

import java.util.*;

public class Application {

    public static List<Integer> filter(List<? extends Integer> inputValues) {
        if (inputValues == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        Map<Integer, Integer> appearedMap = new HashMap<>();

        for (Integer value : inputValues) {
            appearedMap.put(value, appearedMap.getOrDefault(value, 0) + 1);
        }

        List<Integer> result = new ArrayList<>();
        for (Integer value : inputValues) {
            if (appearedMap.get(value) == 1)
                result.add(value);
        }

        return (result);
    }
}
