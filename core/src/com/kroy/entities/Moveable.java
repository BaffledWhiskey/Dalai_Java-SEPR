package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public abstract class Moveable extends Unit {

    private float movementSpeed;
    private Vector2 velocity;

    public Moveable(Kroy gameScreen, Vector2 position, float size, Sprite sprite, int health, float movementSpeed) {
        super(gameScreen, position, size, sprite, health);
        this.movementSpeed = movementSpeed;
        this.velocity = new Vector2(0, 0);
    }

    /**
     * Builds a Moveable from a JsonValue object. */
    public Moveable(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        movementSpeed = json.getFloat("movementSpeed");
        this.velocity = new Vector2(0, 0);
    }


    @Override
    public void update(float timeDelta) {
        Vector2 currVelRotation = new Vector2(movementSpeed * 0.8f, 0).rotate(rotation);
        rotation = currVelRotation.add(velocity).angle();
        position.add(velocity);
        super.update(timeDelta);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity.setLength(movementSpeed);
    }
    public Vector2 getVelocity() {
        return velocity;
    }
}
