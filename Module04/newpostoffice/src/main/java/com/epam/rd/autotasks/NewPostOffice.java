package com.epam.rd.autotasks;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class NewPostOffice {
    private final Collection<Box> listBox;
    private static final int COST_KILOGRAM = 5;
    private static final int COST_CUBIC_METER = 100;
    private static final double COEFFICIENT = 0.5;

    public NewPostOffice() {
        listBox = new ArrayList<>();
    }

    public Collection<Box> getListBox() {
        return (Collection<Box>) ((ArrayList<Box>) listBox).clone();
    }

    static BigDecimal calculateCostOfBox(double weight, double volume, int value) {
        BigDecimal costWeight = BigDecimal.valueOf(weight)
                .multiply(BigDecimal.valueOf(COST_KILOGRAM), MathContext.DECIMAL64);
        BigDecimal costVolume = BigDecimal.valueOf(volume)
                .multiply(BigDecimal.valueOf(COST_CUBIC_METER), MathContext.DECIMAL64);
        return costVolume.add(costWeight)
                .add(BigDecimal.valueOf(COEFFICIENT * value), MathContext.DECIMAL64);
    }

    // implementation begins
    public boolean addBox(String addresser, String recipient, double weight, double volume, int value) {

        if (addresser == null || addresser.trim().isEmpty() ||
                recipient == null || recipient.trim().isEmpty())
            throw new IllegalArgumentException();

        if (weight < 0.5 || weight > 20)
            throw new IllegalArgumentException();

        if (volume <= 0 || volume > 0.25)
            throw new IllegalArgumentException();

        if (value <= 0)
            throw new IllegalArgumentException();

        final BigDecimal cost = calculateCostOfBox(weight, volume, value);
        Box toAdd = new Box(addresser, recipient,weight,volume);
        toAdd.setCost(cost);
        return (listBox.add(toAdd));
    }

    public Collection<Box> deliveryBoxToRecipient(String recipient) {

        Collection<Box> delivered = new ArrayList<>();
        Iterator<Box> it = listBox.iterator();

        while (it.hasNext()) {
            Box curr = it.next();
            if (curr.getRecipient().equals(recipient)) {
                delivered.add(curr);
                it.remove();
            }
        }
        return (delivered);
    }

    public void declineCostOfBox(double percent) {

        BigDecimal coeff = BigDecimal.valueOf(1- (percent / 100));
        Iterator<Box> it = getListBox().iterator();

        while (it.hasNext()) {
            Box curr = it.next();
            BigDecimal currCost = curr.getCost();
            BigDecimal newCost = currCost.multiply(coeff, MathContext.DECIMAL64);
            curr.setCost(newCost);
        }
    }

}
