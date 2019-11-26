package com.kroy.entities;

import com.kroy.game.Point;

/**
 *
 * fire engine class extends the unit class
 */
public class FireEngine extends Unit{
    int volumeOfWater;

    public FireEngine(int volumeOfWater,int movementSpeed, int health, int range, Point position){
        super(movementSpeed, health, range, position);
        this.volumeOfWater = volumeOfWater;
    }

    /**
     *
     * @param fortress the fortress the fire engine is to attack
     */
    public void attackFortress(Fortress fortress){

    }


    /**
     *
     * @param patrol the AlienPatrol the fire engine is to attack
     */
    public void attackAlienPatrols(AlienPatrol patrol){

    }

}
