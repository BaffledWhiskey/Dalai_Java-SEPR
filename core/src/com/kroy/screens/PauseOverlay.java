package com.kroy.screens;

/**
 * This screen is shown when the user 'pauses' the game, i.e. by clicking the 'esc' button
 */

public class PauseOverlay {

    Kroy gameScreen;

    private boolean paused;

    public PauseOverlay(Kroy gameScreen) {
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
