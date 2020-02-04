package com.kroy.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

/**
 *  A Unit can move around the screen and can attack its enemy.
 */
public abstract class Unit extends Entity {

    private float health;
    private float maxHealth;

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

    public void update(float deltaTime) {
        super.update(deltaTime);
        if (health == 0)
            removeSelf();
    }

    public void drawShapes() {
        super.drawShapes();
        if (health != -1)
            drawHealthBar();
    }

    /**
     * Draws the health bar for the given entity based on its current health
     */
    public void drawHealthBar() {
        ShapeRenderer shapeRenderer = kroy.getShapeRenderer();

        Vector2 healthBarPosition = position.cpy().add(size * -0.5f, size * 0.5f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(healthBarPosition.x, healthBarPosition.y, 100, 10);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(healthBarPosition.x, healthBarPosition.y, 100 * health / maxHealth, 10);
        shapeRenderer.end();
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }
}
