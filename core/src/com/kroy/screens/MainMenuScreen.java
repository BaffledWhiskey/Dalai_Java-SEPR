package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.KROY;

/**
 * This screen is shown when the user first enters the app
 */
public class MainMenuScreen implements Screen {

    private static final int BUTTON_WIDTH = 225;
    private static final int BUTTON_HEIGHT = 100;
    private static final int x = GameScreen.WIDTH/2;
    private static final int EXIT_BUTTON_Y = 50;
    private static final int PLAY_BUTTON_Y = 175;
    private static final int KROY_LOGO_Y = 400;
    private static final int LOGO_WIDTH = 600;
    private static final int LOGO_HEIGHT = 300;



    KROY game = new KROY();

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture kroyLogo;
    OrthographicCamera camera;

    public MainMenuScreen(KROY game){
        this.game = game;
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

        game.batch.begin();
        //Draws the logo
        game.batch.draw(kroyLogo, x - LOGO_WIDTH/2, KROY_LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);

        //If the 'play' button is scrolled over, display the 'active' state, else display the 'inactive' state
        if(Gdx.input.getX() < x + BUTTON_WIDTH/2 && Gdx.input.getX() > x - BUTTON_WIDTH/2 && GameScreen.HEIGHT
                - Gdx.input.getY() < PLAY_BUTTON_Y + BUTTON_HEIGHT
                && GameScreen.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y){
            game.batch.draw(playButtonActive, (x) - BUTTON_WIDTH/2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            //If the button is clicked, begin a game
            if(Gdx.input.isTouched()){
                game.setScreen(new GameScreen(game));
            }
        }
        //Else display the 'inactive' sprite
        else {
            game.batch.draw(playButtonInactive, (x) - BUTTON_WIDTH / 2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        //If the 'exit' button is scrolled over, display the 'active' state
        if(Gdx.input.getX() < x + BUTTON_WIDTH/2 && Gdx.input.getX() > x - BUTTON_WIDTH/2 && GameScreen.HEIGHT
                - Gdx.input.getY() < EXIT_BUTTON_Y + BUTTON_HEIGHT
                && GameScreen.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y){
            game.batch.draw(exitButtonActive, (x) - BUTTON_WIDTH/2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            //If the button is clicked, exit the app
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }
        //Else display the 'inactive' sprite
        else {
            game.batch.draw(exitButtonInactive, (x) - BUTTON_WIDTH / 2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
        game.batch.end();

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
