package com.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.Point;

public class AlienPatrol extends Unit{

    boolean hunting;
    Point[] patrolRoute;

    public AlienPatrol(boolean hunting, Point[] patrolRoute, int movementSpeed, int health, int range, Point position, Texture img){
        super(movementSpeed, health,  range, position, img);
        this.hunting = hunting;
        this.patrolRoute = patrolRoute;
    }

    /**
     *
     * @param fireStation the fire station that the patrol is to attack
     */
    public void attackFireStation(FireStation fireStation){

    }

    /**
     *
     * @param fireStation the fire station that the patrol is to hunt
     */
    public void huntFireStation(FireStation fireStation){

    }

}
