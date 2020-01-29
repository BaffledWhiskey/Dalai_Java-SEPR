package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.GameScreen;

public abstract class Moveable extends Unit {

    private float movementSpeed;
    private Vector2 velocity;

    public Moveable(GameScreen gameScreen, Vector2 position, Vector2 dimension, Sprite sprite, int health, float movementSpeed) {
        super(gameScreen, position, dimension, sprite, health);
        this.movementSpeed = movementSpeed;
        this.velocity = new Vector2(0, 0);
    }

    @Override
    public void update(float timeDelta) {
        super.update(timeDelta);
        position.add(velocity);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity.clamp(0, movementSpeed);
    }
}
