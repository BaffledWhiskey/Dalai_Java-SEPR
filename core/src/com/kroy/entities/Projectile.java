package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.Kroy;

public class Projectile extends Movable {

    Unit target;
    float damage;

    public Projectile(Kroy gameScreen, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, Unit target, float damage) {
        super(gameScreen, position, size, sprite, health, movementSpeed);
        this.target = target;
        this.damage = damage;
    }
}
