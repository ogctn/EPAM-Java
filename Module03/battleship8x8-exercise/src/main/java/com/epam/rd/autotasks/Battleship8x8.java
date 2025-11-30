package com.epam.rd.autotasks;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {
        this.ships = ships;
    }

    public boolean shoot(String shot) {
        int row = (shot.charAt(1) - '0') - 1;
        int column = shot.charAt(0) - 'A';
        int shiftVal = 63 - (row * 8 + column);
        long positionMask = 1L << shiftVal;
        shots |= positionMask;
        return ((ships & positionMask) != 0);
    }

    public String state() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {

                int shiftVal = 63 - (row * 8 + column);
                long mask = 1L << shiftVal;

                boolean hasShip = (ships & mask) != 0;
                boolean hasShot = (shots & mask) != 0;
                if (hasShip) {
                    if (hasShot)
                        sb.append("☒");
                    else
                        sb.append("☐");
                } else {
                    if (hasShot)
                        sb.append("×");
                    else
                        sb.append(".");
                }
            }
            if (row < 7)
                sb.append("\n");
        }
        return (sb.toString());
    }

}
