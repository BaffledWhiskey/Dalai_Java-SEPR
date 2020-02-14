package com.kroy.miniGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.BaseGame;
import com.kroy.Controller;
import com.kroy.entities.FireStation;

import java.util.ArrayList;

public class MiniGame extends BaseGame {

    FireStation fireStation;
    Sprite fireStationSprite;
    ArrayList<Invader> invaders;
    ArrayList<Invader> toBeRemoved;
    Vector2 mousePosition;

    float hammerSize;
    float hammerSize2;
    float invaderKillRadius2;
    JsonValue invaderJson;
    float duration;
    float time = 0f;
    float spawnRate;

    public MiniGame(Controller controller, FireStation fireStation) {
        super(controller);

        this.fireStation = fireStation;
        float difficulty = fireStation.getMiniGameDifficulty();

        JsonValue miniGameJson = fireStation.getMiniGameJson();
        duration = miniGameJson.getFloat("duration");
        spawnRate = miniGameJson.getFloat("spawnRate") * difficulty;
        hammerSize = miniGameJson.getFloat("hammerSize");
        hammerSize2 = hammerSize * hammerSize;
        invaderJson = miniGameJson.get("invaderType");

        fireStationSprite = new Sprite(fireStation.getSprite());
        fireStationSprite.setPosition(0, 0);
        float size = Math.max(fireStationSprite.getHeight(), fireStationSprite.getWidth());
        invaderKillRadius2 = invaderJson.getFloat("killRadius");
        fireStationSprite.setScale(invaderKillRadius2 / size);
        invaderKillRadius2 *= invaderKillRadius2;

        invaders = new ArrayList<>();
        toBeRemoved = new ArrayList<>();

        // Initialize some initial invaders
        for (int i = 0; i < miniGameJson.getInt("initialInvaderQty"); ++i)
            invaders.add(new Invader(this, invaderJson));
        mousePosition = new Vector2(0, 0);
    }

    public void onWin() {
        controller.resumeGame();
    }

    public void onLoose() {
        fireStation.removeSelf();
        controller.resumeGame();
    }

    @Override
    public void render(float deltaTime) {
        super.render(deltaTime);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Win condition:
        time += deltaTime;
        if (time > duration && invaders.isEmpty())
            onWin();

        // Spawn new invaders:
        if (time < duration && Math.random() < spawnRate * deltaTime)
            invaders.add(new Invader(this, invaderJson));

        // Update invaders
        for (Invader invader : invaders)
            invader.update(deltaTime);

        // Render invaders
        batch.begin();
        for (Invader invader : invaders)
            invader.render();
        fireStationSprite.draw(batch);
        batch.end();

        // Garbage collect dead invaders
        invaders.removeAll(toBeRemoved);
        toBeRemoved.clear();

        // Draw hammer
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        else
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(mousePosition.x, mousePosition.y, hammerSize);
        shapeRenderer.end();
    }

    /**
     * Handle clicks, i.e. kill invaders that are clicked on. */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Do not allow the player to just hit the centre to avoid loosing
        if (mousePosition.len2() < invaderKillRadius2)
            return true;

        for (Invader invader : invaders)
            if (invader.position.dst(mousePosition) < hammerSize + invader.size)
                toBeRemoved.add(invader);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Ray pickRay = camera.getPickRay(screenX, screenY);
        mousePosition.set(pickRay.origin.x, pickRay.origin.y);
        return true;
    }

    public Vector2 getMousePosition() {
        return mousePosition;
    }
}
