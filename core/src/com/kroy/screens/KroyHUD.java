package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.kroy.Controller;

public class KroyHUD implements Disposable {

    public Stage stage;
    public ExtendViewport viewport;
    private BitmapFont white;
    TextureAtlas atlas;
    Skin skin;
    Controller controller;
    OrthographicCamera camera;

    public Table table;
    private Label timeLabel;


    public KroyHUD(final Controller controller){
        this.controller = controller;

        // Set up the Stage and necessary components
        atlas = new TextureAtlas("skin/uiSkin.atlas");
        skin = new Skin(Gdx.files.internal("skin/uiSkin.json"), atlas);
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight(), camera);
        SpriteBatch batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        table = new Table();
        table.top();
        table.setFillParent(true);

        // Create font for our labels
        white = new BitmapFont(Gdx.files.internal("skin/fonts/default.fnt"), false);
        Label.LabelStyle labelStyle = new Label.LabelStyle(white, Color.WHITE);

        // Create the widgets that will be displayed in the HUD
        timeLabel = new Label("Game Time", labelStyle);
        timeLabel.setFontScale(5f);

        TextButton exitButton = new TextButton("QUIT", skin);
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // Add the widgets t the table
        table.add(timeLabel);
        table.row();
        table.add(exitButton);
        table.row();

        // add table to our stage
        stage.addActor(table);

    }

    public void update(float deltaTime) {
        timeLabel.setText(String.valueOf(Math.round(getKroy().getTime() * 10) / 10f));
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        atlas.dispose();
    }

    Kroy getKroy() {
        return controller.getKroy();
    }

}