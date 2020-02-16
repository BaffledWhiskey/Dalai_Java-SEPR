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

    /**
     * The constructor for a Movable that can be used for testing. Note that this is not the constructor that is used in
     * the actual game.
     *
     * @param kroy The Kroy instance in which the Movable lives
     * @param position The Movable's position within the game world
     * @param size The Movable's size. It is used for rendering
     * @param sprite The Movable's Sprite
     * @param health The Movable's (maximum) health. It always starts out with full health
     * @param movementSpeed The Movable's movement speed
     * @param checkCollisions Whether the movable will be affected by water tiles, buildings etc.
     */
    public Movable(Kroy kroy, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, boolean checkCollisions) {
        super(kroy, position, size, sprite, health);
        this.movementSpeed = movementSpeed;
        this.velocity = new Vector2(0, 0);
        this.checkCollisions = checkCollisions;
    }

    /**
     * Builds a Movable from a JsonValue object. This is the constructor that is used in the game.
     *
     * @param kroy The Kroy instance in which the Movable lives
     * @param json The JsonObject instance that holds the information according to which the Movable is initialized. */
    public Movable(Kroy kroy, JsonValue json) {
        super(kroy, json);
        movementSpeed = json.getFloat("movementSpeed");
        checkCollisions = true;
        this.velocity = new Vector2(0, 0);
    }


    /**
     * Updates the Movable.
     *
     * @param deltaTime The amount of time that has passed since the last tick
     */
    @Override
    public void update(float deltaTime) {
        Vector2 adjustedVelocity = getAdjustedVelocity(deltaTime);

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

        super.update(deltaTime);
    }

    /**
     * Returns the position that the Movable will have in the next tick given its current velocity.
     *
     * @param timeDelta The amount of time that has passed since the last tick
     * @return The vector by which the movale will move this tick
     * */
    Vector2 getAdjustedVelocity(float timeDelta) {
        return velocity.cpy().setLength(timeDelta * movementSpeed);
    }


    /**
     * Checks whether  given position is a valid position for the movable. E.g. a position that is on a water tile
     * would return false.
     *
     * @param pos The position that is to be tested
     * @return whether  given position is a valid position for the movable
     */
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

    /**
     * @param timeDelta The amount of time that has passed since the last tick
     * @return whether the next poition of the movable will be valid according to the isValidPosition method
     */
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
