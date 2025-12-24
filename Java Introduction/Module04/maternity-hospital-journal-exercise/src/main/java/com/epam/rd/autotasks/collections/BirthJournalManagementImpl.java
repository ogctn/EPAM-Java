package com.epam.rd.autotasks.collections;

import java.util.*;

public class BirthJournalManagementImpl implements BirthJournalManagement {

    private final List<Baby> babies = new ArrayList<>();
    private boolean isCommit = false;

    @Override
    public boolean addEntryOfBaby(WeekDay day, Baby baby) {
        if (isCommit)
            return (false);
        return (babies.add(baby));
    }

    @Override
    public void commit() {
        if (!isCommit) {
            List<Baby> unmodifiable = Collections.unmodifiableList(babies);
            babies.clear();
            babies.addAll(unmodifiable);
            isCommit = true;
        }
    }

    @Override
    public int amountBabies() {
        return (babies.size());
    }

    @Override
    public List<Baby> findBabyWithHighestWeight(String gender) {
        List<Baby> result = new ArrayList<>();
        double maximum = Double.MIN_VALUE;
        for (Baby b : babies) {
            if (gender.toLowerCase().compareTo(b.getGender().toLowerCase()) == 0) {
                double thisWeight = b.getWeight();
                if (thisWeight > maximum) {
                    maximum = thisWeight;
                    result.clear();
                    result.add(b);
                } else if (thisWeight == maximum)
                    result.add(b);
            }
        }
        result.sort(new Comparator<Baby>() {
            @Override
            public int compare(Baby o1, Baby o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });
        return (result);
    }

    @Override
    public List<Baby> findBabyWithSmallestHeight(String gender) {
        List<Baby> result = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (Baby b : babies) {
            if (gender.toLowerCase().compareTo(b.getGender().toLowerCase()) == 0) {
                int thisHeight = b.getHeight();
                if (thisHeight < min) {
                    min = thisHeight;
                    result.clear();
                    result.add(b);
                } else if (thisHeight == min)
                    result.add(b);
            }
        }
        result.sort(new Comparator<Baby>() {
            @Override
            public int compare(Baby o1, Baby o2) {
                return (Double.compare(o1.getWeight(), o2.getWeight()));
            }
        });
        return (Collections.unmodifiableList(result));
    }

    private int toMinute(String time) {
        String[] parts = time.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        return (60 * h + m);
    }

    @Override
    public Set<Baby> findBabiesByBirthTime(String from, String to) {
        int fromMin = toMinute(from);
        int toMin = toMinute(to);

        Set<Baby> result = new TreeSet<>(new Comparator<Baby>() {
            @Override
            public int compare(Baby o1, Baby o2) {
                int t1 = toMinute(o1.getTime());
                int t2 = toMinute(o2.getTime());
                if (t1 != t2)
                    return (t1 - t2);
                int cmp = o1.getName().compareTo(o2.getName());
                if (cmp != 0)
                    return (cmp);
                cmp = o1.getGender().compareTo(o2.getGender());
                if (cmp != 0)
                    return (cmp);
                cmp = Double.compare(o1.getWeight(), o2.getWeight());
                if (cmp != 0)
                    return (cmp);
                cmp = Integer.compare(o1.getHeight(), o2.getHeight());
                return (cmp);
            }
        });

        for (Baby b : babies) {
            int t = toMinute(b.getTime());
            if (t >= fromMin && t <= toMin)
                result.add(b);
        }
        return (result);
    }
}
