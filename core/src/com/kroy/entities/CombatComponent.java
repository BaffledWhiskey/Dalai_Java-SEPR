package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.GameScreen;

public class CombatComponent {

    private Entity entity;
    private Sprite projectileSprite;
    private float damage;
    private float range;
    private boolean enabled;

    public CombatComponent(Entity entity, float damage, float range) {
        this.entity = entity;
        this.damage = damage;
        this.range = range;
        this.enabled = true;
    }

    public void attack(Unit target) {
        if (!isInRange(target))
            return;
        GameScreen gameScreen = entity.gameScreen;
        Projectile projectile = new Projectile(gameScreen, entity.position.cpy(), new Vector2(10, 10), projectileSprite, -1, 10.0f, target, damage);
        gameScreen.addEntity(projectile);
    }

    public boolean isInRange(Unit target) {
        return entity.position.dst2(target.position) <= range * range;
    }
}
