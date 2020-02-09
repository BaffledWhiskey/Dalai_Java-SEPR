package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public abstract class Movable extends Unit {

    float movementSpeed;
    Vector2 velocity;
    boolean checkCollisions;

    public Movable(Kroy gameScreen, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, boolean checkCollisions) {
        super(gameScreen, position, size, sprite, health);
        this.movementSpeed = movementSpeed;
        this.velocity = new Vector2(0, 0);
        this.checkCollisions = checkCollisions;
    }

    /**
     * Builds a Moveable from a JsonValue object. */
    public Movable(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        movementSpeed = json.getFloat("movementSpeed");
        checkCollisions = true;
        this.velocity = new Vector2(0, 0);
    }

    @Override
    public void update(float timeDelta) {
        Vector2 adjustedVelocity = getAdjustedVelocity(timeDelta);

        // Set the correct rotation based on the Movable's velocity
        float velocityLength = adjustedVelocity.len();
        if (velocityLength != 0) {
            Vector2 rotationVector = new Vector2(velocity.len(), 0).rotate(rotation);
            rotation = rotationVector.add(velocity).angle();
        }

        // Check for collisions on the tile map
        Vector2 nextPosition = position.cpy().add(adjustedVelocity);

        if (isValidPosition(nextPosition)) {
            position = nextPosition;
        }

        super.update(timeDelta);
    }

    /**
     * Returns the position that the Movable will have in the next tick given its current velocity. */
    Vector2 getAdjustedVelocity(float timeDelta) {
        return velocity.cpy().setLength(timeDelta * movementSpeed);
    }

    private boolean isValidPosition(Vector2 pos) {
        if (pos.x < 0 || pos.y < 0)
            return false;
        if (!checkCollisions)
            return true;
        TiledMapTile nextPositionTile = kroy.getTile(pos);
        if (nextPositionTile == null)
            return false;
        return !nextPositionTile.getProperties().get("blocked", Boolean.class);
    }

    boolean nextPositionIsValid(float timeDelta) {
        Vector2 adjustedVelocity = getAdjustedVelocity(timeDelta);
        Vector2 nextPosition = position.cpy().add(adjustedVelocity);
        return isValidPosition(nextPosition);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
