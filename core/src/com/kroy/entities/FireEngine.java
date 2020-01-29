package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class FireEngine extends Moveable {

    private float maxWater;
    private float water;

    public FireEngine(Kroy gameScreen, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, float water) {
        super(gameScreen, position, size, sprite, health, movementSpeed);
        this.water = water;
        this.maxWater = water;
    }

    /**
     * Builds a FireEngine from a JsonValue object. */
    public FireEngine(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        maxWater = json.getFloat("water");
        water = maxWater;
    }


}
