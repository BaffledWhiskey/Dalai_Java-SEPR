package com.kroy.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kroy.entities.FireEngine;
import com.kroy.game.KROY;
import com.kroy.game.Point;
import javafx.scene.input.InputEvent;
import java.util.ArrayList;

import javax.swing.*;


public class GameScreen implements Screen, InputProcessor {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Texture texture;
    private Sprite test;
    private Sprite player;
    private SpriteBatch sb;
    private FireEngine engine;

    public static int WIDTH = 1080;
    public static int HEIGHT = 900;


    final KROY game;

    public GameScreen(final KROY game) {
        this.game = game;

        Texture img = new Texture("KROY_logo.png");
        Sprite test = new Sprite(img);

        //defining the camera and map characteristics
        map = new TmxMapLoader().load("maps/2/Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 2f); //second parameter is the unit scale (defaulted to 1), 1 pixel = 1 world unit/
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.setToOrtho(false, WIDTH, HEIGHT);
        test.setPosition(WIDTH - test.getWidth()/2, HEIGHT - test.getHeight()/2);
        Gdx.input.setInputProcessor(this);

        //loaded the test player model and drawing it onto the middle of the screen, section for defining the player characteristics
        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("Sprites/playerTest.png"));
        player = new Sprite(texture);
        player.setOrigin(52,54);
        player.setPosition(WIDTH - test.getWidth()/2, HEIGHT - test.getHeight()/2); //draws the player at a position on the screen, not the map.
        //Links to fire engine class
        Point p = new Point(Math.round(WIDTH - test.getWidth()/2), Math.round(HEIGHT - test.getHeight()/2));
        engine = new FireEngine(50,50,50,50,p);
    }



    @Override
    public void render(float delta){
        //Cheap and dirty way of moving the map around, doesn't need to be permanent
        //*********************************
        if(Gdx.input.isTouched()) {
            //float x = Gdx.input.getDeltaX(); commented out and changed the camera alterations to adjust for map scaling
            float y = Gdx.input.getDeltaY();
            camera.position.add(0, y, 0);
            camera.update();
        }
        //*********************************
        Gdx.gl.glClearColor(0,0,0,1) ;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setMap(map);
        renderer.setView(camera);
        renderer.render();

        //section for drawing the actual sprite here
        //I MANAGED TO LOCK THE SPRITE TO THE MAP AND NOT THE SCREEN!!!!!!!
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        player.draw(sb);
        sb.end();
        //Draws a range box
        ArrayList list = new ArrayList<FireEngine>();
        engine.drawBox(list, camera);

        //If you want smooth movement can use this, don't know how to get it to work with interrupts
        //Note: the box doesn't move smoothly with the player
        //***********************************************************************************************************
        /**
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.translateX(200f * Gdx.graphics.getDeltaTime());
            engine.updatePosition(new Point((int) (engine.position.x + 200 * Gdx.graphics.getDeltaTime()), engine.position.y));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.translateX(-1*(200f * Gdx.graphics.getDeltaTime()));
            engine.updatePosition(new Point(-1*(int) (engine.position.x + 200 * Gdx.graphics.getDeltaTime()), engine.position.y));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.translateY((200f * Gdx.graphics.getDeltaTime()));
            engine.updatePosition(new Point(engine.position.x, (int) (engine.position.y + 200 * Gdx.graphics.getDeltaTime())));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.translateY(-1*(200f * Gdx.graphics.getDeltaTime()));
            engine.updatePosition(new Point(engine.position.x, -1*(int) (engine.position.y + 200 * Gdx.graphics.getDeltaTime())));
        }
         **/
        //****************************************************************************************************************

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
        //Moved this to keyDown so sprite moves when key is depressed not released

        if(keycode == Input.Keys.LEFT){
            player.translateX(-10f);
            engine.updatePosition(new Point(engine.position.x - 10,engine.position.y));
        }
        if(keycode == Input.Keys.RIGHT){
            player.translateX(10f);
            engine.updatePosition(new Point(engine.position.x + 10,engine.position.y));

        }
        if (keycode == Input.Keys.UP){
            player.translateY(10f);
            engine.updatePosition(new Point(engine.position.x,engine.position.y + 10));

        }
        if (keycode == Input.Keys.DOWN){
            player.translateY(-10f);
            engine.updatePosition(new Point(engine.position.x,engine.position.y - 10));
        }

        return true;
    }


    @Override
    public boolean keyUp(int keycode) {

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
        //This should work to zoom the map in and out with scroll wheel, the mouse input is a bit off though

        Vector3 screenCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 worldCoordsBefore = camera.unproject(new Vector3(screenCoords));
        camera.zoom += amount * camera.zoom * 0.1f;
        camera.update();
        Vector3 worldCoordsAfter = camera.unproject(new Vector3(screenCoords));
        Vector3 diff = new Vector3(worldCoordsAfter).sub(worldCoordsBefore);
        camera.position.sub(diff);
        camera.update();
        return true;


    }


}