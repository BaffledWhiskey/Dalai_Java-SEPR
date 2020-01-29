package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.Controller;
/**
 * The game over screen is shown when the game has either been won or lost
 */
public class GameOverScreen implements Screen {

    private static final int BUTTON_WIDTH = 175;
    private static final int BUTTON_HEIGHT = 50;
    private static final int x = Gdx.graphics.getWidth()/2;
    private static final int EXIT_BUTTON_Y = 50;
    private static final int PLAY_BUTTON_Y = 175;
    private static final int KROY_LOGO_Y = 400;
    private static final int LOGO_WIDTH = 600;
    private static final int LOGO_HEIGHT = 300;

    final Controller game;

    Texture playAgainActive;
    Texture playAgainInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture kroyLogo;
    Texture gameOverImage;
    Texture loseText;
    Texture winText;

    String result;

    OrthographicCamera camera;

    public GameOverScreen(Controller game, String result){
        this.game = game;
        this.result = result;
        playAgainActive = new Texture("GameOverScreen/PlayAgainActive.png");
        playAgainInactive = new Texture("GameOverScreen/PlayAgainInactive.png");
        exitButtonActive = new Texture("GameOverScreen/ExitActive.png");
        exitButtonInactive = new Texture("GameOverScreen/ExitInactive.png");
        kroyLogo = new Texture("KROY_logo.png");
        gameOverImage = new Texture("GameOverScreen/GameOver.png");
        loseText = new Texture("GameOverScreen/Lose.png");
        winText = new Texture("GameOverScreen/Win.png");
    }



    @Override
    public void show() {

    }

    /**
     * Displays the Game Over screen
     * @param delta a parameter used to render the screen
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        this.dispose();
    }

    /**
     * Disposes of the screen when it is no longer needed
     */
    @Override
    public void dispose() {
        Gdx.gl.glClearColor(0,0,0,1);
        playAgainActive.dispose();
        playAgainInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        kroyLogo.dispose();

    }
}
