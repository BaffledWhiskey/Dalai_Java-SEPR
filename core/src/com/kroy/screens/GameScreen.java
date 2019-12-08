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

import java.security.Key;
import java.util.ArrayList;

import javax.swing.*;

//////////// ANIMATION
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
/////////// ANIMATION


public class GameScreen implements Screen, InputProcessor {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Texture texture;
    private Sprite test;
    private Sprite player;
    private SpriteBatch sb;
    private FireEngine engine;
    private FireEngine engine2;
    ArrayList<FireEngine> fireEngines;

    public static int WIDTH = 1080;
    public static int HEIGHT = 900;


    final KROY game;

    /////// ANIMATION
    Animation animation;
    float elapseTime = 0f;
    TextureAtlas textureAtlas;
    SpriteBatch sb1;
    ////// ANIMATION

    public GameScreen(final KROY game) {
        this.game = game;




        //defining the camera and map characteristics
        map = new TmxMapLoader().load("maps/Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 2f); //second parameter is the unit scale (defaulted to 1), 1 pixel = 1 world unit/
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.setToOrtho(false, WIDTH, HEIGHT);

        Gdx.input.setInputProcessor(this);

        //loaded the test player model and drawing it onto the middle of the screen, section for defining the player characteristics
        sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("Sprites/playerTest.png"));
        //player = new Sprite(texture);
        //player.setOrigin(52,54);
        //player.setPosition(WIDTH - test.getWidth()/2, HEIGHT - test.getHeight()/2); //draws the player at a position on the screen, not the map.
        //Links to fire engine class
        Point p = new Point(Math.round(WIDTH - texture.getWidth()/2), Math.round(HEIGHT - texture.getHeight()/2));
        engine = new FireEngine(50,200,50,50,p, texture);
        engine2 = new FireEngine(100, 300, 12, 32,p, texture);
        Sprite boi = engine.drawable;
        System.out.println(boi);
        boi.setOrigin(52,54);
        engine.drawable.setPosition(WIDTH - engine.drawable.getWidth()/2, HEIGHT - engine.drawable.getHeight()/2);
        fireEngines = new ArrayList<>();
        fireEngines.add(engine);
        fireEngines.add(engine2);


        ////////ANIMATION
        sb1 = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/JetSprites.atlas"));
        animation = new Animation(1f / 40f, textureAtlas.getRegions());
        //////// ANIMATION
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



        //This works some of the time, needs fixing - struggling to get the bounds right, might need a click method for Classes or
        for(FireEngine fireEngine: fireEngines){
            if(Gdx.input.getX() > fireEngine.position.x && Gdx.input.getX() < fireEngine.position.x + fireEngine.drawable.getWidth() &&
            GameScreen.HEIGHT-Gdx.input.getY() > fireEngine.position.y && GameScreen.HEIGHT-Gdx.input.getY() < fireEngine.position.y + fireEngine.drawable.getHeight()/2){
                System.out.println("im here ");
                if(Gdx.input.isTouched()) {
                    fireEngine.toggleState();
                }
            }
        }

        renderer.setMap(map);
        renderer.setView(camera);
        renderer.render();

        //section for drawing the actual sprite here
        //I MANAGED TO LOCK THE SPRITE TO THE MAP AND NOT THE SCREEN!!!!!!!
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        engine.drawable.draw(sb);
        engine2.drawable.draw(sb);
        //player.draw(sb);
        sb.end();
        //Draws a range box
        ArrayList list = new ArrayList<FireEngine>();
        engine.drawBox(list, camera, engine.drawable);
        engine2.drawBox(list,camera,engine2.drawable);

        //If you want smooth movement can use this, don't know how to get it to work with interrupts
        //***********************************************************************************************************

        for(FireEngine fireEngine: fireEngines) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                if(fireEngine.isActive) {
                    fireEngine.drawable.translateX((int) (fireEngine.movementSpeed) * Gdx.graphics.getDeltaTime());
                    fireEngine.updatePosition(new Point((int) (fireEngine.drawable.getX()), (int) fireEngine.drawable.getY()));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                if(fireEngine.isActive) {
                    fireEngine.drawable.translateX((int) ((fireEngine.movementSpeed) * -Gdx.graphics.getDeltaTime()));
                    fireEngine.updatePosition(new Point((int) (fireEngine.drawable.getX()), (int) fireEngine.drawable.getY()));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (fireEngine.isActive) {
                    fireEngine.drawable.translateY((int) ((fireEngine.movementSpeed) * Gdx.graphics.getDeltaTime()));
                    fireEngine.updatePosition(new Point((int) (fireEngine.drawable.getX()), (int) fireEngine.drawable.getY()));
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if(fireEngine.isActive) {
                    fireEngine.drawable.translateY((int) ((fireEngine.movementSpeed) * -Gdx.graphics.getDeltaTime()));
                    fireEngine.updatePosition(new Point((int) (fireEngine.drawable.getX()), (int) fireEngine.drawable.getY()));
                }
            }

            ////////ANIMATION
            elapseTime += Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                sb1.begin();
                sb1.draw((TextureRegion) animation.getKeyFrame(elapseTime, true), 0, 0, 20, 20, 50, 50, 1, 1, 9, true);
                sb1.end();
            }
            ////// ANIMATION
        }
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
        /**
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
         **/
        return false;
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