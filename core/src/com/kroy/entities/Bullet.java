package com.kroy.entities;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.kroy.game.Point;
import com.badlogic.gdx.math.Vector2;
import com.kroy.screens.GameScreen;

/** A Bullet is drawn from a target towards its enemy, and either hits the enemy (hence reducing its health)
 * or misses the enemy, leaving the screen*/
public class Bullet extends ApplicationAdapter {
    public static final int SPEED =300;  //speed of bullet
    public Texture texture;      //create static object
    //x,y position of the bullet
    private Vector2 direction;

    Point initialPosition;
    public Point position;
    public int range;

    public boolean remove = false;       //check if the object should be removed

    public Bullet(float x, float y, Texture texture, int range) {    //create a new bullet, start from (x,y)
        this.initialPosition = new Point((int)x, (int)y);
        this.position = new Point((int)x, (int) y);
        this.texture = texture;
        this.range = range;

        Vector2 directionCalc  = new Vector2(Gdx.input.getX() - x, (GameScreen.HEIGHT - Gdx.input.getY()) - y);
        System.out.println("shoot");
        // Add Random varience to the bullet directipon
        //directionCalc.x *= Math.random() * (((1.2 - 0.8) + 1) + 0.8);
        //directionCalc.y *= Math.random() * (((1.2 - 0.8) + 1) + 0.8);

        directionCalc.nor();
        this.direction = directionCalc;


        if (texture == null) {
            texture = new Texture("Sprites/bubble.png");   //image of the bullet
        }
    }

    /**
     * Updates the bullet position by the given direction
     * @param deltaTime A factor used to determine how far the bullet must travel
     */
    public void update (float deltaTime) {
        position.y += (SPEED * deltaTime) * direction.y;
        position.x += (SPEED * deltaTime) * direction.x;

        // and make sure the bullet doesn't leave the screen
        // once bullet leave the screen, destroy the bullet
        System.out.println(position.x);
        System.out.println("init"+Integer.toString(this.initialPosition.x+range));
        if (position.x > initialPosition.x + range || position.x < initialPosition.x - range
        || position.y > initialPosition.y + range || position.y < initialPosition.y -range) {
            remove = true;
        }
    }

    /**
     * Determines whether the bullet has hit a given target
     * @param target The target which should be tested
     * @return true if the bullet has hit the given target, false otherwise
     */
    public boolean isHit(Tower target){
        if (this.position.x > target.position.x - target.dimensions.width/2 && this.position.x < target.position.x + target.dimensions.width/2
                && this.position.y > target.position.y - target.dimensions.height/2 && this.position.y > target.position.y + target.dimensions.height/2) {
            System.out.println("HIT");
            return true;
        }
        return false;
    }

    /**
     * Reduces the target's heath if hit
     * @param target The target to reduce the health of
     * @return The reduced health of the target
     */
    public int onHit(Entity target){
        target.setHealth(target.getHealth());
        return target.health;
    }

    /**
     * Draws the bullet on the screen
     * @param batch The SpriteBatch required to draw the bullet
     */
    public void render (SpriteBatch batch){
        batch.draw(texture,position.x ,position.y);
    }

}



    /*
    SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapseTime = 0f;
    float scaleX;
    float scaleY;

    public Bullet(){
        scaleX=1;
        scaleY=1;

    }

    public void create() {
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/JetSprites.atlas"));
        animation = new Animation(1f / 40f, textureAtlas.getRegions());


    }

    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }

    public void render() {
        elapseTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw((TextureRegion) animation.getKeyFrame(elapseTime,true),0,0,20,20,70,70,1,1,9,true);
        batch.end();

    }

     */




