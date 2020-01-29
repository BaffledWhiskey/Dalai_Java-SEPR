package com.kroy.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.GameScreen;

/** The Entity class stores any object.
 */
public abstract class Entity{

    protected GameScreen gameScreen;
    protected Vector2 dimension;
    protected Vector2 position;
    private Sprite sprite;

    /**
     * @param position The current position of the Entity
     * @param gameScreen The GameScreen class that owns the Entity
     * @param dimension The dimensions of the entity, i.e. width and height
     */
    public Entity(GameScreen gameScreen, Vector2 position, Vector2 dimension, Sprite sprite){
        this.gameScreen = gameScreen;
        this.position = position;
        this.dimension = dimension;
        this.sprite = sprite;
    }

    /**
     * The update method that is called with every tick of the game. */
    public void update(float timeDelta) {
        render();
    }

    public void render() {
        SpriteBatch batch = gameScreen.getBatch();
        sprite.setPosition(position.x, position.y);
        sprite.setSize(dimension.x, dimension.y);
        sprite.draw(batch);
    }

    public void onClick() {

    }

    /**
     * Returns whether a Vector is inside, i.e. collides with the entity.
     * @param v The vector */
    public boolean collides(Vector2 v) {
        Vector2 p = position;
        Vector2 d = dimension;
        return p.x <= v.x && v.x <= p.x + d.x && p.y <= v.y && v.y <= p.y + d.y;
    }

    /**
     * Draws a bounding box around the entity. Useful for debugging.
     */
    public void drawBoundingBox() {
        ShapeRenderer shapeRenderer = gameScreen.getShapeRenderer();
        shapeRenderer.begin();
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rectLine(position, dimension.cpy().add(position), 1.0f);
        shapeRenderer.end();
    }
}
