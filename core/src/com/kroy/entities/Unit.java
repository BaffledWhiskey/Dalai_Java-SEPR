package com.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.Point;

import java.util.ArrayList;

/**
 *  A Unit can move around the screen and can attack its enemy.
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

    /**
     * Allows smooth movement of entities, supports hitboxes allowing movement to be restricted
     * @param direction A string in all caps determining the direction (options are "UP "DOWN" "LEFT" "RIGHT")
     * @param hitBoxes the arraylist of hitboxes you wish this entity to have collisions with
     */
    public void updatePosition(String direction, ArrayList<HitBox> hitBoxes){
        //If 'up' key pressed, move the entity up
        if(direction=="UP"){
            this.drawable.translateY((int) ((this.movementSpeed) * Gdx.graphics.getDeltaTime()));
            this.drawable.setRotation(0);
            this.position = new Point((int)(this.drawable.getX()+this.drawable.getWidth()/2),(int)(this.drawable.getY()+this.drawable.getHeight()/2));
            //If the Unit enters a HitBox, move the unit back to a legal position.
            for(HitBox hitBox: hitBoxes){
                if(this.position.y > hitBox.position.y && this.position.y < hitBox.position.y+hitBox.HEIGHT
                        && this.position.x >= hitBox.position.x  && this.position.x <= hitBox.position.x+hitBox.WIDTH){
                    this.position.y = hitBox.position.y-25;
                    this.drawable.setPosition(this.drawable.getX(), this.position.y);
                }
            }

        }
        //If 'down' key pressed, move the entity down
        else if(direction=="DOWN"){
            this.drawable.translateY((int) ((this.movementSpeed) * -Gdx.graphics.getDeltaTime()));
            this.drawable.setRotation(180);
            this.position = new Point((int)(this.drawable.getX()+this.drawable.getWidth()/2),(int)(this.drawable.getY()+this.drawable.getHeight()/2));
            //If the Unit enters a HitBox, move the unit back to a legal position.
            for(HitBox hitBox: hitBoxes){
                if(this.position.y < hitBox.position.y+hitBox.HEIGHT && this.position.y > hitBox.position.y
                        && this.position.x >= hitBox.position.x && this.position.x <= hitBox.position.x+hitBox.WIDTH){
                    this.position.y = hitBox.position.y+hitBox.HEIGHT-8;
                    this.drawable.setPosition(this.drawable.getX(), this.position.y);
                }
            }
        }
        //If 'left' key pressed, move the entity left
        else if(direction=="LEFT"){
            this.drawable.translateX((int) ((this.movementSpeed) * -Gdx.graphics.getDeltaTime()));
            this.drawable.setRotation(90);
            this.position = new Point((int)(this.drawable.getX()+this.drawable.getWidth()/2),(int)(this.drawable.getY()+this.drawable.getHeight()/2));
            //If the Unit enters a HitBox, move the unit back to a legal position.
            for(HitBox hitBox: hitBoxes){
                if(this.position.x < hitBox.position.x+hitBox.WIDTH && this.position.x > hitBox.position.x
                       && this.position.y >= hitBox.position.y && this.position.y <= hitBox.position.y+hitBox.HEIGHT){
                    this.position.x = hitBox.position.x+hitBox.WIDTH+8;
                    this.drawable.setPosition(this.position.x, this.drawable.getY());
                }
            }
        }
        //If 'right' key pressed, move the entity right
        else if(direction=="RIGHT"){
            this.drawable.translateX((int) ((this.movementSpeed) * Gdx.graphics.getDeltaTime()));
            this.drawable.setRotation(270);
            this.position = new Point((int)(this.drawable.getX()+this.drawable.getWidth()/2),(int)(this.drawable.getY()+this.drawable.getHeight()/2));
            //If the Unit enters a HitBox, move the unit back to a legal position.
            for(HitBox hitBox: hitBoxes){
                if(this.position.x > hitBox.position.x && this.position.x < hitBox.position.x+hitBox.WIDTH
                        && this.position.y >= hitBox.position.y && this.position.y <= hitBox.position.y+hitBox.HEIGHT){
                    this.position.x = hitBox.position.x-8;
                    this.drawable.setPosition(this.position.x, this.drawable.getY());
                }
            }
        }

        //this.position = new Point((int)(this.drawable.getX()+this.drawable.getWidth()/2),(int)(this.drawable.getY()+this.drawable.getHeight()/2));

    }

}
