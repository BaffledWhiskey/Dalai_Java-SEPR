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
import com.kroy.screens.Kroy;
import com.kroy.screens.KroyHUD;
import com.kroy.screens.MainMenuScreen;
import java.util.ArrayList;

/**
 * The Controller class sets out the preliminaries for the game
 */
public class Controller extends Game {

    Kroy kroy;
    KroyHUD kroyHUD;

    public void create() {
        setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {}

    public void startGame() {
        kroy = new Kroy(this);
        kroyHUD = new KroyHUD(this);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(kroyHUD.stage);
        multiplexer.addProcessor(kroy);
        Gdx.input.setInputProcessor(multiplexer);

        setScreen((kroy));
    }

    public Kroy getKroy() {
        return kroy;
    }

    public KroyHUD getKroyHUD() {
        return kroyHUD;
    }
}
