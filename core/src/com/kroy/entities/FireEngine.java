package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class FireEngine extends Movable {

    private float maxWater;
    private float water;
    float originalMovementSpeed;

    public FireEngine(Kroy gameScreen, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, float water) {
        super(gameScreen, position, size, sprite, health, movementSpeed);
        this.originalMovementSpeed = movementSpeed;
        this.water = water;
        this.maxWater = water;
    }

    /**
     * Builds a FireEngine from a JsonValue object. */
    public FireEngine(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        maxWater = json.getFloat("water");
        originalMovementSpeed = json.getFloat("movementSpeed");
        water = maxWater;
    }

    public void update(float timeDelta) {

        TiledMapTile tile = kroy.getTile(position);
        float speedFactor = tile.getProperties().get("speedFactor", Float.class);
        movementSpeed = originalMovementSpeed * speedFactor;

        super.update(timeDelta);
    }
}