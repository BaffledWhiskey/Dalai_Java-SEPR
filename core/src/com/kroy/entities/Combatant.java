package com.kroy.entities;

import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.Kroy;

/**
 * An interface for all entities that have a CombatComponent. The CombatComponent makes use of the below methods to
 * communicate with the entity (i.e. Combatant) and provide hooks. */
public interface Combatant {

    /**
     * A simple getter.
     * @return The Combatant's combat component */
    CombatComponent getCombatComponent();
    /**
     * A simple getter.
     * @return  The Combatant's kroy instance */
    Kroy getKroy();
    /**
     * A hook that is called right after a target has been chosen and a projectile was spawned.
     * @param projectile The projectile that was spawned */
    void onAttack(Projectile projectile);

    /**
     * A hook that lets the combatant decide how much damage the projectile will do to a given target. Note that the
     * projectile will be spawned automatically.
     * @param target The Unit that is to be attacked
     * @return
     */
    float attackDamage(Unit target);
    /**
     * A simple getter.
     * @return The combatant's position */
    Vector2 getPosition();

}
