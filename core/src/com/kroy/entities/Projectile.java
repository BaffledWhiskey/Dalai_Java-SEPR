package com.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kroy.game.Point;

public class Projectile{
    final Point initialPosition;
    Point position;
    int damage = 5;
    double distanceMoved = 0;
    public Sprite drawable;
    int range;
    Vector2 direction;

    public Projectile(Point position, int range, Vector2 direction){
        this.position = position;
        this.initialPosition = position;
        this.drawable = new Sprite(new Texture("Sprites/water.png"));

        // Add some random variance to each bullet
        direction.x *= Math.random() * (((1.2 - 0.8) + 1) + 0.8);
        direction.y *= Math.random() * (((1.2 - 0.8) + 1) + 0.8);
        this.direction = direction;
        this.range = range;
        drawable.setPosition(position.x - drawable.getWidth()/2, position.y - drawable.getHeight()/2);
    }

    /**
     * Updates the health of the entity that has just been hit. Does not check wether its health it less than 0
     * @param target The Entity that the bullet has just hit
     * @return target.health The new health of the entity that has just been hit.
     */
    public int onHit(Entity target){
        target.setHealth(target.health - this.damage);
        return target.health;
    }

    /**
     * Updates the position of the bullet based on the direction vector.
     * If the distance exceeds the range location does not change.
     * @return true  The bullet is still within the range that it can travel. Keep entity.
     *         false The bullet has exceeded the range. Remove entity from game.
     */
    public Boolean newPosition(){
        this.position.x += direction.x;
        this.position.y += direction.y;
        this.distanceMoved = position.distance(initialPosition);
        if(distanceMoved > range){
            this.position.x -= direction.x;
            this.position.y -= direction.y;
        }
        this.drawable.setPosition(position.x,position.y);
        return true;
    }
}