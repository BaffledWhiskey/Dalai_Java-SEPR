package com.kroy.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.screens.Kroy;

public class FireStation extends Unit {

    float repairRange;
    float repairRate;
    float refillRate;
    JsonValue miniGameJson;
    float miniGameInterval;
    float miniGameCountdown;
    float miniGameDifficulty = 1;

    /**
     * The constructor for a FireStation that can be used for testing. Note that this is not the constructor that is used in
     * the actual game.
     *
     * @param kroy The Kroy instance in which the Alien lives
     * @param position The Alien's position within the game world
     * @param size The Alien's size. It is used for rendering
     * @param sprite The Alien's Sprite
     * @param health The Alien's (maximum) health. It always starts out with full health
     * @param repairRange The range in which fire engines will be repaired and refilled
     * @param repairRate The health per second that nearby fire engines will receive
     * @param refillRate The water per second that nearby fire engines will receive
     * @param miniGameJson The Json for the mini game when the fire station is contested
     * @param miniGameInterval The probability that a mini game starts
     * @param miniGameIntervalOffset The offset by which the mini game interval is started
     */
    public FireStation(Kroy kroy, Vector2 position, float size, Sprite sprite, float health, float repairRange, float repairRate, float refillRate, JsonValue miniGameJson, float miniGameInterval, float miniGameIntervalOffset) {
        super(kroy, position, size, sprite, health);
        this.repairRange = repairRange;
        this.repairRate = repairRate;
        this.refillRate = refillRate;
        this.miniGameJson = miniGameJson;
        this.miniGameInterval = miniGameInterval;
        this.miniGameCountdown = miniGameIntervalOffset;
    }

    /**
     * Builds a FireStation from a JsonValue object. This is the constructor that is used in the game.
     *
     * @param kroy The Kroy instance in which the fire station lives
     * @param json The JsonObject instance that holds the information according to which the fire station is initialized. */
    public FireStation(Kroy kroy, JsonValue json) {
        super(kroy, json);
        repairRange = json.getFloat("repairRange");
        repairRate = json.getFloat("repairRate");
        refillRate = json.getFloat("refillRate");

        miniGameJson = json.get("miniGame");
        miniGameInterval = miniGameJson.getFloat("interval");
        miniGameCountdown = miniGameJson.getFloat("intervalOffset");
    }

    /**
     * Updates the fire engine.
     *
     * @param deltaTime The amount of time that has passed since the last tick */
    public void update(float deltaTime) {

        // Start the minigame with a slight probability
        miniGameCountdown -= deltaTime;
        if (miniGameCountdown < 0) {
            miniGameCountdown = miniGameInterval;
            // With each time that we play the minigame, the difficulty increases
            miniGameDifficulty += 0.25;
            kroy.startMiniGame(this);
        }

        for (Entity entity : getKroy().getEntitiesOfType(FireEngine.class)) {
            FireEngine fireEngine = (FireEngine) entity;
            if (fireEngine.getPosition().dst2(getPosition()) > repairRange * repairRange)
                continue;
            fireEngine.addHealth(repairRate * deltaTime);
            fireEngine.addWater(refillRate * deltaTime);
        }
        super.update(deltaTime);
    }

    public JsonValue getMiniGameJson() {
        return miniGameJson;
    }

    public float getMiniGameDifficulty() {
        return miniGameDifficulty;
    }
}
