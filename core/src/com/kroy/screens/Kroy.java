package com.kroy.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
    private AssetManager assetManager;

    private ArrayList<Entity> entities;
    private HashMap<Class, ArrayList<Entity>> entityTypes;
    private FireEngine selectedFireEngine;
    private boolean isUpdating = false;
    private ArrayList<Entity> toBeAdded;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;



    public Kroy(final Controller controller) {
        this.controller = controller;
        pauseOverlay = new PauseOverlay(this);
        camera = new OrthographicCamera();
        camera.position.set(0f, 0f, 1f);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        font = new BitmapFont();
        toBeAdded = new ArrayList<>();
        entities = new ArrayList<>();
        entityTypes = new HashMap<>();
        assetManager = new AssetManager();
    }

    /**
     * Adds an Entity to the game.
     * @param entity The Entity that is to be added */
    public void addEntity(Entity entity) {
        if (isUpdating) {
            toBeAdded.add(entity);
            return;
        }

        entities.add(entity);
        if (!entityTypes.containsKey(entity.getClass()))
            entityTypes.put(entity.getClass(), new ArrayList<Entity>());
        entityTypes.get(entity.getClass()).add(entity);
    }

    /**
     * Removes all entites that have the isToBeRemoved flag set. */
    public void garbageCollectEntities() {
        // Collect all entites with the isToBeRemoved flag
        ArrayList<Entity> toBeRemoved = new ArrayList<Entity>();
        for (Entity entity : entities)
            if (entity.isToBeRemoved()) {
                toBeRemoved.add(entity);
                if (entity == selectedFireEngine)
                    setSelectedFireEngine(null);
            }
        entities.removeAll(toBeRemoved);

        for (Class klass : entityTypes.keySet()) {
            ArrayList<Entity> arrayList = entityTypes.get(klass);
            arrayList.removeAll(toBeRemoved);
        }
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
        mapRenderer = new OrthogonalTiledMapRenderer(map, Tools.MAP_UNIT_SCALE);

        // Lead all entities from the level file. This could be made more generic, but for the sake of simplicity we
        // keep it as is.
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

        if (!getEntitiesOfType(FireEngine.class).isEmpty())
            setSelectedFireEngine((FireEngine) getEntitiesOfType(FireEngine.class).get(0));
    }

    /**
     * Updates and renders the scene.
     * @param deltaTime The difference in time to the last call to render
     */
    @Override
    public void render(float deltaTime){
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        // Let the camera follow the selected FireTruck
        if (selectedFireEngine != null) {
            Vector2 fireEnginePosition = selectedFireEngine.getPosition().cpy();
            Vector2 cameraPosition = new Vector2(camera.position.x, camera.position.y);
            Vector2 delta = fireEnginePosition.sub(cameraPosition);
            float length = deltaTime * delta.len2() * 0.005f;
            cameraPosition.add(delta.setLength(length));
            camera.position.set(cameraPosition.x, cameraPosition.y, camera.position.z);
        }

        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);


        // To avoid modification of the entities array whilst iterating over it, we feed all newly added
        // entities into a buffer (toBeAdded) and add them in the next tick (addPendingEntities). We manage deleting
        // entities similarly.
        addPendingEntities();
        isUpdating = true;
        for (Entity entity : entities)
            entity.update(deltaTime);
        isUpdating = false;
        garbageCollectEntities();

        batch.begin();
        for (Entity entity : entities)
            entity.render();
        batch.end();

        // We need to draw all shapes outside the batch.begin context, see:
        // https://stackoverflow.com/questions/30894456/how-to-use-spritebatch-and-shaperenderer-in-one-screen
        for (Entity entity : entities)
            entity.drawShapes();
    }

    /**
     * Add all the waiting entities to the game. */
    private void addPendingEntities() {
        for (Entity entity : toBeAdded)
            addEntity(entity);
        toBeAdded.clear();
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

    public TiledMapTile getTile(Vector2 pos) {
        // There is probably a better way of getting the tile but whatever :)
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        int x = (int) (pos.x / (tiledMapTileLayer.getTileWidth() * Tools.MAP_UNIT_SCALE) - tiledMapTileLayer.getOffsetX());
        int y = (int) (pos.y / (tiledMapTileLayer.getTileHeight() * Tools.MAP_UNIT_SCALE) - tiledMapTileLayer.getOffsetY());
        TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(x, y);
        if (cell == null)
            return null;
        return cell.getTile();
    }

    public TiledMapTile getTile(int x, int y) {
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        return tiledMapTileLayer.getCell(x, y).getTile();
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
        if (keycode == Input.Keys.SHIFT_LEFT) {
            ArrayList<Entity> fireEngines = getEntitiesOfType(FireEngine.class);

            if (selectedFireEngine == null)
                setSelectedFireEngine((FireEngine) fireEngines.get(0));
            else {
                // Select the next fire engine
                for (int i = 0; i < fireEngines.size(); i++) {
                    FireEngine fireEngine = (FireEngine) fireEngines.get(i);
                    if (fireEngine.equals(selectedFireEngine)) {
                        setSelectedFireEngine((FireEngine)fireEngines.get((i + 1) % fireEngines.size()));
                        break;
                    }
                }
            }
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
        // If you click on a fire Engine, set the selectedFireEngine to the according FireEngine.
        // If no fire engine is selected, set selectedFireEngine to null.
        Ray pickRay = camera.getPickRay(screenX, screenY);
        Vector2 pos = new Vector2(pickRay.origin.x, pickRay.origin.y);

        setSelectedFireEngine(null);
        for (Entity entity : getEntitiesOfType(FireEngine.class)) {
            FireEngine fireEngine = (FireEngine) entity;
            if (fireEngine.collides(pos)) {
                setSelectedFireEngine(fireEngine);
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

    public TiledMap getMap() {
        return map;
    }

    private void setSelectedFireEngine(FireEngine fireEngine) {
        if (selectedFireEngine != null) {
            selectedFireEngine.setVelocity(new Vector2(0, 0));
            selectedFireEngine.setAttack(false);
        }
        selectedFireEngine = fireEngine;
    }

    public FireEngine getSelectedFireEngine() {
        return selectedFireEngine;
    }
}