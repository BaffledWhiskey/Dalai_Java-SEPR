package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.GameScreen;

public class Projectile extends Moveable {

    Unit target;
    float damage;

    public Projectile(GameScreen gameScreen, Vector2 position, Vector2 dimension, Sprite sprite, int health, float movementSpeed, Unit target, float damage) {
        super(gameScreen, position, dimension, sprite, health, movementSpeed);
    }
}
