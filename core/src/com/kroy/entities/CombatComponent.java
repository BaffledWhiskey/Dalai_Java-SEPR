package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

/**
 * Every entity that has the ability to shoot projectiles at other Units must implement the Combatant interface. Part
 * of that interface is the getCombatComponent getter, which must return an instance of this class. This instance then
 * provides the functionality to spawn projectiles and use them to attack units.
 * As Java does not allow multiple inheritance / Mixins, we can not include this in our entity inheritance hierarchy. By
 * providing the Combatant interface together with this component, we circumvent this. */
public class CombatComponent {

    private Combatant entity;
    private Sprite projectileSprite;
    private float damage;
    private float range;
    private boolean enabled;
    private float reloadTime;
    private float reloadCountdown;


    /**
     * This constructor can be used in tests, however it is not used in the main game.
     *
     * @param entity The entity that implements the combatant interface
     * @param damage The default damage that fired projectiles inflict
     * @param range The range in which targets can be attacked
     * @param reloadTime The reload time between each attack
     */
    public CombatComponent(Combatant entity, float damage, float range, float reloadTime) {
        this.entity = entity;
        this.damage = damage;
        this.range = range;
        this.enabled = true;
        this.reloadTime = reloadTime;
        this.reloadCountdown = reloadTime;
    }

    /**
     * Builds a CombatComponent from a JsonValue object.
     * @param entity The entity that implements the combatant interface
     * @param json The JsonObject instance that holds the information according to which the combat component is
     *             initialized*/
    public CombatComponent(Combatant entity, JsonValue json) {
        // Initialize the combat component
        this.entity = entity;
        damage = json.getFloat("damage");
        range = json.getFloat("range");
        String img = json.getString("img");
        reloadTime = json.getFloat("reloadTime");
        reloadCountdown = reloadTime;
        projectileSprite = getEntity().getKroy().getSprite(img);
    }

    /**
     * Updates the combat component. Not much needs to be done other than keeping track of time to update the reload
     * countdown.
     *
     * @param deltaTime The amount of time that has passed since the last tick*/
    public void update(float deltaTime) {
        reloadCountdown -= deltaTime;
    }


    /**
     * Attack a given target. All tests will be run to check whether the target is valid. The hooks in the Combatant
     * interface are used to hand control to the Combatant when needed.
     *
     * @param target The Unit that is to be attacked */
    public void attack(Unit target) {
        float attackStrength = entity.attackDamage(target);
        // Check if target is valid
        if (!hasReloaded() || !isInRange(target) || attackStrength <= 0)
            return;

        Kroy kroy = entity.getKroy();
        // Spawn a new projectile
        Projectile projectile = new Projectile(kroy, entity.getPosition().cpy(), 10 * attackStrength, projectileSprite, -1, 500.0f, target, attackStrength);
        kroy.addEntity(projectile);
        reloadCountdown = reloadTime;
        entity.onAttack(projectile);
    }

    /**
     * A simple check whether a target is in range.
     *
     * @param target The Unit that is to be attacked
     * @return whether a target is in range
     */
    public boolean isInRange(Unit target) {
        if (target == null)
            return false;
        return entity.getPosition().dst2(target.position) <= range * range;
    }


    /**
     * Checks whether a given position is within range of the combat component.
     *
     * @param pos The position in question
     * @return
     */
    public boolean isInRange(Vector2 pos) {
        if (pos == null)
            return false;
        return entity.getPosition().dst2(pos) <= range * range;
    }

    /**
     * @return Whether the combat component has reloaded and is ready to fire again */
    public boolean hasReloaded() {
        return reloadCountdown <= 0;
    }

    public Combatant getEntity() {
        return entity;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getRange() {
        return range;
    }
}
