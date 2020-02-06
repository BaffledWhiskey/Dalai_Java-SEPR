package com.kroy.entities;

import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.Kroy;

/**
 * An interface for all entities that have a CombatComponent. The CombatComponent makes use of the below methods to
 * communicate with the entity (i.e. Combatant) and provide hooks. */
public interface Combatant {

    CombatComponent getCombatComponent();
    Kroy getKroy();
    void onAttack(Projectile projectile);
    float attackDamage(Unit target);
    Vector2 getPosition();

}
