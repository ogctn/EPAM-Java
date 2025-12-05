package com.epam.autotasks.collections;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

class RangedOpsIntegerSet extends AbstractSet<Integer> {

    private final Set<Integer> set = new TreeSet<>();

    public boolean add(int fromInclusive, int toExclusive) {
        boolean isAnyAdded = false;

        for (int i = fromInclusive; i < toExclusive; i++) {
            if (set.add(i))
                isAnyAdded = true;
        }
        return (isAnyAdded);
    }

    public boolean remove(int fromInclusive, int toExclusive) {
        boolean isAnyRemoved = false;

        for (int i = fromInclusive; i < toExclusive; i++) {
            if (set.remove(i))
                isAnyRemoved = true;
        }
        return (isAnyRemoved);
    }

    @Override
    public boolean add(final Integer integer) {
        return (set.add(integer));
    }

    @Override
    public boolean remove(final Object o) {
        return (set.remove(o));
    }

    @Override
    public Iterator<Integer> iterator() {
        return (set.iterator());
    }

    @Override
    public int size() {
        return (set.size());
    }
}
