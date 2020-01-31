package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.Tools;
import com.kroy.screens.Kroy;

/** The Entity class stores any object.
 */
public abstract class Entity{

    Kroy kroy;
    float size;
    Vector2 position;
    float rotation;
    Sprite sprite;

    /**
     * @param position The current position of the Entity
     * @param kroy The Kroy class that owns the Entity
     * @param size The size of the Entity, i.e. radius
     */
    public Entity(Kroy kroy, Vector2 position, float size, Sprite sprite){
        this.kroy = kroy;
        this.position = position;
        this.size = size;
        this.sprite = sprite;
        rotation = 0;
    }

    /**
     * Builds an Entity from a JsonValue object. */
    public Entity(Kroy kroy, JsonValue json) {
        this.kroy = kroy;
        position = Tools.vector2fromJson(json.get("position"));
        size = json.getFloat("size");
        String imgPath = json.getString("img");
        sprite = kroy.getSprite(imgPath);
        rotation = 0;
    }

    /**
     * The update method that is called with every tick of the game. */
    public void update(float timeDelta) {}

    public void render() {
        SpriteBatch batch = kroy.getBatch();
        sprite.setPosition(position.x, position.y);
        sprite.setOriginCenter();
        float shortSide = Math.min(sprite.getHeight(), sprite.getWidth());
        float scalar = 0.5f * size / shortSide;
        sprite.setScale(scalar);
        sprite.setRotation(rotation);
        sprite.draw(batch);
    }

    public void drawShapes() {

    }

    /**
     * Returns whether a Vector is inside, i.e. collides with the entity.
     * @param v The vector */
    public boolean collides(Vector2 v) {
        return position.dst2(v) < size * size;
    }

    public Vector2 getPosition() {
        return position;
    }
}
