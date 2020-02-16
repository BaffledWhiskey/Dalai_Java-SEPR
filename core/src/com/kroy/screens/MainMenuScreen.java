package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.Controller;

public class MainMenuScreen implements Screen{

    Controller controller;
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;

    String[] levelFiles = new String[]{"levels/level1.json"}; // Add new level files here

    public MainMenuScreen(Controller controller)
    {
        this.controller = controller;
        atlas = new TextureAtlas("skin/uiSkin.atlas");
        skin = new Skin(Gdx.files.internal("skin/uiSkin.json"), atlas);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.app.getGraphics().getWidth() / 2, Gdx.app.getGraphics().getHeight() / 2, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        //Stage should control input:
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.center();

        // Add Kroy label
        Texture kroyLogoTexture = new Texture("KROY_logo.png");
        Image kroyLogo = new Image(kroyLogoTexture);
        mainTable.add(kroyLogo);
        mainTable.row();

        if (controller.getKroy() != null) {
            if (controller.getKroy().hasWon()) {
                Label hasWonLabel = new Label("You won!", skin);
                mainTable.add(hasWonLabel);
                mainTable.row();
            } else if (controller.getKroy().hasLost()) {
                Label hasLostLabel = new Label("You Lost!", skin);
                mainTable.add(hasLostLabel);
                mainTable.row();
            }
        }

        //Create and add level buttons
        TextButton levelButtons[] = new TextButton[levelFiles.length];
        for (int i = 0; i < levelFiles.length; i++) {
            levelButtons[i] = new TextButton("Play Level " + (i + 1), skin);
            final int finalI = i;
            levelButtons[i].addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.startGame(levelFiles[finalI]);
                }
            });
            //Add buttons and labels to table
            mainTable.add(levelButtons[i]).pad(20);
            mainTable.row();
        }

        // Create and add Resume button if needed
        if (controller.getKroy() != null && !(controller.getKroy().hasLost() || controller.getKroy().hasWon())) {
            TextButton resumeButton = new TextButton("Resume", skin);
            resumeButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.resumeGame();
                }
            });
            mainTable.add(resumeButton).pad(20);
            mainTable.row();
        }

        // Create and add quit button
        TextButton quitButton = new TextButton("Quit", skin);
        quitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        mainTable.add(quitButton).pad(20);

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        atlas.dispose();
    }
}