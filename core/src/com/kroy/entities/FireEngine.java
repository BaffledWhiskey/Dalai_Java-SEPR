package com.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kroy.game.Point;

/**
 *
 * fire engine class extends the unit class
 */
public class FireEngine extends Unit{
    int volumeOfWater;
    public boolean isActive;
    private static int counter = 0; // Acts as counter for how many FireEngine class instances exist
    public int instanceNum; // What number fire engine is this. Mainly for Debugging


    public FireEngine(int volumeOfWater, int movementSpeed, int health, int range, Point position, Texture img){
        super(movementSpeed, health, range, position,img);
        this.volumeOfWater = volumeOfWater;
        counter++;
        instanceNum = this.getNumberOfInstances();
    }

    public int getNumberOfInstances(){
        return counter;
    }

    public String toString(){
        return String.valueOf(instanceNum);
    }
    public void setState(boolean bool){
        isActive = bool;
    }


    /**
     *
     * @param fortress the fortress the fire engine is to attack
     */
    public void attackFortress(Fortress fortress){
        //currently the DPS will be inversely proportional to movement speed
        fortress.setHealth((fortress.getHealth() - (int)500/movementSpeed));
    }


    /**
     *
     * @param patrol the AlienPatrol the fire engine is to attack
     */
    public void attackAlienPatrols(AlienPatrol patrol){

    }

    /**
     *
     * @param animation death animation (use explosion)
     * @param elapseTime (time elapsed between frames)
     */
    public void destroy(Animation animation, float elapseTime){
        if(!(this.health <= 0)){
            try {
                throw new Exception("can't destroy Entity with health greater than 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            SpriteBatch sb = new SpriteBatch();
            sb.begin();
                sb.draw((TextureRegion) animation.getKeyFrame(elapseTime, true), this.position.x, this.position.y, 0, 0, 80, 80, 1, 1, 9, true);
            sb.end();
            sb.dispose();
        }
    }

}
