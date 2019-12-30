package com.kroy.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kroy.game.Point;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 *
 */
public class Entity{
    int health;
    int range;
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
        if(abs(target.position.x - this.position.x) < this.range &&
                abs(target.position.y - this.position.y) < this.range){
            return true;
        }
        else {
            return false;
        }
    }


    public void drawBox(ArrayList<Entity> target, OrthographicCamera camera, Sprite sprite, ShapeRenderer shape) {
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
