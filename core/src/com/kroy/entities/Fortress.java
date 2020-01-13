package com.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.Point;

public class Fortress extends Tower{
    /**
     * @param dimensions The size of the tower
     * @param health     The current health of the tower
     * @param range      The maximun range of the tower
     * @param position   The position of the tower on the map
     */

    public Fortress(int[] dimensions, int health, int range, Point position, Texture img) {
        super(dimensions, health, range, position, img);
    }

    public int increaseHealth(){
        return health++;
    }



    public void attackFireEngine(FireEngine engine){
        engine.setHealth(engine.getHealth() - 10);
    }

}
