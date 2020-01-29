package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.Kroy;

import java.util.List;

/**
 * This screen is shown when the user 'pauses' the game, i.e. by clicking the 'esc' button
 */

public class PauseOverlay {

    GameScreen gameScreen;

    private boolean paused;

    public PauseOverlay(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {
        return this.paused;
    }

    /**
     * This method is called whenever a new screen is rendered and gamePaused == true;.
     * The method draws the required textures to the screen over the top of the game state. No changes to the game are
     * possible whilst this method is being called
     */
    public void pauseScreen(){
    }

    /**
     * Disposes of the Pause screen assets
     */
    public void dispose() {
    }
}
