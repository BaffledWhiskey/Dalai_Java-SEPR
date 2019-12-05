package com.kroy.entities;

import com.kroy.game.Point;

/**
 *  Unit class extends the entity class and has the children of Alien patrol and FireEngine
 */
public class Unit extends Entity {
    public int movementSpeed;

    public Unit(int movementSpeed, int health, int range, Point position){
        super(health,range,position);
        this.movementSpeed = movementSpeed;
    }

    /**
     *
     * @param destination coords of desired destination for current unit
     * @return true if the path can be taken, false if not
     */
    public boolean pathFind(Point destination){
        return false;
    }

    public void updatePosition(Point newPos){
        this.position = newPos;
    }

}
