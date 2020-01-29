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

    protected Kroy gameScreen;
    protected float size;
    protected Vector2 position;
    protected float rotation;
    protected Sprite sprite;

    /**
     * @param position The current position of the Entity
     * @param gameScreen The GameScreen class that owns the Entity
     * @param size The size of the Entity, i.e. radius
     */
    public Entity(Kroy gameScreen, Vector2 position, float size, Sprite sprite){
        this.gameScreen = gameScreen;
        this.position = position;
        this.size = size;
        this.sprite = sprite;
        rotation = 0;
    }

    /**
     * Builds an Entity from a JsonValue object. */
    public Entity(Kroy gameScreen, JsonValue json) {
        this.gameScreen = gameScreen;
        position = Tools.vector2fromJson(json.get("position"));
        size = json.getFloat("size");
        String imgPath = json.getString("img");
        sprite = gameScreen.getSprite(imgPath);
        rotation = 0;
    }

    /**
     * The update method that is called with every tick of the game. */
    public void update(float timeDelta) {
        render();
    }

    public void render() {
        SpriteBatch batch = gameScreen.getBatch();
        sprite.setPosition(position.x, position.y);
        sprite.setOriginCenter();
        float shortSide = Math.min(sprite.getHeight(), sprite.getWidth());
        float scalar = 0.5f * size / shortSide;
        sprite.setScale(scalar);
        sprite.setRotation(rotation);
        sprite.draw(batch);
    }

    /**
     * Returns whether a Vector is inside, i.e. collides with the entity.
     * @param v The vector */
    public boolean collides(Vector2 v) {
        return position.dst2(v) < size * size;
    }
}
