package com.kroy.game;

/**
 * The Point class stores a pair of co-ordinates representing a point on the map
 */
public class Point {
    public int x,y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Determines the distance from one point to another
     * @param newPosition The position to determine the distance to
     * @return The distance between the two points
     */
    public double distance(Point newPosition){
            double a = Math.abs(newPosition.x-this.x);
            double b = Math.abs(newPosition.y-this.y);
            return Math.hypot(a,b);
    }
}
