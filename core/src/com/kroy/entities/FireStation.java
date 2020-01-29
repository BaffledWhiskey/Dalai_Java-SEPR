package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class FireStation extends Unit {

    public FireStation(Kroy gameScreen, Vector2 position, float size, Sprite sprite, float health) {
        super(gameScreen, position, size, sprite, health);
    }

    /**
     * Builds a ForeStation from a JsonValue object. */
    public FireStation(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
    }

}
