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
    public static final int SPEED =300;
    private static Texture texture;
    int x,y;
    Point position;


    public boolean remove = false;


    public Bullet( int x, int y) {
        this.x = x;
        this.y = y;


        if (texture == null)
            texture = new Texture("Sprites/waterball.png");
        }

        public void update (float deltaTime){
                y += SPEED * deltaTime;
                if (y >Gdx.graphics.getHeight()) {
                    remove = true;
                }


    }

    public void render (SpriteBatch batch){
        batch.draw(texture,x,y);
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




