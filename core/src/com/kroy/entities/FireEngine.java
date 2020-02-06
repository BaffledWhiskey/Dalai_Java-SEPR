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

    public FireEngine(Kroy gameScreen, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, boolean checkCollisions, float water, CombatComponent combatComponent) {
        super(gameScreen, position, size, sprite, health, movementSpeed, checkCollisions);
        this.originalMovementSpeed = movementSpeed;
        this.water = water;
        this.maxWater = water;
        this.combatComponent = combatComponent;
        attack = false;
    }

    /**
     * Builds a FireEngine from a JsonValue object. */
    public FireEngine(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        maxWater = json.getFloat("water");
        originalMovementSpeed = json.getFloat("movementSpeed");
        water = maxWater;
        combatComponent = new CombatComponent(this, json.get("combat"));
        attack = false;
    }

    public void update(float timeDelta) {
        if (isSelected())
            handleUserInput();
        if (attack)
            attackNearestEnemy();

        combatComponent.update(timeDelta);

        TiledMapTile tile = kroy.getTile(position);
        float speedFactor = tile.getProperties().get("speedFactor", Float.class);
        movementSpeed = originalMovementSpeed * speedFactor;

        super.update(timeDelta);
    }

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

    private void attackNearestEnemy() {
        combatComponent.attack(getClosestOfTypes(new Class[]{Alien.class, Fortress.class}));
    }

    public void drawShapes() {
        super.drawShapes();
        drawWaterBar();
    }

    /**
     * Draws the health bar for the given entity based on its current health
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

    public CombatComponent getCombatComponent() {
        return combatComponent;
    }

    @Override
    public void onAttack(Projectile projectile) {
        addWater(-Math.max(0, projectile.damage));
    }

    @Override
    public float attackDamage(Unit target) {
        if (attack)
            return Math.min(water, combatComponent.getDamage());
        return 0;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public boolean isSelected() {
        return getKroy().getSelectedFireEngine() == this;
    }

    public void setWater(float water) {
        this.water = Math.max(0, Math.min(water, maxWater));
    }

    public void addWater(float delta) {
        setWater(water + delta);
    }
}