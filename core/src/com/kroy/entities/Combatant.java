package com.kroy.entities;

import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.Kroy;

public interface Combatant {

    public CombatComponent getCombatComponent();
    public Kroy getKroy();
    public void onAttack(Projectile projectile);
    public float attackStrength(Unit target);
    public Vector2 getPosition();

}
