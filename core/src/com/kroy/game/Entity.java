package com.kroy.game;

import static java.lang.Math.abs;

/**
 *
 */
public class Entity {
    int health;
    int range;
    Point position;

    /**
     * @param health The current health of the Entity
     * @param range The maximum range that the Entity can attack from
     * @param position The current position of the Entity
     */
    public Entity(int health, int range, Point position){
        this.health = health;
        this.range = range;
        this.position = position;
    }

    /**
     * Returns whether or not an entity is in range to be attacked based on a square of width 2*range centered on the
     * current Entity
     * @param target The entity that to determine if its in range
     * @return true The enemy is range to be attacked
     *         false the enemy is not in range to be attacked
     */
    public boolean inRange(Entity target){
        if(abs(target.position.x - this.position.x) < this.range ||
                abs(target.position.y - this.position.y) < this.range){
            return true;
        }
        return false;
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health){
        this.health = health;
    }

}
