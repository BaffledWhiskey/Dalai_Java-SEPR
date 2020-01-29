package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.Controller;

/**
 * This screen is shown when the user first enters the app
 */
public class MainMenuScreen implements Screen {

    private static final int BUTTON_WIDTH = 225;
    private static final int BUTTON_HEIGHT = 100;
    private static final int x = Gdx.graphics.getWidth()/2;
    private static final int EXIT_BUTTON_Y = 50;
    private static final int PLAY_BUTTON_Y = 175;
    private static final int KROY_LOGO_Y = 400;
    private static final int LOGO_WIDTH = 600;
    private static final int LOGO_HEIGHT = 300;

    Controller game;
    SpriteBatch spriteBatch;

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture kroyLogo;
    OrthographicCamera camera;

    public MainMenuScreen(Controller game){
        this.game = game;
        spriteBatch = new SpriteBatch();
        playButtonActive = new Texture("MainMenuScreen/playButtonActive.png");
        playButtonInactive = new Texture("MainMenuScreen/playButton.png");
        exitButtonActive = new Texture("MainMenuScreen/exitButtonActive.png");
        exitButtonInactive = new Texture("MainMenuScreen/exitButton.png");
        kroyLogo = new Texture("KROY_logo.png");
    }



    @Override
    public void show() {

    }

    /**
     * Renders the Main Menu screen
     * @param delta A parameter required to render this screen
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        //Draws the logo
        spriteBatch.draw(kroyLogo, x - LOGO_WIDTH/2, KROY_LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);

        //If the 'play' button is scrolled over, display the 'active' state, else display the 'inactive' state
        if(Gdx.input.getX() < x + BUTTON_WIDTH/2 && Gdx.input.getX() > x - BUTTON_WIDTH/2 && Gdx.graphics.getHeight()
                - Gdx.input.getY() < PLAY_BUTTON_Y + BUTTON_HEIGHT
                && Gdx.graphics.getHeight() - Gdx.input.getY() > PLAY_BUTTON_Y){
            spriteBatch.draw(playButtonActive, (x) - BUTTON_WIDTH/2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            //If the button is clicked, begin a game
            if(Gdx.input.isTouched()){
                game.setScreen(new Kroy(game));
            }
        }
        //Else display the 'inactive' sprite
        else {
            spriteBatch.draw(playButtonInactive, (x) - BUTTON_WIDTH / 2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        //If the 'exit' button is scrolled over, display the 'active' state
        if(Gdx.input.getX() < x + BUTTON_WIDTH/2 && Gdx.input.getX() > x - BUTTON_WIDTH/2 && Gdx.graphics.getHeight()
                - Gdx.input.getY() < EXIT_BUTTON_Y + BUTTON_HEIGHT
                && Gdx.graphics.getHeight() - Gdx.input.getY() > EXIT_BUTTON_Y){
            spriteBatch.draw(exitButtonActive, (x) - BUTTON_WIDTH/2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            //If the button is clicked, exit the app
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
        //Else display the 'inactive' sprite
        else {
            spriteBatch.draw(exitButtonInactive, (x) - BUTTON_WIDTH / 2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        spriteBatch.end();

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

    @Override
    public void dispose() {
        Gdx.gl.glClearColor(0,0,0,1);
        playButtonActive.dispose();
        playButtonInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        kroyLogo.dispose();
    }
}
