package com.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kroy.game.Point;

import java.util.ArrayList;

/**
 *
 * fire engine class extends the unit class
 */
public class FireEngine extends Unit{
    int volumeOfWater;
    int fireRate;
    public boolean isActive;
    private static int counter = 0; // Acts as counter for how many FireEngine class instances exist
    public int instanceNum; // What number fire engine is this. Mainly for Debugging
    public static ArrayList<Bullet> bullets = new ArrayList<>();

    SpriteBatch sb;


    public FireEngine(int volumeOfWater, int movementSpeed, int health, int range, Point position, Texture img, int fireRate){
        super(movementSpeed, health, range, position,img);
        this.fireRate = fireRate;
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
    public void toggleState(){
        if(isActive){
            isActive = false;
        }else {
            isActive = true;
        }
    }


    /**
     *
     */
    public void attackFortress(){
        Vector2 direction = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        System.out.println(this.volumeOfWater);
        // If Space is pressed create new bullet

        for (int i = 0; i < fireRate; i ++) {
            if(volumeOfWater > 0){
                bullets.add(new Bullet(this.position, this.range, direction));
                volumeOfWater -= 1;
                System.out.println("AC=ctualy Shooting");
            }else{
                System.out.println("not SHooting");
                sb = new SpriteBatch();
                sb.begin();
                Texture emptyTexture = new Texture("Sprites/Empty.png");
                Sprite drawable = new Sprite(emptyTexture);
                drawable.setPosition(this.position.x, this.position.y);
                drawable.draw(sb);
                sb.end();
                sb.dispose();

            }
        }

    }


    /**
     *
     * @param patrol the AlienPatrol the fire engine is to attack
     */
    public void attackAlienPatrols(AlienPatrol patrol){

    }

}
