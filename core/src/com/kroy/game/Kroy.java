package com.kroy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kroy.entities.Entity;
import com.kroy.screens.MainMenuScreen;
import java.util.ArrayList;

/**
 * The KROY class sets out the preliminaries for the game
 */
public class Kroy extends Game {



    public void create() {
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {}
}
