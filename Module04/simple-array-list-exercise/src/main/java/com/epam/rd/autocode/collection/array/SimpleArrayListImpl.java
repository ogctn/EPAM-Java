package com.epam.rd.autocode.collection.array;

import java.util.*;

public class SimpleArrayListImpl implements SimpleArrayList {

    private static final int DEFAULT_CAPACITY = 10;
    private static final int FACTOR_MULTIPLIER = 2;
    private static final double INCREASE_LOAD_FACTOR = 0.75;
    private static final double DECREASE_LOAD_FACTOR = 0.4;

    private Object[] elements;
    private int size;
    private int capacity;


    /**
     * Creates a list with the default capacity = 10.
     */
    public SimpleArrayListImpl() {
        this.elements = new Object[DEFAULT_CAPACITY];
        size = 0;
        capacity = DEFAULT_CAPACITY;
    }

    private void expandArr() {
        this.capacity = (int) (capacity() * FACTOR_MULTIPLIER * INCREASE_LOAD_FACTOR);
        if (capacity < size)
            capacity = size + 1;
        Object[] newArr = new Object[capacity()];
        System.arraycopy(getElements(), 0, newArr, 0, size());
        this.elements = newArr;
    }

    private void shrinkArr() {
        this.capacity = size() * 2;
        if (isEmpty())
            capacity = 1;
        Object[] newArr = new Object[capacity()];
        System.arraycopy(getElements(), 0, newArr, 0, size());
        this.elements = newArr;
    }

    @Override
    public boolean add(Object element) {
        if (element == null)
            throw (new NullPointerException());

        if (size() >= (int)(capacity() * INCREASE_LOAD_FACTOR))
            expandArr();
        elements[size()] = element;
        this.size++;
        return (true);
    }

    @Override
    public int capacity() { return (this.capacity); }

    @Override
    public boolean decreaseCapacity() {
        if (size() != 0 && size() <= (DECREASE_LOAD_FACTOR * capacity())) {
            shrinkArr();
            return (true);
        }
        return (false);
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index > size() - 1)
            throw (new IndexOutOfBoundsException());
        return (elements[index]);
    }

    private Object[] getElements() { return (this.elements); };

    private boolean isEmpty() { return (size() == 0); }

    @Override
    public Optional<Object> remove(Object el) {
        if (el == null)
            throw (new NullPointerException());
        Object obj = null;
        int idx = -1;
        for (int i = 0; i < size(); i++) {
            if (el.equals(get((i)))) {
                idx = i;
                break;
            }
        }
        if (idx != -1) {
            Object removed = get(idx);
            System.arraycopy(elements, idx + 1, elements, idx, size - 1 - idx);
            elements[capacity - 1] = null;
            size--;
            return (Optional.of(removed));
        }
        else
            return (Optional.empty());
    }

    @Override
    public int size() { return (size); }

    @Override
    public String toString() {
        if (size() == 0)
            return ("[]");

        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size(); i++) {
            sb.append(get(i));
            if (i < size() - 1)
                sb.append(", ");
        }
        sb.append("]");
        return (sb.toString());
    }

}
