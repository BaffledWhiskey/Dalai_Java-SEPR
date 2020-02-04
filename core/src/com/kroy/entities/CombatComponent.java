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

    public CombatComponent(Combatant entity, JsonValue json) {
        // Initialize the combat component
        this.entity = entity;
        damage = json.getFloat("damage");
        range = json.getFloat("range");
        String img = json.getString("img");
        projectileSprite = getEntity().getKroy().getSprite(img);
    }

    public void attack(Unit target) {
        float attackStrength = entity.attackStrength(target);
        if (!isInRange(target) || attackStrength <= 0)
            return;
        Kroy gameScreen = entity.getKroy();
        Projectile projectile = new Projectile(gameScreen, entity.getPosition().cpy(), 10 * attackStrength, projectileSprite, -1, 500.0f, target, attackStrength);
        gameScreen.addEntity(projectile);
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
