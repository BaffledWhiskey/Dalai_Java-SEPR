package com.kroy.game;

public class Point {
    public int x,y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point newPosition){
        double a = Math.abs(newPosition.x-this.x);
        double b = Math.abs(newPosition.y-this.y);
        return Math.hypot(a,b);
    }
}
