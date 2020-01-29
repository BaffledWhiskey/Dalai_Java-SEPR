package com.kroy.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.GameScreen;

/**
 *  A Unit can move around the screen and can attack its enemy.
 */
public abstract class Unit extends Entity {

    protected float health;
    protected float maxHealth;

    public Unit(GameScreen gameScreen, Vector2 position, Vector2 dimension, Sprite sprite, float health){
        super(gameScreen, position, dimension, sprite);
        this.health = health;
        this.maxHealth = health;
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
