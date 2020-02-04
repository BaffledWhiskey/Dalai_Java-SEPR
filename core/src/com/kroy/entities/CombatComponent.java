package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class CombatComponent {

    private Combatant entity;
    private Sprite projectileSprite;
    private float damage;
    private float range;
    private boolean enabled;

    public CombatComponent(Combatant entity, float damage, float range) {
        this.entity = entity;
        this.damage = damage;
        this.range = range;
        this.enabled = true;
    }


    /**
     * Builds a CombatComponent from a JsonValue object. */
    public CombatComponent(Combatant entity, JsonValue json) {
        // Initialize the combat component
        this.entity = entity;
        damage = json.getFloat("damage");
        range = json.getFloat("range");
        String img = json.getString("img");
        projectileSprite = getEntity().getKroy().getSprite(img);
    }

    /**
     * Attack a given target. All tests will be run to check whether the target is valid.
     * @param target The Unit that is to be attacked*/
    public void attack(Unit target) {
        float attackStrength = entity.attackDamage(target);
        // Check if target is valid
        if (!isInRange(target) || attackStrength <= 0)
            return;

        Kroy kroy = entity.getKroy();
        // Spawn a new projectile
        Projectile projectile = new Projectile(kroy, entity.getPosition().cpy(), 10 * attackStrength, projectileSprite, -1, 500.0f, target, attackStrength);
        kroy.addEntity(projectile);
        entity.onAttack(projectile);
    }

    public boolean isInRange(Unit target) {
        return entity.getPosition().dst2(target.position) <= range * range;
    }

    public Combatant getEntity() {
        return entity;
    }

    public float getDamage() {
        return damage;
    }
    public float getRange() {
        return range;
    }
}
