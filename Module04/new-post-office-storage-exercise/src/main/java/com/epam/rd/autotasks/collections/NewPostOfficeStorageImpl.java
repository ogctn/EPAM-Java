package com.epam.rd.autotasks.collections;

import org.junit.platform.engine.support.hierarchical.HierarchicalTestEngine;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class NewPostOfficeStorageImpl implements NewPostOfficeStorage {
    private List<Box> parcels;

    /**
     * Creates internal storage for becoming parcels
     */
    public NewPostOfficeStorageImpl() {
        parcels = new LinkedList<>();
    }

    /**
     * Creates own storage and appends all parcels into own storage.
     * It must add either all the parcels or nothing, if an exception occurs.
     *
     * @param boxes a collection of parcels.
     * @throws NullPointerException if the parameter is {@code null}
     *                              or contains {@code null} values.
     */
    public NewPostOfficeStorageImpl(Collection<Box> boxes) {
        if (boxes == null)
            throw (new NullPointerException());

        for (Box box : boxes) {
            if (box == null)
                throw (new NullPointerException());
        }
        parcels = new LinkedList<>(boxes);
    }

    private void checkNullBBox(Box box) {
        if (box == null)
            throw (new NullPointerException());
    }

    private void checkNullBBoxes(Collection<Box> boxes) {
        if (boxes == null)
            throw (new NullPointerException());
        for (Box box : boxes) {
            if (box == null)
                throw (new NullPointerException());
        }
    }

    @Override
    public boolean acceptBox(Box box) {
        checkNullBBox(box);
        return (parcels.add(box));
    }

    @Override
    public boolean acceptAllBoxes(Collection<Box> boxes) {
        checkNullBBoxes(boxes);
        return (parcels.addAll(boxes));
    }

    @Override
    public boolean carryOutBoxes(Collection<Box> boxes) {
        checkNullBBoxes(boxes);

        boolean flag = false;
        Iterator<Box> it = parcels.iterator();
        while (it.hasNext()) {
            Box stored = it.next();
            boolean remove = false;

            for (Box removeBox : boxes) {
                if (stored.equals(removeBox)) {
                    remove = true;
                    break;
                }
            }
            if (remove) {
                it.remove();
                flag = true;
            }
        }
        return (flag);
    }

    @Override
    public List<Box> carryOutBoxes(Predicate<Box> predicate) {
        if (predicate == null)
            throw (new NullPointerException());

        List<Box> removedList = new LinkedList<>();
        Iterator<Box> it = parcels.iterator();
        while (it.hasNext()) {
            Box box = it.next();
            if (predicate.test(box)) {
                removedList.add(box);
                it.remove();
            }
        }
        return (removedList);
    }

    @Override
    public List<Box> getAllWeightLessThan(double weight) {
        if (weight <= 0.0)
            throw (new IllegalArgumentException());

        final double testW = weight;
        Predicate<Box> predicate = new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                return (box.getWeight() < testW);
            }
        };
        return (searchBoxes(predicate));
    }

    @Override
    public List<Box> getAllCostGreaterThan(BigDecimal cost) {
        if (cost == null)
            throw (new NullPointerException());
        if (cost.compareTo(BigDecimal.ZERO) < 0)
            throw (new IllegalArgumentException());

        Predicate<Box> predicate = new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                return (box.getCost().compareTo(cost) > 0);
            }
        };
        return (searchBoxes(predicate));
    }

    @Override
    public List<Box> getAllVolumeGreaterOrEqual(double volume) {
        if (volume < 0.0)
            throw (new IllegalArgumentException());

        Predicate<Box> predicate = new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                return (box.getVolume() >= volume);
            }
        };
        return (searchBoxes(predicate));
    }

    @Override
    public List<Box> searchBoxes(Predicate<Box> predicate) {
        if (predicate == null)
            throw (new NullPointerException());

        List<Box> res = new LinkedList<>();
        for (Box box : parcels) {
            if (predicate.test(box))
                res.add(box);
        }
        return (res);
    }

    @Override
    public void updateOfficeNumber(Predicate<Box> predicate, int newOfficeNumber) {
        if (predicate == null)
            throw (new NullPointerException());

        for (Box box : parcels) {
            if (predicate.test(box))
                box.setOfficeNumber(newOfficeNumber);
        }
    }
}
