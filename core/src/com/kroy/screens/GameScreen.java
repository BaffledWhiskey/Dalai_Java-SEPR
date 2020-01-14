package com.kroy.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Vector3;

import com.kroy.entities.Entity;
import com.kroy.entities.FireEngine;
import com.kroy.entities.FireStation;
import com.kroy.entities.Fortress;
import com.kroy.game.KROY;
import com.kroy.game.Point;


import java.util.ArrayList;


//////////// ANIMATION //////////////////////////////////////////////////////////////////////
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
/////////// ANIMATION //////////////////////////////////////////////////////////////////////

// Testing - FireStation Co-Ords
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class GameScreen implements Screen, InputProcessor {

    private Texture playAgainActive;
    private Texture playAgainInactive;
    private Texture exitButtonActive;
    private Texture exitButtonInactive;
    private Texture kroyLogo;
    private Texture gameOverImage;

    //Parameters for Firestation
    private static final int FIRE_STATION_X = 832;
    private static final int FIRE_STATION_Y = 144;
    private static final int FIRE_STATION_WIDTH = 192;
    private static final int FIRE_STATION_HEIGHT = 96 ;

    private Texture fireStationTexture;
    private FireStation fireStation;

    // Parameters for Fortress 1
    private static final int FORTRESS_1_X = 776;
    private static final int FORTRESS_1_Y = 688;
    private static final int FORTRESS_1_WIDTH = 80;
    private static final int FORTRESS_1_HEIGHT = 64;

    // Parameters for Fortress 2
    private static final int FORTRESS_2_X = 448;
    private static final int FORTRESS_2_Y = 832;
    private static final int FORTRESS_2_WIDTH = 96;
    private static final int FORTRESS_2_HEIGHT = 96;

    // Parameters for Fortress 3
    private static final int FORTRESS_3_X = 96;
    private static final int FORTRESS_3_Y = 790;
    private static final int FORTRESS_3_WIDTH = 96;
    private static final int FORTRESS_3_HEIGHT = 176;


    private Texture fortressTexture;
    private Fortress fortress1;
    private Fortress fortress2;
    private Fortress fortress3;




    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Texture fireEngineTexture;
    private SpriteBatch sb;
    private FireEngine engine1;
    private FireEngine engine2;
    private FireEngine engine3;
    private ArrayList<FireEngine> fireEngines;
    private ArrayList<Fortress> fortressList;

    private boolean gamePaused;

    public static int WIDTH = 1080;
    public static int HEIGHT = 900;


    private final KROY game;
    private FPSLogger FPS;

    PauseScreen pauseScreen = new PauseScreen();

    // Testing - Fire Statoin Co-Ords
    ShapeRenderer shape = new ShapeRenderer();

    /////// ANIMATION ////////////////////////////
    Animation animation;
    float elapseTime = 0f;
    TextureAtlas textureAtlas;
    SpriteBatch sb1;
    ////// ANIMATION ///////////////////////

    public GameScreen(final KROY game) {
        this.game = game;
        FPS = new FPSLogger();
        pauseScreen.setPaused(false);



        //defining the camera and map characteristics
        map = new TmxMapLoader().load("maps/Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 2f); //second parameter is the unit scale (defaulted to 1), 1 pixel = 1 world unit/
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.setToOrtho(false, WIDTH, HEIGHT);

        // Sets the input processor to this class
        Gdx.input.setInputProcessor(this);

        //loaded the test player model and drawing it onto the middle of the screen, section for defining the player characteristics
        sb = new SpriteBatch();
        fireEngineTexture = new Texture(Gdx.files.internal("Sprites/playerTest.png"));
        //Links to fire engine class
        Point p = new Point(Math.round(WIDTH/2 - fireEngineTexture.getWidth()/2), Math.round(HEIGHT/2 - fireEngineTexture.getHeight()/2));
        engine1 = new FireEngine(50,200,50,100,p, fireEngineTexture); // Instance Number 1
        engine2 = new FireEngine(200, 500, 25, 50,p, fireEngineTexture); // Instance Number 2
        engine3 = new FireEngine(100, 300, 12, 64,p, fireEngineTexture); // Instance Number 3

        //((FireEngine) engine1).toggleState(); // Sets to active for testing
        //Sprite drawable = engine1.drawable;

        //drawable.setOrigin(52,54);


        
        fireEngines = new ArrayList<>();
        fireEngines.add(engine1);
        fireEngines.add(engine2);
        fireEngines.add(engine3);

        // FireStation
        fireStationTexture = new Texture("Sprites/FireStation.png");
        int[] fireStationDimensions = new int[2];
        fireStationDimensions[0] = FIRE_STATION_HEIGHT;
        fireStationDimensions[1] = FIRE_STATION_WIDTH;
        Point fireStationPoint = new Point(FIRE_STATION_X, FIRE_STATION_Y);
        fireStation = new FireStation(fireStationDimensions, 200, 100, fireStationPoint, fireStationTexture);

        //Fortress 1
        fortressTexture = new Texture("Sprites/Fortress1.png");
        int[] fortress1Dimensions = new int[2];
        fortress1Dimensions[0] = FORTRESS_1_WIDTH;
        fortress1Dimensions[1] = FORTRESS_1_HEIGHT;
        Point fortress1Point = new Point(FORTRESS_1_X, FORTRESS_1_Y);
        fortress1 = new Fortress(fortress1Dimensions, 200, 100, fortress1Point, fortressTexture);

        //Fortress 2
        fortressTexture = new Texture("Sprites/Fortress2.png");
        int[] fortress2Dimensions = new int[2];
        fortress2Dimensions[0] = FORTRESS_2_WIDTH;
        fortress2Dimensions[1] = FORTRESS_2_HEIGHT;
        Point fortress2Point = new Point(FORTRESS_2_X, FORTRESS_2_Y);
        fortress2 = new Fortress(fortress2Dimensions, 200, 100, fortress2Point, fortressTexture);

        //Fortress 3
        fortressTexture = new Texture("Sprites/Fortress3.png");
        int[] fortress3Dimensions = new int[2];
        fortress3Dimensions[0] = FORTRESS_3_WIDTH;
        fortress3Dimensions[1] = FORTRESS_3_HEIGHT;
        Point fortress3Point = new Point(FORTRESS_3_X, FORTRESS_3_Y);
        fortress3 = new Fortress(fortress3Dimensions, 200, 100, fortress3Point, fortressTexture);

        fortressList = new ArrayList<>();
        fortressList.add(fortress1);
        fortressList.add(fortress2);
        fortressList.add(fortress3);

        fireEngines = new ArrayList<>();
        fireEngines.add(engine1);
        fireEngines.add(engine2);
        fireEngines.add(engine3);

        ////////ANIMATION //////////////////////////////////////////////////////////////////////
        sb1 = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/JetSprites.atlas"));
        animation = new Animation(1f / 40f, textureAtlas.getRegions());
        //////// ANIMATION //////////////////////////////////////////////////////////////////////
    }



    @Override
    public void render(float delta){

        if(gamePaused){
            pauseScreen.pauseScreen(game);

        }else {

            //Cheap and dirty way of moving the map around, doesn't need to be permanent
            //*********************************
            if (Gdx.input.isTouched()) {
                //float x = Gdx.input.getDeltaX(); commented out and changed the camera alterations to adjust for map scaling
                float y = Gdx.input.getDeltaY();
                camera.position.add(0, y, 0);
                camera.update();
            }
            //*********************************
            FPS.log(); // Prints FPS to Console

            Gdx.gl.glClearColor(19/355f , 103/255f, 44/255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


            renderer.setMap(map);
            renderer.setView(camera);
            renderer.render();

            //section for drawing the actual sprites here
            sb.setProjectionMatrix(camera.combined);
            sb.begin();
            fireStation.drawable.draw(sb);
            engine1.drawable.draw(sb);
            engine2.drawable.draw(sb);
            engine3.drawable.draw(sb);
            fortress1.drawable.draw(sb);
            fortress2.drawable.draw(sb);
            fortress3.drawable.draw(sb);

            sb.end();
            //Draws a range box - Testing Purposes

            engine1.drawBox(fortressList, camera, engine1.drawable, shape);
            engine2.drawBox(fortressList, camera, engine2.drawable, shape);
            engine3.drawBox(fortressList,camera,engine3.drawable, shape);
            //fireStation.drawBox(patrolList, camera,fireStation.drawable);
            fortress1.drawBox(fireEngines,camera, shape);
            fortress2.drawBox(fireEngines,camera,shape);
            fortress3.drawBox(fireEngines,camera, shape);


            //If you want smooth movement can use this, don't know how to get it to work with interrupts
            //***********************************************************************************************************
            // Only moves the fire engine if its currently selected. isActive == true;
            fireEngineMovement();


            //TESTING - FINDING Co-Ords for Fire Station
            drawRect();

            ////////ANIMATION //////////////////////////////////////////////////////////////////////

            //Plays explosion when clicked or space is hit for testing purposes
            sb1.setProjectionMatrix(camera.combined);
            elapseTime += Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                sb1.begin();
                sb1.draw((TextureRegion) animation.getKeyFrame(elapseTime, true), 0, 0, 20, 20, 80, 80, 1, 1, 9, true);
                sb1.end();
            }

            if (Gdx.input.isTouched()) {
                sb1.begin();
                sb1.draw((TextureRegion) animation.getKeyFrame(elapseTime, true), 0, 0, 20, 20, 80, 80, 1, 1, 9, true);
                sb1.end();
            }


            ////// ANIMATION //////////////////////////////////////////////////////////////////////
        //****************************************************************************************************************


            for(Fortress fortress: fortressList){
                for(FireEngine fireEngine: fireEngines){
                    if(fireEngine.inRange(fortress)){
                        fireEngine.attackFortress(fortress);
                    }
                    if(fortress.inRange(fireEngine)){
                        fortress.attackFireEngine(fireEngine);
                    }
                    if(fireEngine.getHealth() <= 0){
                        fireEngine.destroy(animation,elapseTime);
                        //Need a way of deleting this object properly but can't figure it out
                    }
                }
            }


        }
    }

    /**
     * Method that we used to find the co-oridnates of certain places
     */
    private void drawRect(){
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeType.Line);
        shape.setColor(Color.RED);
        shape.rect(48, 702, 96, 176);
        shape.end();


    }



    /** This method is called whenever a new screen is rendered and gamePaused == false;
     * The method is responsible for the movement of each of the fire engines and checking which one should be moved by
     * the players input. It checks through the ArrayList fireEngines and if the current FireEngine objects
     * isActive attribute is True, then applies the needed movement to that FireEngine. It drwas the fire Engine to the
     * new location on the screen.
     *
     * Once added it wil also change which fireEngine fireEngineTexture is needed based on the direction that it is traveling in.
     */
    private void fireEngineMovement(){
        //why is Entity used as the type for fire engine? - if you set it as fire engine you don't need to cast it later
        for (Entity fireEngine : fireEngines) {
            if (((FireEngine)fireEngine).isActive) {
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    if (((FireEngine)fireEngine).isActive) {
                        ((FireEngine) fireEngine).updatePosition("RIGHT");
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    if (((FireEngine)fireEngine).isActive) {
                        ((FireEngine) fireEngine).updatePosition("LEFT");
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    if (((FireEngine)fireEngine).isActive) {
                        ((FireEngine) fireEngine).updatePosition("UP");
                    }
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    if (((FireEngine)fireEngine).isActive) {
                        ((FireEngine) fireEngine).updatePosition("DOWN");
                    }
                }
            }
        }

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
        pauseScreen.setPaused(true);
        System.out.println("Pause Method Called");
    }

    @Override
    public void resume(){
        pauseScreen.setPaused(false);
        System.out.println("Resume Method Called");
    }

    @Override
    public void dispose(){
        map.dispose();
        renderer.dispose();

        //Pause Screen
        playAgainActive.dispose();
        playAgainInactive.dispose();
        exitButtonActive.dispose();
        exitButtonInactive.dispose();
        kroyLogo.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        //Moved this to keyDown so sprite moves when key is depressed not released

//        if(keycode == Input.Keys.LEFT){
//            player.translateX(-10f);
//            engine.updatePosition(new Point(engine.position.x - 10,engine.position.y));
//        }
//        if(keycode == Input.Keys.RIGHT){
//            player.translateX(10f);
//            engine.updatePosition(new Point(engine.position.x + 10,engine.position.y));
//
//        }
//        if (keycode == Input.Keys.UP){
//            player.translateY(10f);
//            engine.updatePosition(new Point(engine.position.x,engine.position.y + 10));
//
//        }
//        if (keycode == Input.Keys.DOWN){
//            player.translateY(-10f);
//            engine.updatePosition(new Point(engine.position.x,engine.position.y - 10));
//        }
//
//        return true;
        if(keycode == Input.Keys.ESCAPE && pauseScreen.isPaused()){
            this.resume();
        }
        else if(keycode == Input.Keys.ESCAPE){
            this.pause();
        }
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
        // If you click on a fire Engine change the isActive State
        // Iterates through all drawn fireEngines on the screen and checks weather the mouse is over the current fireEngine
        // If it is all FireEngines are changed to inActive and the one clicked then changed to Active.
        for(Entity fireEngine: fireEngines){
            if(screenX > fireEngine.position.x - fireEngine.drawable.getWidth()/2 && screenX < fireEngine.position.x
                + fireEngine.drawable.getWidth()/2 && GameScreen.HEIGHT - screenY > fireEngine.position.y - fireEngine.drawable.getHeight()/2
                && GameScreen.HEIGHT - screenY < fireEngine.position.y + fireEngine.drawable.getHeight()/2){

                // Bad way to do it. Almost certainly more efficient way to do it.
                // Changes any active fireEngine to inActive
                for(Entity checkState: fireEngines){
                    if (((FireEngine)checkState).isActive){
                        ((FireEngine)checkState).setState(false);
                    }
                }
                // Makes fireEngine that was just clicked Active
                ((FireEngine) fireEngine).setState(true);
                System.out.println("Fire Engine State Changed:" +((FireEngine) fireEngine).isActive+"\n FireEngine "+ fireEngine + " Is active");
            }
        }

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