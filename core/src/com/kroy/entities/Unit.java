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

    /**
     * The constructor for a Fortress that can be used for testing. Note that this is not the constructor that is used in
     * the actual game.
     *
     * @param kroy The Kroy instance in which the Unit lives
     * @param position The Unit's position within the game world
     * @param size The Unit's size. It is used for rendering
     * @param sprite The Unit's Sprite
     * @param health The Unit's (maximum) health. It always starts out with full health
     */
    public Unit(Kroy kroy, Vector2 position, float size, Sprite sprite, float health){
        super(kroy, position, size, sprite);
        this.health = health;
        this.maxHealth = health;
    }

    /**
     * Builds a Unit from a JsonValue object.
     * @param kroy The Kroy instance in which the Unit lives
     * @param json The JsonObject instance that holds the information according to which the unit is initialized
     * */
    public Unit(Kroy kroy, JsonValue json) {
        super(kroy, json);
        health = json.getFloat("health");
        maxHealth = health;
    }


    /**
     * Checks whether the unit's health is below 0.
     *
     * @param deltaTime
     */
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (health == 0)
            onHealthBelowZero();
    }

    /**
     * A hook that is called when the unit's health drops below 0. Can be overwritten by subclasses for special
     * behaviour.*/
    protected void onHealthBelowZero() {
        removeSelf();
    }

    /**
     * Draws the unit's health bar.
     */
    public void drawShapes() {
        super.drawShapes();
        if (health != -1)
            drawHealthBar();
    }

    /**
     * Draws the health bar for the given entity based on its current health
     */
    public void drawHealthBar() {
        final float maxHealthBarWidth = 100;
        final float maxHealthBarHeight = 10;
        ShapeRenderer shapeRenderer = kroy.getShapeRenderer();

        Vector2 healthBarPosition = position.cpy().add(-0.5f * maxHealthBarWidth, size * 0.5f);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(healthBarPosition.x, healthBarPosition.y, maxHealthBarWidth, maxHealthBarHeight);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(healthBarPosition.x, healthBarPosition.y, maxHealthBarWidth * health / maxHealth, maxHealthBarHeight);
        shapeRenderer.end();
    }

    /**
     * Returns the unit that is the closest to this Unit and is of one of the given types.
     *
     * @param types The types that are allowed */
    public Unit getClosestOfTypes(Class[] types) {
        Unit closest = null;
        float closestDistance = Float.POSITIVE_INFINITY;

        for (Class klass : types)
            for (Entity entity : getKroy().getEntitiesOfType(klass)) {
                Unit unit = (Unit) entity;
                float dst2 = unit.position.dst2(getPosition());
                if (dst2 < closestDistance) {
                    closest = unit;
                    closestDistance = dst2;
                }
            }

        return closest;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = Math.max(0, Math.min(health, maxHealth));
    }

    public void addHealth(float delta) {
        setHealth(health + delta);
    }
}
