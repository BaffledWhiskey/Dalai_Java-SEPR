package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class Alien extends Movable {

    public Alien(Kroy gameScreen, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, boolean checkCollisions) {
        super(gameScreen, position, size, sprite, health, movementSpeed, checkCollisions);
    }

    /**
     * Builds an Alien from a JsonValue object. */
    public Alien(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        setVelocity(new Vector2(movementSpeed, 0));
    }

    public void update(float deltaTime) {

        setVelocity(getVelocity().rotate((float) (Math.random() * 10f - 5f)));

        super.update(deltaTime);
    }
}
