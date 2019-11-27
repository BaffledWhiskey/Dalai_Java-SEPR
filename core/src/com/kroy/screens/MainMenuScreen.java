package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.game.KROY;

public class MainMenuScreen implements Screen {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 150;
    private static final int x = GameScreen.WIDTH/2;
    private static final int EXIT_BUTTON_Y = 100;
    private static final int PLAY_BUTTON_Y = 300;
    private static final int KROY_LOGO_Y = 500;
    private static final int LOGO_WIDTH = 400;
    private static final int LOGO_HEIGHT = 200;



    KROY game = new KROY();

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture kroyLogo;
    OrthographicCamera camera;

    public MainMenuScreen(KROY game){
        this.game = game;
        playButtonActive = new Texture("playButtonActive.png");
        playButtonInactive = new Texture("playButton.png");
        exitButtonActive = new Texture("exitButtonActive.png");
        exitButtonInactive = new Texture("exitButton.png");
        kroyLogo = new Texture("KROY_logo.png");
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(kroyLogo, x - LOGO_WIDTH/2, KROY_LOGO_Y, LOGO_WIDTH, LOGO_HEIGHT);

        if(Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x - BUTTON_WIDTH && GameScreen.HEIGHT - Gdx.input.getY() < PLAY_BUTTON_Y + BUTTON_HEIGHT && GameScreen.HEIGHT - Gdx.input.getY() > PLAY_BUTTON_Y){
            game.batch.draw(playButtonActive, (x) - BUTTON_WIDTH/2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new GameScreen(game));
            }
        }else {
            game.batch.draw(playButtonInactive, (x) - BUTTON_WIDTH / 2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        if(Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x - BUTTON_WIDTH && GameScreen.HEIGHT - Gdx.input.getY() < EXIT_BUTTON_Y + BUTTON_HEIGHT && GameScreen.HEIGHT - Gdx.input.getY() > EXIT_BUTTON_Y){
            game.batch.draw(exitButtonActive, (x) - BUTTON_WIDTH/2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        }else {
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
    public void hide() { }

    @Override
    public void dispose() {

    }
}
