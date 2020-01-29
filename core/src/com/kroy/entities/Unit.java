package com.kroy.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

/**
 *  A Unit can move around the screen and can attack its enemy.
 */
public abstract class Unit extends Entity {

    protected float health;
    protected float maxHealth;

    public Unit(Kroy gameScreen, Vector2 position, float size, Sprite sprite, float health){
        super(gameScreen, position, size, sprite);
        this.health = health;
        this.maxHealth = health;
    }

    /**
     * Builds a Unit from a JsonValue object. */
    public Unit(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        health = json.getFloat("health");
        maxHealth = health;
    }

    /**
     */
    public void move(Vector2 delta){

    }

    /**
     * Draws the health bar for the given entity based on its current health
     * @param camera The camera used to render the game screen
     * @param shape The ShapeRenderer used to render the game screen
     */
    public void drawHealthBar(OrthographicCamera camera, ShapeRenderer shape) {

    }
}
