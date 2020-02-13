package com.kroy.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.BaseGame;
import com.kroy.Tools;
import com.kroy.entities.*;
import com.kroy.Controller;

import java.io.Reader;
import java.nio.file.Paths;
import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Kroy extends BaseGame {

    private KroyHUD kroyHUD;

    private ArrayList<Entity> entities;
    private HashMap<Class, ArrayList<Entity>> entityTypes;
    private FireEngine selectedFireEngine;
    private boolean isUpdating = false;
    private ArrayList<Entity> toBeAdded;
    private FireStation waitingMiniGameFireStation;

    private float time;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;


    public Kroy(Controller controller, String levelFile) {
        super(controller);
        toBeAdded = new ArrayList<>();
        entities = new ArrayList<>();
        entityTypes = new HashMap<>();
        time = 0f;
        kroyHUD = new KroyHUD(controller);
        // Load level from JSON file
        loadLevel(levelFile);
        selectNextFireEngine();
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
    }

    @Override
    public void show() {
        waitingMiniGameFireStation = null;
    }

    /**
     * Updates and renders the scene.
     * @param deltaTime The difference in time to the last call to render
     */
    @Override
    public void render(float deltaTime){
        super.render(deltaTime);

        time += deltaTime;

        // Let the camera follow the selected FireTruck
        if (selectedFireEngine != null) {
            Vector2 fireEnginePosition = selectedFireEngine.getPosition().cpy();
            Vector2 cameraPosition = new Vector2(camera.position.x, camera.position.y);
            Vector2 delta = fireEnginePosition.sub(cameraPosition);
            float length = deltaTime * delta.len2() * 0.005f;
            cameraPosition.add(delta.setLength(length));
            camera.position.set(cameraPosition.x, cameraPosition.y, camera.position.z);
        }

        mapRenderer.setView(camera);
        mapRenderer.render();

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

        getKroyHUD().update(deltaTime);

        if (waitingMiniGameFireStation != null)
            controller.startMiniGame(waitingMiniGameFireStation);
    }

    /**
     * Add all the waiting entities to the game. */
    private void addPendingEntities() {
        for (Entity entity : toBeAdded)
            addEntity(entity);
        toBeAdded.clear();
    }

    public void startMiniGame(FireStation fireStation) {
        waitingMiniGameFireStation = fireStation;
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

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SHIFT_LEFT) {
            selectNextFireEngine();
            return true;
        }
        return false;
    }

    private void selectNextFireEngine() {
        ArrayList<Entity> fireEngines = getEntitiesOfType(FireEngine.class);
        if (selectedFireEngine == null) {
            setSelectedFireEngine((FireEngine) fireEngines.get(0));
            return;
        }
        for (int i = 0; i < fireEngines.size(); i++) {
            FireEngine fireEngine = (FireEngine) fireEngines.get(i);
            if (fireEngine.equals(selectedFireEngine)) {
                setSelectedFireEngine((FireEngine)fireEngines.get((i + 1) % fireEngines.size()));
                break;
            }
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // If you click on a fire Engine, set the selectedFireEngine to the according FireEngine.
        // If no fire engine is selected, set selectedFireEngine to null.
        Ray pickRay = camera.getPickRay(screenX, screenY);
        Vector2 pos = new Vector2(pickRay.origin.x, pickRay.origin.y);

        for (Entity entity : getEntitiesOfType(FireEngine.class)) {
            FireEngine fireEngine = (FireEngine) entity;
            if (fireEngine.collides(pos)) {
                setSelectedFireEngine(fireEngine);
                break;
            }
        }
        return true;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
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

    public KroyHUD getKroyHUD() {
        return kroyHUD;
    }

    public float getTime() {
        return time;
    }

    @Override
    public InputProcessor getInputProcessor() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(kroyHUD.stage);
        multiplexer.addProcessor(this);
        return multiplexer;
    }
}