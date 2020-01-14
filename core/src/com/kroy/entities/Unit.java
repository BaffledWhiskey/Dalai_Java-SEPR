package com.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.Point;

/**
 *  Unit class extends the entity class and has the children of Alien patrol and FireEngine
 */
public class Unit extends Entity {
    public int movementSpeed;

    public Unit(int movementSpeed, int health, int range, Point position, Texture img){
        super(health,range,position,img);
        this.movementSpeed = movementSpeed;
    }

    /**
     *
     * @param destination coords of desired destination for current unit
     * @return true if the path can be taken, false if not
     */
    public boolean pathFind(Point destination){
        return false;
    }


    //Added this so that we can move both the sprite and entity with the same method
    public void updatePosition(String direction){
        if(direction=="UP"){
            this.drawable.translateY((int) ((this.movementSpeed) * Gdx.graphics.getDeltaTime()));
            this.drawable.setRotation(0);
        }
        else if(direction=="DOWN"){
            this.drawable.translateY((int) ((this.movementSpeed) * -Gdx.graphics.getDeltaTime()));
            this.drawable.setRotation(180);
        }
        else if(direction=="LEFT"){
            this.drawable.translateX((int) ((this.movementSpeed) * -Gdx.graphics.getDeltaTime()));
            this.drawable.setRotation(270);
        }
        else if(direction=="RIGHT"){
            this.drawable.translateX((int) ((this.movementSpeed) * Gdx.graphics.getDeltaTime()));
            this.drawable.setRotation(90);
        }
        this.position = new Point((int)(this.drawable.getX()+this.drawable.getWidth()/2),(int)(this.drawable.getY()+this.drawable.getHeight()/2));

    }

}
