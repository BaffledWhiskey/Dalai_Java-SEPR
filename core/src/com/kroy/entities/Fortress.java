package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class Fortress extends Unit implements Combatant {

    private CombatComponent combatComponent;
    private float alienSpawnRate;
    private JsonValue alienJSON;
    private Sprite neutralSprite;
    public boolean isOccupied;
    int scoreValue; // The amount of points awarded for freeing this fortress

    /**
     * The constructor for a Fortress that can be used for testing. Note that this is not the constructor that is used in
     * the actual game.
     *
     * @param kroy The Kroy instance in which the Fortress lives
     * @param position The Fortress's position within the game world
     * @param size The Fortress's size. It is used for rendering
     * @param sprite The Fortress's Sprite
     * @param health The Fortress's (maximum) health. It always starts out with full health
     * @param alienJSON The Json object according to which the aliens will be constructed
     */
    public Fortress(Kroy kroy, Vector2 position, float size, Sprite sprite, float health, CombatComponent combatComponent, JsonValue alienJSON, Sprite neutralSprite) {
        super(kroy, position, size, sprite, health);
        this.combatComponent = combatComponent;
        this.alienJSON = alienJSON;
        this.neutralSprite = neutralSprite;
        isOccupied = true;
    }

    /**
     * Builds a Fortress from a JsonValue object. This is the constructor that is used in the game.
     *
     * @param kroy The Kroy instance in which the fortress lives
     * @param json The JsonObject instance that holds the information according to which the fortress is initialized. */
    public Fortress(Kroy kroy, JsonValue json) {
        super(kroy, json);
        combatComponent = new CombatComponent(this, json.get("combat"));
        alienSpawnRate = json.getFloat("alienSpawnRate");
        alienJSON = json.get("alienType");
        neutralSprite = kroy.getSprite(json.getString("neutralImg"));
        isOccupied = true;
    }


    /**
     * Updates the Fortress.
     *
     * @param deltaTime The amount of time that has passed since the last tick */
    public void update(float deltaTime) {
        if (isOccupied) {
            attackNearestEnemy();
            combatComponent.update(deltaTime);

            // Randomly spawn aliens
            if (Math.random() < alienSpawnRate * deltaTime) {
                spawnAlien();
            }

            // Increase the fortress' damage over time
            combatComponent.setDamage(combatComponent.getDamage() + 0.5f * deltaTime);
        }

        super.update(deltaTime);
    }

    @Override
    protected void onHealthBelowZero() {
        isOccupied = false;
        setSprite(neutralSprite);
        //getKroy().addScore();
    }

    /**
     * Spawns a new alien according to the alienJson object. */
    private void spawnAlien() {
        Alien alien = new Alien(kroy, alienJSON);
        kroy.addEntity(alien);
    }

    /**
     * Attacks the nearest enemy. */
    private void attackNearestEnemy() {
        combatComponent.attack((Unit) getClosestOfTypes(new Class[]{FireEngine.class}));
    }

    public CombatComponent getCombatComponent() {
        return combatComponent;
    }

    @Override
    public void onAttack(Projectile projectile) {
        return;
    }

    @Override
    public float attackDamage(Unit target) {
        return combatComponent.getDamage();
    }
}