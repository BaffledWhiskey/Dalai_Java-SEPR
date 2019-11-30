package com.kroy.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.game.KROY;

import javax.swing.*;


public class GameScreen implements Screen, InputProcessor {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    Texture img;
    Sprite test;

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;


    final KROY game;


    public GameScreen(final KROY game) {
        this.game = game;

        Texture img = new Texture("KROY_logo.png");
        Sprite test = new Sprite(img);

        map = new TmxMapLoader().load("maps/2/Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        test.setPosition(WIDTH - test.getWidth()/2, HEIGHT - test.getHeight()/2);
        Gdx.input.setInputProcessor(this);
    }



    @Override
    public void render(float delta){
        //Cheap and dirty way of moving the map around, doesn't need to be permanent
        //*********************************
        if(Gdx.input.isTouched()) {
            float x = Gdx.input.getDeltaX();
            float y = Gdx.input.getDeltaY();
            camera.position.add(-x, y, 0);
            camera.update();
        }
        //*********************************
        Gdx.gl.glClearColor(0,0,0,1) ;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        renderer.setMap(map);
        renderer.setView(camera);
        renderer.render();





        // Sound does play and so map should have been rendered. WHY???
        //Sound sound = Gdx.audio.newSound(Gdx.files.internal("service-bell_daniel_simion.mp3"));
        //sound.play();

    }
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

    }

    @Override
    public void show(){




    }


    @Override
    public void hide(){
        dispose();
    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }

    @Override
    public void dispose(){
        map.dispose();
        renderer.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //this is to get the sprite to move as a test, I think it renders in behind the map though.
        if(keycode == Input.Keys.LEFT){
            test.translateX(-1f);
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Attempt at getting interrupts to work, no clue...
        //camera.position.set(new Vector3((int)camera.position.x + screenX, (int)camera.position.y + screenY, 0.0f));
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
