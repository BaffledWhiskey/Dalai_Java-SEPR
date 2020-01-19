package com.kroy.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kroy.game.Point;

/**
 *
 * fire engine class extends the unit class
 */
public class FireEngine extends Unit{
    int volumeOfWater;
    public boolean isActive;
    public boolean isBeingRepaired;
    private static int counter = 0; // Acts as counter for how many FireEngine class instances exist
    public int instanceNum; // What number fire engine is this. Mainly for Debugging
    public int maxVolume;


    public FireEngine(int volumeOfWater, int movementSpeed, int health, int range, Point position, Texture img){
        super(movementSpeed, health, range, position,img);
        this.volumeOfWater = volumeOfWater;
        this.maxVolume = volumeOfWater;
        counter++;
        instanceNum = this.getNumberOfInstances();
        isBeingRepaired = false;
    }

    public int getNumberOfInstances(){
        return counter;
    }

    public int getVolumeOfWater(){
        return volumeOfWater;
    }

    public void lowerVolumeOfwater(){
        this.volumeOfWater --;
    }

    public void increaseVolumeOfWater() {
        this.volumeOfWater+= 10;
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
    public void destroy(Animation animation, float elapseTime, SpriteBatch sb){
        if(!(this.health <= 0)){
            try {
                throw new Exception("can't destroy Entity with health greater than 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            sb.begin();
            sb.draw((TextureRegion) animation.getKeyFrame(elapseTime, true), this.position.x, this.position.y, 0, 0, 80, 80, 1, 1, 9, true);
            sb.end();
        }
    }

    public void drawWaterBar(OrthographicCamera camera, ShapeRenderer shape) {
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.CYAN);
        shape.rect(position.x - 50 , position.y + height/2 + 30, 100, 10);
        shape.end();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.CYAN);
        shape.rect(position.x - 50 , position.y + height/2 + 30, (volumeOfWater * 100/ maxVolume), 10);
        shape.end();
    }

}
