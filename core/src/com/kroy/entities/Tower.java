package com.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.Point;

/**
 * A Tower cannot move but can perform special actions when an entity is in range of it
 */
public class Tower extends Entity{
    Dimensions dimensions;

    /**
     *
     * @param dimensions The height and width of the tower
     * @param health The current health of the tower
     * @param range The maximum range of the tower
     * @param position The position of the tower on the map
     */
    public Tower(Dimensions dimensions, int health, int range, Point position, Texture img) {
        super(health, range, position, img);
        this.dimensions = dimensions;
    }

}
