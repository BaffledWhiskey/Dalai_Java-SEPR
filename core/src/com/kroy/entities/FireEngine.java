package com.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.Point;

/**
 *
 * fire engine class extends the unit class
 */
public class FireEngine extends Unit{
    int volumeOfWater;
    public boolean isActive;


    public FireEngine(int volumeOfWater, int movementSpeed, int health, int range, Point position, Texture img){
        super(movementSpeed, health, range, position,img);
        this.volumeOfWater = volumeOfWater;
    }

    public void setState(boolean state){
        isActive = state;
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
