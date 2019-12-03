package com.kroy.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.entities.Entity;
import com.kroy.entities.Bullet;
import com.kroy.screens.MainMenuScreen;
import java.util.ArrayList;


public class KROY extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

    public ArrayList<Entity> entities;


    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}
