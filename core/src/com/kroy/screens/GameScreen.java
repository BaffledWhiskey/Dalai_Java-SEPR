package com.kroy.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kroy.entities.*;
import com.kroy.game.Kroy;
import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GameScreen implements Screen, InputProcessor {

    private Kroy kroy;
    private PauseOverlay pauseOverlay;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private ArrayList<Entity> entities;
    private AssetManager assetManager;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;


    public GameScreen(final Kroy kroy) {
        this.kroy = kroy;
        pauseOverlay = new PauseOverlay(this);
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        font = new BitmapFont();
        entities = new ArrayList<Entity>();
        assetManager = new AssetManager();

        //defining the camera and map characteristics
        map = new TmxMapLoader().load("maps/Map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f); //second parameter is the unit scale (defaulted to 1), 1 pixel = 1 world unit/
    }

    /**
     * Adds an Entity to the game.
     * @param entity The Entity that is to be added */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * A wrapper around the central AssetManager.
     * @param path The path to the image to be used as the Texture, relative to the assets folder */
    public Sprite getSprite(String path) {
        if (!assetManager.contains(path)) {
            assetManager.load(path, Texture.class);
            assetManager.finishLoading();
        }
        Texture texture = assetManager.get(path);
        return new Sprite(texture);
    }

    /**
     * Renders the bullet at its required position
     * @param deltaTime The offset to update the bullet by
     */
    @Override
    public void render(float deltaTime){
        mapRenderer.setView(camera);
        mapRenderer.render();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.begin();
        shapeRenderer.begin();

        for (Entity entity : entities)
            entity.update(deltaTime);

        shapeRenderer.end();
        batch.end();
    }

    /**
     * Resizes the screen if it has been adjusted
     * @param width The new width to resize to
     * @param height The new height to resize to
     */
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void show(){
        // Sets the input processor to this class
        Gdx.input.setInputProcessor(this);

        FireEngine fe = new FireEngine(this, new Vector2(100, 100), new Vector2(100, 100), getSprite("Sprites/FireEngine1.png"), 100, 10);
        addEntity(fe);
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
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE && pauseOverlay.isPaused()){
            this.resume();
        }
        else if(keycode == Input.Keys.ESCAPE){
            this.pause();
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
        // If you click on a fire Engine change the isActive State
        // Iterates through all drawn fireEngines on the screen and checks weather the mouse is over the current fireEngine
        // If it is all FireEngines are changed to inActive and the one clicked then changed to Active.
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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

    public SpriteBatch getBatch() {
        return batch;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

}