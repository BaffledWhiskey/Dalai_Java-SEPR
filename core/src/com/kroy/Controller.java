package com.kroy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kroy.entities.Entity;
import com.kroy.entities.FireStation;
import com.kroy.miniGame.MiniGame;
import com.kroy.screens.Kroy;
import com.kroy.screens.KroyHUD;
import com.kroy.screens.MainMenuScreen;
import java.util.ArrayList;

/**
 * The Controller class sets out the preliminaries for the game
 */
public class Controller extends Game {

    Kroy kroy;
    MiniGame miniGame;
    MainMenuScreen mainMenuScreen;

    public void create() {
        // The first screen we show is the MainMenu screen
        mainMenuScreen = new MainMenuScreen(this);
        setScreen(mainMenuScreen);
    }

    public void dispose() {
        if (kroy != null)
            kroy.dispose();
        if (miniGame != null)
            miniGame.dispose();
        super.dispose();
    }

    public void startMainMenu() {
        setScreen(mainMenuScreen);
    }

    public void startGame(String levelFile) {
        kroy = new Kroy(this, levelFile);
        Gdx.input.setInputProcessor(kroy.getInputProcessor());
        setScreen(kroy);
    }

    public void resumeGame() {
        Gdx.input.setInputProcessor(kroy.getInputProcessor());
        setScreen(kroy);
    }

    public void startMiniGame(FireStation fireStation) {
        miniGame = new MiniGame(this, fireStation);
        Gdx.input.setInputProcessor(miniGame.getInputProcessor());
        setScreen(miniGame);
    }

    public Kroy getKroy() {
        return kroy;
    }
}
