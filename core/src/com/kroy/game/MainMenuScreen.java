package com.kroy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class MainMenuScreen implements Screen {

    KROY game = new KROY();

    Texture playButtonActive;
    Texture playButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;

    public MainMenuScreen(KROY game){
        this.game = game;
        playButtonActive = new Texture("playButtonActive.png");
        playButtonInactive = new Texture("playButton.png");
        exitButtonActive = new Texture("exitButtonActive.png");
        exitButtonInactive = new Texture("exitButton.png");
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        //game.batch.draw(img, x, y);
        game.batch.end();

        game.batch.draw(exitButtonActive, 100, 100);

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
