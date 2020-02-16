package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.Kroy;

public class Projectile extends Movable {

    Unit target;
    float damage;

    /**
     * The constructor for a Fortress that can be used for testing. Note that this is not the constructor that is used in
     * the actual game.
     *
     * @param kroy The Kroy instance in which the Fortress lives
     * @param position The Fortress's position within the game world
     * @param size The Fortress's size. It is used for rendering
     * @param sprite The Fortress's Sprite
     * @param health The Fortress's (maximum) health. It always starts out with full health
     * @param movementSpeed The projectile's movement speed
     * @param target The projectile's target
     * @param damage The amount of damage the projectile will cause to its target
     */
    public Projectile(Kroy kroy, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, Unit target, float damage) {
        super(kroy, position, size, sprite, health, movementSpeed, false);
        this.target = target;
        this.damage = damage;
    }

    /**
     * @param deltaTime The amount of time that has passed since the last tick
     */
    public void update(float deltaTime) {
        // Move the projectile towards the target
        Vector2 targetPosition = target.getPosition();
        Vector2 delta = targetPosition.cpy().sub(position);
        float adjRange = movementSpeed * deltaTime;
        if (delta.len2() < adjRange * adjRange)
            impact();
        setVelocity(delta);

        // Check if the projectile reached its target
        super.update(deltaTime);
    }

    /**
     * This is the method that gets called when the projectile hits its target.
     */
    void impact() {
        System.out.println("impact");
        target.addHealth(-damage);
        removeSelf();
    }
}
