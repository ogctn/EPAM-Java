package com.epam.rd.autocode.assestment.linearcalculations;

public class LinearCalculation {

    /**
     * The speed of the first car is V1 km/h, the speed of the second car is V2 km/h,
     * the distance between them is S km. Find the distance between cars after T hours,
     * if the cars are distancing from each other. The given distance is equal
     * the sum of the initial distance + the general way, which was covered by cars;
     * the general way = time*total speed.
     *
     * Example:
     * <pre>
     * V1 = 90 km/h; V2 = 110 km/h; S = 65 km; T = 3 h  =>  Distance = 665 km
     * V1 = 65.5 km/h; V2 = 90.4 km/h; S = 20.9 km; T = 1.5 h  =>  Distance = 254.75 km
     * V1 = 70 km/h; V2 = 85.6 km/h; S = 32.6 km; T = 2 h  =>  Distance = 343.8 km
     * </pre>
     *
     * @return the distance
     */
    public static double findCarsDistance(double car1Speed, double car2Speed, double initialDistance, double time) {
        return ((car1Speed + car2Speed) * time + initialDistance);
    }

    public static void main(String[] args) {
        System.out.println(findCarsDistance(90., 110., 65., 3.));
    }
}
