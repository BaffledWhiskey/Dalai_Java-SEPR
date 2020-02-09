package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class Fortress extends Unit implements Combatant {

    private CombatComponent combatComponent;
    private float alienSpawnRate;
    private JsonValue alienJSON;

    public Fortress(Kroy gameScreen, Vector2 position, float size, Sprite sprite, float health, CombatComponent combatComponent, JsonValue alienJSON) {
        super(gameScreen, position, size, sprite, health);
        this.combatComponent = combatComponent;
        this.alienJSON = alienJSON;
    }

    /**
     * Builds a Fortress from a JsonValue object. */
    public Fortress(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        combatComponent = new CombatComponent(this, json.get("combat"));
        alienSpawnRate = json.getFloat("alienSpawnRate");
        alienJSON = json.get("alienType");
    }

    public void update(float deltaTime) {
        combatComponent.update(deltaTime);
        attackNearestEnemy();

        if (Math.random() < alienSpawnRate * deltaTime)
            spawnAlien();

        super.update(deltaTime);
    }

    private void spawnAlien() {
        Alien alien = new Alien(kroy, alienJSON);
        kroy.addEntity(alien);
    }

    private void attackNearestEnemy() {
        combatComponent.attack(getClosestOfTypes(new Class[]{FireEngine.class}));
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