package com.epam.rd.autotasks;

import java.util.Optional;

public enum Direction {
    N(0),
    NE(45),
    E(90),
    SE(135),
    S(180),
    SW(225),
    W(270),
    NW(315);

    private int degrees;

    Direction(final int degrees) {
        this.degrees = degrees;
    }

    public static Direction ofDegrees(int degrees) {
        degrees = ((degrees % 360) + 360) % 360;

        for (Direction dir : Direction.values()) {
            if (dir.degrees == degrees)
                return (dir);
        }
        return (null);
    }

    public static Direction closestToDegrees(int degrees) {
        degrees = ((degrees % 360) + 360) % 360;

        int minDiff = 360, diff = 0;
        Direction closest = null;

        for (Direction dir : Direction.values()) {
            diff = Math.abs(dir.degrees - degrees);

            if (diff > 180)
                diff = 360 - diff;
            if (diff < minDiff) {
                minDiff = diff;
                closest = dir;
            }
        }
        return (closest);
    }

    public Direction opposite() {
        for (Direction dir : Direction.values()) {
            if (degrees == (dir.degrees + 180) % 360)
                return (dir);
        }
        return (null);
    }

    public int differenceDegreesTo(Direction direction) {
        int diff = Math.abs(degrees - direction.degrees);
        return (diff > 180 ? 360 - diff : diff);
    }
}
