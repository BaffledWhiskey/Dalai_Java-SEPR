package com.kroy.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.kroy.entities.*;
import com.kroy.game.KROY;
import com.kroy.game.Point;


import java.util.*;


//////////// ANIMATION //////////////////////////////////////////////////////////////////////
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
/////////// ANIMATION //////////////////////////////////////////////////////////////////////

// Testing - FireStation Co-Ords
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;


public class GameScreen implements Screen, InputProcessor {
    // If true render hit boxes, else don't
    boolean testMode = false;

    //Parameters for Bullet
    ArrayList<Bullet> bullets;
    int x;
    int y;
    public Point position;
    public static final float SPEED = 300;

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

    private ArrayList<HitBox> hitBoxes;
    private HitBox box1;
    private HitBox box2;
    private HitBox box3;
    private HitBox box4;
    private HitBox box5;
    private HitBox box6;
    private HitBox box7;
    private HitBox box8;
    private HitBox box9;
    private HitBox box10;
    private HitBox box11;
    private HitBox box12;
    private HitBox box13;
    private HitBox box14;
    private HitBox box15;
    private HitBox box16;
    private HitBox box17;
    private HitBox box18;
    private HitBox box19;

    int mouseOffset;


    //Initialises textures for pause screen
    Texture playAgainActive = new Texture("PauseScreen/ResumeActive.png");
    Texture playAgainInactive = new Texture("PauseScreen/ResumeInactive.png");
    Texture exitButtonActive = new Texture("PauseScreen/ExitActive.png");
    Texture exitButtonInactive = new Texture("PauseScreen/ExitInactive.png");
    Texture kroyLogo = new Texture("KROY_logo.png");
    List<Texture> pauseTextures = Arrays.asList(playAgainActive, playAgainInactive, exitButtonActive, exitButtonInactive, kroyLogo);

    //Initialises pause screen
    PauseScreen pauseScreen = new PauseScreen(false, pauseTextures);

    private Texture gameOverImage;

    // Testing - Fire Station Co-Ords
    ShapeRenderer shape = new ShapeRenderer();

    //
    Animation animation;
    float elapseTime = 0f;
    TextureAtlas textureAtlas;
    SpriteBatch sb1;



    public GameScreen(final KROY game) {
        //bullet
        bullets = new ArrayList<Bullet>();


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

        //Section for defining the player characteristics
        sb = new SpriteBatch();
        //Links to fire engine class
        Point p = new Point(830,220 );
        engine1 = new FireEngine(50,200,100,100,p, new Texture(Gdx.files.internal("Sprites/FireEngine1.png"))); // Instance Number 1
        engine2 = new FireEngine(200, 500, 50, 50,p, new Texture(Gdx.files.internal("Sprites/FireEngine2.png"))); // Instance Number 2
        engine3 = new FireEngine(100, 300, 25, 64,p, new Texture(Gdx.files.internal("Sprites/FireEngine1.png"))); // Instance Number 3

        //((FireEngine) engine1).toggleState(); // Sets to active for testing
        //Sprite drawable = engine1.drawable;

        //drawable.setOrigin(52,54);

        //HitBoxes to allow collisions


        box1 = new HitBox(475,358,13,GameScreen.HEIGHT - 750);
        box2 = new HitBox(130, 388, 320, GameScreen.HEIGHT - 852);
        box3 = new HitBox(262, 420, 400, GameScreen.HEIGHT- 680);
        box4 = new HitBox(30,310,397, GameScreen.HEIGHT-693);
        box5 = new HitBox(117,183,397,GameScreen.HEIGHT-390);
        box6 = new HitBox(117,215, 605, GameScreen.HEIGHT-390);
        box7 = new HitBox(72, 214, 606, GameScreen.HEIGHT-248);
        box8 = new HitBox(262, 220, 845, GameScreen.HEIGHT-679);
        box9 = new HitBox(215, 150, 845, GameScreen.HEIGHT-392);
        box10 = new HitBox(450, 100, 1021, GameScreen.HEIGHT-435);
        box11 = new HitBox(215, 150, 844, GameScreen.HEIGHT-150);
        box12 = new HitBox(227, 181, 397, GameScreen.HEIGHT-246);
        box13 = new HitBox(133, 214, 605, GameScreen.HEIGHT-152);
        box14 = new HitBox(300, 200, 172, GameScreen.HEIGHT-246);
        box15 = new HitBox(200, 1200, 1,GameScreen.HEIGHT- 6);
        box16 = new HitBox(200, 400, 600,  GameScreen.HEIGHT - 1033);
        box17 = new HitBox(200, 100, 958, GameScreen.HEIGHT - 850);
        box18 = new HitBox(300, 50, -33, GameScreen.HEIGHT - 290);
        box19 = new HitBox(300, 103, 44, GameScreen.HEIGHT- 246);


        hitBoxes = new ArrayList<>();
        hitBoxes.add(box1);
        hitBoxes.add(box2);
        hitBoxes.add(box3);
        hitBoxes.add(box4);
        hitBoxes.add(box5);
        hitBoxes.add(box6);
        hitBoxes.add(box7);
        hitBoxes.add(box8);
        hitBoxes.add(box9);
        hitBoxes.add(box10);
        hitBoxes.add(box11);
        hitBoxes.add(box12);
        hitBoxes.add(box13);
        hitBoxes.add(box14);
        hitBoxes.add(box15);
        hitBoxes.add(box16);
        hitBoxes.add(box17);
        hitBoxes.add(box18);
        hitBoxes.add(box19);



        fireEngines = new ArrayList<>();
        fireEngines.add(engine1);
        fireEngines.add(engine2);
        fireEngines.add(engine3);



        // FireStation
        fireStationTexture = new Texture("Sprites/FireStation.png");
        Point fireStationPoint = new Point(FIRE_STATION_X, FIRE_STATION_Y);
        Dimensions fireStationDimensions = new Dimensions(FIRE_STATION_WIDTH, FIRE_STATION_HEIGHT);
        fireStation = new FireStation(fireStationDimensions, 200, 100, fireStationPoint, fireStationTexture);

        //Fortress 1
        fortressTexture = new Texture("Sprites/Fortress1.png");
        Dimensions fortress1Dimensions = new Dimensions(FORTRESS_1_WIDTH, FORTRESS_1_HEIGHT);
        Point fortress1Point = new Point(FORTRESS_1_X, FORTRESS_1_Y);
        fortress1 = new Fortress(fortress1Dimensions, 200, 90, fortress1Point, fortressTexture);

        //Fortress 2
        fortressTexture = new Texture("Sprites/Fortress2.png");
        Dimensions fortress2Dimensions = new Dimensions(FORTRESS_2_WIDTH, FORTRESS_2_HEIGHT);
        Point fortress2Point = new Point(FORTRESS_2_X, FORTRESS_2_Y);
        fortress2 = new Fortress(fortress2Dimensions, 200, 100, fortress2Point, fortressTexture);

        //Fortress 3
        fortressTexture = new Texture("Sprites/Fortress3.png");
        Dimensions fortress3Dimensions = new Dimensions(FORTRESS_3_WIDTH, FORTRESS_3_HEIGHT);
        Point fortress3Point = new Point(FORTRESS_3_X, FORTRESS_3_Y);
        fortress3 = new Fortress(fortress3Dimensions, 200, 110, fortress3Point, fortressTexture);

        fortressList = new ArrayList<>();
        fortressList.add(fortress1);
        fortressList.add(fortress2);
        fortressList.add(fortress3);

        fireEngines = new ArrayList<>();
        fireEngines.add(engine1);
        fireEngines.add(engine2);
        fireEngines.add(engine3);


        //Creates animation for explosion upon destruction
        sb1 = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/JetSprites.atlas"));
        animation = new Animation(1f / 40f, textureAtlas.getRegions());

        mouseOffset = 0;

    }


    /**
     * Renders the bullet at its required position
     * @param delta The offset to update the bullet by
     */
    @Override
    public void render(float delta){
        for(FireEngine fireEngine: fireEngines){
            if(fireEngine.isActive){
                /*If space key is pressed and fire engine has water, a bullet is created at a position relative to that
                *of the active Fire Engine, and the volume of water of the engine is decreased*/
                if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && fireEngine.getVolumeOfWater() > 0){
                    bullets.add(new Bullet(fireEngine.position.x-24,fireEngine.position.y+10,
                            new Texture("Sprites/bubble.png")));
                    fireEngine.lowerVolumeOfwater();
                }
                ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
                // Checks each bullet against each fortress for a collision
                for(Bullet bullet: bullets){
                    for (Tower fort: fortressList){
                        /*If bullet hits fortress, lower the fortress' health by the given damage per second and delete
                        the bullet.*/
                        boolean hit =  bullet.isHit(fort);
                        if(hit) {
                            fort.setHealth(fort.getHealth()-(int)500/fireEngine.movementSpeed);
                            bulletsToRemove.add(bullet);
                        }
                    }
                    //Update the position of the bullet
                    bullet.update(delta);
                    // If bullet goes off screen remove it
                    if (bullet.remove)
                        bulletsToRemove.add(bullet);
                }
                bullets.removeAll(bulletsToRemove);
            }
        }

        //If the game is paused, show the pause screen
        if (pauseScreen.isPaused()){
            pauseScreen.pauseScreen(game);
        }
        else {
            //Moves the map around when the input is touched
            if (Gdx.input.isTouched()) {
                int y = Gdx.input.getDeltaY();
                camera.position.add(0, y, 0);
                camera.update();
                mouseOffset += y;
            }
            FPS.log(); // Prints FPS to Console

            Gdx.gl.glClearColor(19/355f , 103/255f, 44/255f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            //Renders the map
            renderer.setMap(map);
            renderer.setView(camera);
            renderer.render();

            //section for drawing the actual sprites here
            sb.setProjectionMatrix(camera.combined);
            sb.begin();

            //Draws each (alive) fire engine
            fireStation.drawable.draw(sb);
            for(FireEngine fireEngine: fireEngines){
                fireEngine.drawable.draw(sb);
            }
            //Draws each (alive) fortress
            for(Fortress fortress: fortressList){
                fortress.drawable.draw(sb);
            }
            sb.end();
            sb.begin();
            //Draws each active bullet
            for (Bullet bullet:bullets){
                bullet.render(sb);
            }
            //Draws health bar and water bar for each (alive) fire engine
            for(FireEngine fireEngine: fireEngines){
                fireEngine.drawHealthBar(camera, shape);
                fireEngine.drawWaterBar(camera,shape);
            }
            //Draws health bar for each (living) fortress
            for(Fortress fortress: fortressList){
                fortress.drawHealthBar(camera, shape);
            }


            sb.end();

            if(this.testMode == true){
                testboxRenderer();
            }


            //Moves the Fire Engine if it is active
            fireEngineMovement();

            ArrayList<FireEngine> fireEnginesToDelete = new ArrayList<>();
            ArrayList<Fortress> fortressesToDelete = new ArrayList<>();
            for(final FireEngine fireEngine: fireEngines){
                for(Fortress fortress: fortressList){
                    if(fireEngine.inRange(fortress)){
                        fireEngine.attackFortress(fortress);
                    }
                    if(fortress.inRange(fireEngine)){
                        fortress.attackFireEngine(fireEngine);
                    }
                    //If fortress' health is 0, remove it from the list of fortresses
                    if(fortress.getHealth() <= 0){
                        fortress.destroy(animation,elapseTime, sb);
                        //Need a way of deleting this object properly but can't figure it out
                        fortressesToDelete.add(fortress);
                    }
                }
                //If engine's health is 0, remove it from the list of fire engines
                if(fireEngine.getHealth() <= 0){
                    fireEngine.destroy(animation,elapseTime, sb);
                    //Need a way of deleting this object properly but can't figure it out
                    fireEnginesToDelete.add(fireEngine);

                }

                if(fireEngine.inRange(fireStation)){
                    //Only calls when not already called, setting the token Boolean 'isBeingRepaired' to true when called
                    if (fireEngine.isBeingRepaired == false) {
                        fireEngine.isBeingRepaired = true;
                        //Calls the 'repair' function at one-second intervals
                        //until the fireEngine moves out of the range
                        final Timer t = new Timer();
                        t.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                if (!fireEngine.inRange(fireStation)) {
                                    t.cancel();
                                }
                                fireStation.repair(fireEngine, 2);
                            }
                        }, 0, 1000);

                    }
                }
                else {
                    fireEngine.isBeingRepaired = false;
                }
            }
            //If there are no alive fire stations, move to the 'lose' screen
            if(fireEngines.isEmpty()){
                game.setScreen(new GameOverScreen(game, "lose"));
            }
            //If there are no alive fortresses, move to the 'win' screen
            if(fortressList.isEmpty()){
                game.setScreen(new GameOverScreen(game, "win"));
            }
            fireEngines.removeAll(fireEnginesToDelete);
            fortressList.removeAll(fortressesToDelete);

            //Moves fire engines off the map - temp fix for bullets drawing on death
            for(FireEngine fireEngine : fireEnginesToDelete){
                fireEngine.position = new Point(5000,5000);
                fireEngine.drawable.setPosition(5000,5000);
            }

        }
    }

    private void testboxRenderer(){
        //Draws a range box - Testing Purposes
        for(FireEngine fireEngine: fireEngines){
            fireEngine.drawBox(fortressList, camera,fireEngine.drawable,shape);

        }
        for(Fortress fortress: fortressList){
            fortress.drawBox(fireEngines,camera,shape);

        }

        //Draws Hitboxes for testing
        for(HitBox box: hitBoxes){
            box.drawBox(fireEngines,camera);
        }

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
        for (Entity fireEngine : fireEngines) {
            //Only move the Fire Engine if it is active
            if (((FireEngine)fireEngine).isActive) {
                //If the right arrow key is being pressed, move the engine to the right
                if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                    if (((FireEngine)fireEngine).isActive) {
                        ((FireEngine) fireEngine).updatePosition("RIGHT",hitBoxes);

                    }
                }
                //If the left arrow key is being pressed, move the engine to the left
                else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                    if (((FireEngine)fireEngine).isActive) {
                        ((FireEngine) fireEngine).updatePosition("LEFT",hitBoxes);
                    }
                }
                //If the up arrow key is being pressed, move the engine up
                else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    if (((FireEngine)fireEngine).isActive) {
                        ((FireEngine) fireEngine).updatePosition("UP",hitBoxes);
                    }
                }
                //If the down arrow key is being pressed, move the engine down
                else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    if (((FireEngine)fireEngine).isActive) {
                        ((FireEngine) fireEngine).updatePosition("DOWN",hitBoxes);
                    }
                }
            }
        }

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

    }


    @Override
    public void hide(){
        dispose();
    }

    @Override
    public void pause(){
        //Signals that the game has been paused
        pauseScreen.setPaused(true);
    }

    @Override
    public void resume(){
        //Signals that the game has been resumed
        pauseScreen.setPaused(false);
    }

    @Override
    public void dispose(){
        //Disposes of the map, render and pause screen
        map.dispose();
        renderer.dispose();
        pauseScreen.dispose();
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
        screenY -= mouseOffset;
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
        System.out.println(screenX);
        System.out.println(screenY);
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
        /**
        Vector3 screenCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 worldCoordsBefore = camera.unproject(new Vector3(screenCoords));
        camera.zoom += amount * camera.zoom * 0.1f;
        camera.update();
        Vector3 worldCoordsAfter = camera.unproject(new Vector3(screenCoords));
        Vector3 diff = new Vector3(worldCoordsAfter).sub(worldCoordsBefore);
        camera.position.sub(diff);
        camera.update();
        return true;
         **/
        return false;

    }
}