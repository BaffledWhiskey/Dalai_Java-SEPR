package com.kroy.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.kroy.game.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class Entity{
    int health;
    int range;
    int height;
    int width;
    public Point position;
    public Sprite drawable;


    /**
     * @param health The current health of the Entity
     * @param range The maximum range that the Entity can attack from
     * @param position The current position of the Entity
     * @param img The texture of the entity that is to be drawn
     */
    public Entity(int health, int range, Point position,Texture img){
        this.health = health;
        this.range = range;
        this.position = position;
        this.height = img.getHeight();
        this.width = img.getWidth();
        this.drawable = new Sprite(img);
        drawable.setPosition(position.x - drawable.getWidth()/2,position.y - drawable.getHeight()/2);
    }

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
        //Initialising square for the shooter
        List<Integer> rangeBox = Arrays.asList(position.x, range, position.y, range);
        //Initialising square for the target
        List<Integer> targetBox = Arrays.asList(target.position.x, target.width / 2, target.position.y, target.height / 2);
        if(targetBox.get(0) - targetBox.get(1) < rangeBox.get(0) + rangeBox.get(1)
                && rangeBox.get(0) - rangeBox.get(1) < targetBox.get(0) + targetBox.get(1)
                && targetBox.get(2) - targetBox.get(3)  < rangeBox.get(2) + rangeBox.get(3)
                && rangeBox.get(2) - rangeBox.get(3) <  targetBox.get(2) + targetBox.get(3)) {
            return true;
        }
        else {
            return false;
        }
    }



    public void drawBox(ArrayList<FireEngine> target, OrthographicCamera camera, ShapeRenderer shape) {
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeType.Line);
        boolean redBox = false;
        for(Entity entity : target) {
            if (this.inRange(entity)) {
                redBox = true;
            }
        }
        if (redBox) {
            shape.setColor(Color.RED);
        }
        else {
            shape.setColor(Color.GREEN);
        }
        shape.rect(position.x - range, position.y - range, range * 2, range * 2);
        shape.end();
    }

    public void drawBox(ArrayList<Fortress> target, OrthographicCamera camera, Sprite sprite, ShapeRenderer shape) {
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeType.Line);
        boolean redBox = false;
        for(Entity entity : target) {
            if (this.inRange(entity)) {
                redBox = true;
            }
        }
        if (redBox) {
            shape.setColor(Color.RED);
        }
        else {
            shape.setColor(Color.GREEN);
        }
        shape.rect(position.x - range, position.y - range, range * 2, range * 2);
        shape.end();
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health){
        this.health = health;
    }

}
