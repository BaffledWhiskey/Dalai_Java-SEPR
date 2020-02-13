package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.Tools;
import com.kroy.screens.Kroy;

/** The Entity class stores any object within the game world. It is the root node of our entity inheritance hierarchy.
 */
public abstract class Entity{

    Kroy kroy;
    float size;
    Vector2 position;
    float rotation;
    Sprite sprite;
    private boolean toBeRemoved;

    /**
     * A constructor that can be used in unit tests.
     *
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
     * Builds an Entity from a JsonValue object. This is the constructor that is actually used in the game.
     * @param kroy The Kroy instance in which the Entity lives
     * @param json The JsonObject instance that holds the information according to which the entity is initialized*/
    public Entity(Kroy kroy, JsonValue json) {
        this.kroy = kroy;
        position = Tools.vector2fromJson(json.get("position"));
        if (json.has("size"))
            size = json.getFloat("size");
        else
            size = json.getInt("sizeInTiles") * 32f * Tools.MAP_UNIT_SCALE;
        String imgPath = json.getString("img");
        sprite = kroy.getSprite(imgPath);
        rotation = 0;
    }

    /**
     * The update method that is called with every tick of the game. It will be overwritten by one of its subclasses.
     * We do not make it abstract as a subclass might not overwrite it in case no special behaviour is needed.
     * @param timeDelta The amount of time that has passed since the last tick */
    public void update(float timeDelta) {}

    /**
     * The render method renders the entity to the Kroy instance's screen. This method must always be called within the
     * context of batch.begin(), as it makes use of sprite.draw(batch). */
    public void render() {
        SpriteBatch batch = kroy.getBatch();
        float shortSide = Math.min(sprite.getWidth(), sprite.getHeight());
        float scalar = size / shortSide;
        sprite.setPosition(position.x - sprite.getWidth() * 0.5f, position.y - sprite.getHeight() * 0.5f);
        sprite.setRotation(rotation);
        sprite.setScale(scalar);
        sprite.draw(batch);
    }


    /**
     * The drawShapes method draws any shapes to the screen, using the Kroy instance's shape renderer. */
    public void drawShapes() { }

    /**
     * Returns whether a Vector is inside, i.e. collides with the entity.
     * @param v The vector */
    public boolean collides(Vector2 v) {
        return position.dst2(v) < size * size;
    }

    /**
     * When called, the entity will be removed from the game during the next tick. */
    public void removeSelf() {
        toBeRemoved = true;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Kroy getKroy() {
        return kroy;
    }

    public boolean isToBeRemoved() {
        return toBeRemoved;
    }

    public float getSize() {
        return size;
    }

    public float getRotation() {
        return rotation;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
