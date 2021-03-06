package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.KROY;

import java.util.List;

/**
 * This screen is shown when the user 'pauses' the game, i.e. by clicking the 'esc' button
 */

public class PauseScreen {

    // Parameters for Pause Screen
    private static final int BUTTON_WIDTH = 175;
    private static final int BUTTON_HEIGHT = 50;
    private static final int x = GameScreen.WIDTH/2;
    private static final int EXIT_BUTTON_Y = 50;
    private static final int PLAY_BUTTON_Y = 175;
    private static final int KROY_LOGO_Y = 400;
    private static final int LOGO_WIDTH = 600;
    private static final int LOGO_HEIGHT = 300;

    Texture playAgainActive;
    Texture playAgainInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture kroyLogo;

    private boolean paused;

    public PauseScreen(boolean paused, List<Texture> textures) {
        this.paused = paused;
        this.playAgainActive = textures.get(0);
        this.playAgainInactive = textures.get(1);
        this.exitButtonActive = textures.get(2);
        this.exitButtonInactive = textures.get(3);
        this.kroyLogo = textures.get(4);
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
     * @param game The game which is currently being played
     */
    public void pauseScreen(KROY game){
        game.batch.begin();
        game.batch.draw(kroyLogo, GameScreen.WIDTH/2-LOGO_WIDTH/2, KROY_LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);

        //If the user scrolls over the 'resume' button, switch to the 'active' state
        if(Gdx.input.getX() < GameScreen.WIDTH/2 + BUTTON_WIDTH/2 && Gdx.input.getX() > GameScreen.WIDTH/2 - BUTTON_WIDTH/2 && GameScreen.HEIGHT
                - Gdx.input.getY() < PLAY_BUTTON_Y + BUTTON_HEIGHT
                && GameScreen.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y){
            game.batch.draw(playAgainActive, GameScreen.WIDTH/2-BUTTON_WIDTH/2 , PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            //If it is clicked, resume the game
            if(Gdx.input.isTouched()) {
                setPaused(false);
            }
        }
        //Else display the 'inactive' sprite
        else {
            game.batch.draw(playAgainInactive, GameScreen.WIDTH/2-BUTTON_WIDTH / 2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        if(Gdx.input.getX() < GameScreen.WIDTH/2 + BUTTON_WIDTH/2 && Gdx.input.getX() > GameScreen.WIDTH/2 - BUTTON_WIDTH/2 && GameScreen.HEIGHT
                - Gdx.input.getY() < EXIT_BUTTON_Y + BUTTON_HEIGHT
                && GameScreen.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y){
            game.batch.draw(exitButtonActive, GameScreen.WIDTH/2-BUTTON_WIDTH/2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            //If it is clicked, exit the app
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
        //Else display the 'inactive' sprite
        else {
            game.batch.draw(exitButtonInactive, GameScreen.WIDTH/2-BUTTON_WIDTH / 2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        game.batch.end();

    }

    /**
     * Disposes of the Pause screen assets
     */
    public void dispose() {
        //Pause Screen
        playAgainActive.dispose();
        playAgainInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        kroyLogo.dispose();
    }
}
