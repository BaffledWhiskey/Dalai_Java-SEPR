package com.kroy.entities;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.kroy.game.Point;
import com.badlogic.gdx.math.Vector2;


public class Bullet extends ApplicationAdapter {
    public static final int SPEED =300;  //speed of bullet
    public Texture texture;      //create static object
    //x,y position of the bullet
    private Vector2 direction;

    Point initialPosition;
    Point position;

    public boolean remove = false;       //check if the object should be removed

    public Bullet(float x, float y, Texture texture) {    //create a new bullet, start from (x,y)
        this.initialPosition = new Point((int)x, (int)y);
        this.position = this.initialPosition;
        this.texture = texture;

         Vector2 directionCalc  = new Vector2(Gdx.input.getX() - x, Gdx.input.getY() - y);

        // Add Random varience to the bullet directipon
        directionCalc.x *= Math.random() * (((1.2 - 0.8) + 1) + 0.8);
        directionCalc.y *= Math.random() * (((1.2 - 0.8) + 1) + 0.8);
        directionCalc.nor();

        this.direction = directionCalc;


        if (texture == null) {
            texture = new Texture("Sprites/bubble.png");   //image of the bullet,
        }
    }

    // update the bullet position (go up)
    public void update (float deltaTime) {
        position.y += direction.y * SPEED * deltaTime;
        position.x += direction.x * SPEED * deltaTime;

        // and make sure the bullet doesn't leave the screen
        // once bullet leave the screen, destroy the bullet
        if (position.y > Gdx.graphics.getHeight()) {
            remove = true;
        }
    }

    //draw the bullet
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




