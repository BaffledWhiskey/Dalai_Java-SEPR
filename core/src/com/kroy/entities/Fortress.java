package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class Fortress extends Unit implements Combatant {

    private CombatComponent combatComponent;

    public Fortress(Kroy gameScreen, Vector2 position, float size, Sprite sprite, float health, CombatComponent combatComponent) {
        super(gameScreen, position, size, sprite, health);
        this.combatComponent = combatComponent;
    }

    /**
     * Builds a Fortress from a JsonValue object. */
    public Fortress(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        combatComponent = new CombatComponent((Combatant) this, json.get("combat"));
    }

    public void update(float deltaTime) {
        attackNearestEnemy();

        super.update(deltaTime);
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