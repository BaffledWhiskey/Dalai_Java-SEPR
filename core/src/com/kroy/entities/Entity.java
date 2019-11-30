package com.kroy.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
public class Entity {
    int health;
    int range;
    Point position;
    ShapeRenderer shape = new ShapeRenderer();
    OrthographicCamera camera = new OrthographicCamera();


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

    public void drawBox(ArrayList<Entity> entities) {
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeType.Line);
        for(Entity entity : entities) {
            if (inRange(entity)) {
                shape.setColor(Color.RED);
            }
            else {
                shape.setColor(Color.GREEN);
            }
        }
        shape.rect(position.x, position.y, range * 2, range * 2);
        shape.end();
    }

    public int getHealth(){
        return this.health;
    }

    public void setHealth(int health){
        this.health = health;
    }

}
