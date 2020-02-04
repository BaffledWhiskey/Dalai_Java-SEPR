package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.Kroy;

public class Projectile extends Movable {

    Unit target;
    float damage;
    float originalMovementSpeed;

    public Projectile(Kroy gameScreen, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, Unit target, float damage) {
        super(gameScreen, position, size, sprite, health, movementSpeed);
        this.target = target;
        this.damage = damage;
        originalMovementSpeed = movementSpeed;
    }

    public void update(float deltaTime) {
        Vector2 targetPosition = target.getPosition();
        Vector2 delta = targetPosition.cpy().sub(position);

        // Check if the projectile reached its target
        if (delta.len2() < movementSpeed * movementSpeed * deltaTime) {
            impact();
            return;
        }

        setVelocity(delta);
        super.update(deltaTime);
    }

    void impact() {
        target.setHealth(target.getHealth() - damage);
        removeSelf();
    }
}
