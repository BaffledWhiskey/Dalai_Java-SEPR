package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

/**
 * The class for Aliens in the main game. */
public class Alien extends Movable implements Combatant{

    CombatComponent combatComponent;
    float age;
    float maxAge;

    /**
     * The constructor for an Alien that can be used for testing. Note that this is not the constructor that is used in
     * the actual game.
     *
     * @param kroy The Kroy instance in which the Alien lives
     * @param position The Alien's position within the game world
     * @param size The Alien's size. It is used for rendering
     * @param sprite The Alien's Sprite
     * @param health The Alien's (maximum) health. It always starts out with full health
     * @param movementSpeed The Alien's movement speed in pixels per second
     * @param checkCollisions If false, then no collision checking will be donw, i.e. the Movable can move through walls and over water
     * @param combatComponent The Alien's combat component that it will use for combat
     */
    public Alien(Kroy kroy, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, boolean checkCollisions, CombatComponent combatComponent, float maxAge) {
        super(kroy, position, size, sprite, health, movementSpeed, checkCollisions);
        this.combatComponent = combatComponent;
        this.age = 0;
        this.maxAge = maxAge;

    }

    /**
     * Builds an Alien from a JsonValue object. This is the constructor that is used in the game.
     *
     * @param kroy The Kroy instance in which the Alien lives
     * @param json The JsonObject instance that holds the information according to which the alien is initialized. */
    public Alien(Kroy kroy, JsonValue json) {
        super(kroy, json);
        setVelocity(new Vector2(movementSpeed, 0).rotate((float) (Math.random() * 360f)));
        combatComponent = new CombatComponent(this, json.get("combat"));
        this.age = 0;
        this.maxAge = json.getFloat("maxAge");
    }

    /**
     * Updates the alien, looking for enemies that it can attack and moving around the map in a random pattern.
     *
     * @param deltaTime The amount of time that has passed since the last tick */
    public void update(float deltaTime) {
        age += deltaTime;

        // If the alien is above a certain age, make it slowly loose health. This limits the total amount of aliens in
        // the game.
        if (age > maxAge)
            addHealth(getMaxHealth() * -0.25f * deltaTime);

        combatComponent.update(deltaTime);

        // If the alien hits a wall, turn by 180 degrees
        if (!nextPositionIsValid(deltaTime))
            setVelocity(getVelocity().rotate(180));
        // Add a random element to the alien's velocity
        setVelocity(getVelocity().rotate((float) (Math.random() * 10f - 5f)));

        // CombatComponent::attack will do all further checks for us
        Unit closestEnemy = (Unit) getClosestOfTypes(new Class[] {FireEngine.class, FireStation.class});
        combatComponent.attack(closestEnemy);

        super.update(deltaTime);
    }

    @Override
    public CombatComponent getCombatComponent() {
        return combatComponent;
    }

    /**
     * A hook for the combat component. As we do not need special behaviour, we do not make any use of it here. */
    @Override
    public void onAttack(Projectile projectile) {

    }

    /**
     * A hook for the combat component. It is called right as the combat component attacks a target and spawns a
     * projectile.
     *
     * @param target The Unit that is to be attacked
     * @return The amount of damage that the fired projectile will cause to the target
     */
    @Override
    public float attackDamage(Unit target) {
        return combatComponent.getDamage();
    }
}
