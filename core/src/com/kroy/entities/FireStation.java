package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.GameScreen;

public class FireStation extends Unit {

    public FireStation(GameScreen gameScreen, Vector2 position, Vector2 dimension, Sprite sprite, float health) {
        super(gameScreen, position, dimension, sprite, health);
    }
}
