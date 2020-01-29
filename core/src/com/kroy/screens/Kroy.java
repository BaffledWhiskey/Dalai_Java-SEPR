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
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.Tools;
import com.kroy.entities.*;
import com.kroy.Controller;

import java.io.Reader;
import java.nio.file.Paths;
import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Kroy implements Screen, InputProcessor {

    private Controller controller;
    private PauseOverlay pauseOverlay;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private ArrayList<Entity> entities;
    private FireEngine selectedFireEngine;
    private HashMap<Class, ArrayList<Entity>> entityTypes;
    private AssetManager assetManager;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;


    public Kroy(final Controller controller) {
        this.controller = controller;
        pauseOverlay = new PauseOverlay(this);
        camera = new OrthographicCamera();
        camera.position.set(500f, 500f, 1f);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        font = new BitmapFont();
        entities = new ArrayList<Entity>();
        entityTypes = new HashMap<>();
        assetManager = new AssetManager();
    }

    /**
     * Adds an Entity to the game.
     * @param entity The Entity that is to be added */
    public void addEntity(Entity entity) {
        entities.add(entity);
        if (!entityTypes.containsKey(entity.getClass()))
            entityTypes.put(entity.getClass(), new ArrayList<Entity>());
        entityTypes.get(entity.getClass()).add(entity);
    }

    /**
     * Returns all Entites of a certain class. */
    public ArrayList<Entity> getEntitiesOfType(Class type) {
        return entityTypes.get(type);
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
     * Load a level from a JSON file. */
    public void loadLevel(String levelFile) {
        Reader reader = Gdx.files.internal(Paths.get(levelFile).toString()).reader();
        JsonValue levelJson = new JsonReader().parse(reader);

        // Load the map from the according .tmx file
        map = new TmxMapLoader().load(levelJson.getString("mapFile"));
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f);

        // Lead all entities from the level file
        JsonValue entityJsonObj = levelJson.get("entities");

        JsonValue fireEngineJsonArray = entityJsonObj.get("FireEngine");
        for (JsonValue json : fireEngineJsonArray) {
            FireEngine entity = new FireEngine(this, json);
            addEntity(entity);
        }

        JsonValue fireStationJsonArray = entityJsonObj.get("FireStation");
        for (JsonValue json : fireStationJsonArray) {
            FireStation entity = new FireStation(this, json);
            addEntity(entity);
        }

        JsonValue alienJsonArray = entityJsonObj.get("Alien");
        for (JsonValue json : alienJsonArray) {
            Alien entity = new Alien(this, json);
            addEntity(entity);
        }

        JsonValue fortressJsonArray = entityJsonObj.get("Fortress");
        for (JsonValue json : fortressJsonArray) {
            Fortress entity = new Fortress(this, json);
            addEntity(entity);
        }
    }

    /**
     * Renders the bullet at its required position
     * @param deltaTime The offset to update the bullet by
     */
    @Override
    public void render(float deltaTime){
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        handleWASDInput();

        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.begin();
        shapeRenderer.begin();

        for (Entity entity : entities)
            entity.update(deltaTime);

        shapeRenderer.end();
        batch.end();
    }

    private void handleWASDInput() {
        if (selectedFireEngine == null)
            return;

        float x = 0;
        float y = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            x += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            x -= 1;

        selectedFireEngine.setVelocity(new Vector2(x, y));
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

        loadLevel("levels/level1.json");
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
        // If you click on a fire Engine, set the selectedFireEngine to the according FireEngine.
        // If no fire engine is selected, set selectedFireEngine to null.
        Ray pickRay = camera.getPickRay(screenX, screenY);
        Vector2 pos = new Vector2(pickRay.origin.x, pickRay.origin.y);

        selectedFireEngine = null;
        for (Entity entity : getEntitiesOfType(FireEngine.class)) {
            FireEngine fireEngine = (FireEngine) entity;
            if (fireEngine.collides(pos)) {
                selectedFireEngine = fireEngine;
                break;
            }
        }
        return true;
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