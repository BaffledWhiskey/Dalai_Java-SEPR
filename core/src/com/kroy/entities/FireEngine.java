package com.kroy.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class FireEngine extends Movable implements Combatant {

    private float maxWater;
    private float water;
    float originalMovementSpeed;
    private CombatComponent combatComponent;
    private boolean attack;

    /**
     * The constructor for a FireEngine that can be used for testing. Note that this is not the constructor that is used in
     * the actual game.
     *
     * @param kroy The Kroy instance in which the FireEngine lives
     * @param position The FireEngine's position within the game world
     * @param size The FireEngine's size. It is used for rendering
     * @param sprite The FireEngine's Sprite
     * @param health The FireEngine's (maximum) health. It always starts out with full health
     * @param movementSpeed The FireEngine's movement speed in pixels per second
     * @param checkCollisions If false, then no collision checking will be donw, i.e. the Movable can move through walls and over water
     * @param water The maximum amount of water that the fire engine can hold at once
     * @param combatComponent The FireEngine's combat component that it will use for combat
     */
    public FireEngine(Kroy kroy, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, boolean checkCollisions, float water, CombatComponent combatComponent) {
        super(kroy, position, size, sprite, health, movementSpeed, checkCollisions);
        this.originalMovementSpeed = movementSpeed;
        this.water = water;
        this.maxWater = water;
        this.combatComponent = combatComponent;
        attack = false;
    }

    /**
     * Builds a FireEngine from a JsonValue object. This is the constructor that is used in the game.
     *
     * @param kroy The Kroy instance in which the Alien lives
     * @param json The JsonObject instance that holds the information according to which the fire engine is initialized. */
    public FireEngine(Kroy kroy, JsonValue json) {
        super(kroy, json);
        maxWater = json.getFloat("water");
        originalMovementSpeed = json.getFloat("movementSpeed");
        water = maxWater;
        combatComponent = new CombatComponent(this, json.get("combat"));
        attack = false;
    }

    /**
     * Updates the FireEngine, attacking the nearest enemy if needed, and handling user input when selected.
     *
     * @param deltaTime The amount of time that has passed since the last tick */
    public void update(float deltaTime) {
        if (isSelected())
            handleUserInput();
        if (attack)
            attackNearestEnemy();

        combatComponent.update(deltaTime);

        // Adapt the movement speed to the tile type that the fire engine is moving over
        TiledMapTile tile = kroy.getTile(position);
        float speedFactor = tile.getProperties().get("speedFactor", Float.class);
        movementSpeed = originalMovementSpeed * speedFactor;

        super.update(deltaTime);
    }

    /**
     * Handles user input when the fire engine is selected. This includes the w, a, s, d keys for movement and the
     * space bar for attacking. */
    private void handleUserInput() {
        getKroy().getTile(getPosition());
        float x = 0;
        float y = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            x += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            x -= 1;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
            setAttack(true);
        else
            setAttack(false);

        setVelocity(new Vector2(x, y));
    }

    /**
     * Helper function to attack the nearest enemy (i.e. nearest Alien of Fortress). */
    private void attackNearestEnemy() {
        combatComponent.attack(getClosestOfTypes(new Class[]{Alien.class, Fortress.class}));
    }

    /**
     * Draws a bar that indicates the amount of water left in the fire engine's tank. */
    public void drawShapes() {
        super.drawShapes();
        drawWaterBar();
    }

    /**
     * Draws the water bar based on how much water the fire engine has left in the tank.
     */
    public void drawWaterBar() {
        final float maxWaterBarWidth = 100;
        final float maxWaterBarHeight = 10;
        ShapeRenderer shapeRenderer = kroy.getShapeRenderer();

        Vector2 waterBarPosition = position.cpy().add(-0.5f * maxWaterBarWidth, size * 0.5f + 1.5f * maxWaterBarHeight);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(waterBarPosition.x, waterBarPosition.y, maxWaterBarWidth, maxWaterBarHeight);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(waterBarPosition.x, waterBarPosition.y, maxWaterBarWidth * water / maxWater, maxWaterBarHeight);
        shapeRenderer.end();
    }

    /**
     * A hook for the combat component. We update the amount of water left in the fire engine's tank. */
    @Override
    public void onAttack(Projectile projectile) {
        addWater(-Math.max(0, projectile.damage));
    }

    /**
     * A hook for the combat component. If no water is left in the tank, the attack damage goes to 0, indicating that
     * no projectiles can be shot anymore.
     *
     * @param target The Unit that is to be attacked
     * @return The amount of damage that the fired projectile will cause to the target
     */
    @Override
    public float attackDamage(Unit target) {
        if (!attack)
            return 0;
        if (target.getClass() == Fortress.class && !((Fortress) target).isOccupied)
            return 0;
        return Math.min(water, combatComponent.getDamage());
    }

    public void setWater(float water) {
        // Do not allow more water in the tank than its maximum capacity or amounts below 0
        this.water = Math.max(0, Math.min(water, maxWater));
    }

    public void addWater(float delta) {
        setWater(water + delta);
    }

    public CombatComponent getCombatComponent() {
        return combatComponent;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public boolean isSelected() {
        return getKroy().getSelectedFireEngine() == this;
    }
}