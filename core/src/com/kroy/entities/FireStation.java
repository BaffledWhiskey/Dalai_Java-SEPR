package com.kroy.entities;

import com.kroy.game.Point;

import java.util.Timer;
import java.util.TimerTask;


public class FireStation extends Tower {
    /**
     *
     * @param dimensions The amount of space the station will use
     * @param health The amount of health the station has
     * @param range The range at which the station can refill/repair a Fire Engine
     * @param position The position of the fire station on the map
     */
    public FireStation(int[] dimensions, int health, int range, Point position) {
        super(dimensions,health,range,position);
    }

    /**
     * Repairs and refills a fire engine which is within range
     * @param engine The engine which is to be repaired
     * @param healthChangePerSecond The amount the station can repair the engine per second
     */

    public void repair(final FireEngine engine, final int healthChangePerSecond) {
        Timer t = new Timer();
        //Increases health every second whilst it is less than 100 and is in range
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (engine.health < 100 && inRange(engine)) {
                    engine.health = engine.health + healthChangePerSecond;
                }
            }
        }, 100, 0);
    }
}
