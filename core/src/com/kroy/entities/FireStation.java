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
    float miniGameProbability;
    float miniGameDifficulty = 1;

    public FireStation(Kroy gameScreen, Vector2 position, float size, Sprite sprite, float health) {
        super(gameScreen, position, size, sprite, health);
    }

    /**
     * Builds a ForeStation from a JsonValue object. */
    public FireStation(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
        repairRange = json.getFloat("repairRange");
        repairRate = json.getFloat("repairRate");
        refillRate = json.getFloat("refillRate");

        miniGameJson = json.get("miniGame");
        miniGameProbability = miniGameJson.getFloat("probability");
    }

    public void update(float deltaTime) {
        if ((Math.random() < miniGameProbability * deltaTime)) {
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
