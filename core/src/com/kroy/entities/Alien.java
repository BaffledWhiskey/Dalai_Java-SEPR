package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class Alien extends Movable implements Combatant{

    CombatComponent combatComponent;

    public Alien(Kroy gameScreen, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, boolean checkCollisions, CombatComponent combatComponent) {
        super(gameScreen, position, size, sprite, health, movementSpeed, checkCollisions);
        this.combatComponent = combatComponent;
    }

    /**
     * Builds an Alien from a JsonValue object. */
    public Alien(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        setVelocity(new Vector2(movementSpeed, 0).rotate((float) (Math.random() * 360f)));
        combatComponent = new CombatComponent(this, json.get("combat"));
    }

    public void update(float deltaTime) {
        combatComponent.update(deltaTime);

        // If the alien hits a wall, turn by 180 degrees
        if (!nextPositionIsValid(deltaTime))
            setVelocity(getVelocity().rotate(180));
        // Add a random element to the alien's velocity
        setVelocity(getVelocity().rotate((float) (Math.random() * 10f - 5f)));

        // CombatComponent::attack will do all further checks for us
        Unit closestEnemy = getClosestOfTypes(new Class[] {FireEngine.class, FireStation.class});
        combatComponent.attack(closestEnemy);

        super.update(deltaTime);
    }

    @Override
    public CombatComponent getCombatComponent() {
        return combatComponent;
    }

    @Override
    public void onAttack(Projectile projectile) {

    }

    @Override
    public float attackDamage(Unit target) {
        return combatComponent.getDamage();
    }
}
