package com.kroy.entities;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;


public class Explosion extends ApplicationAdapter {
    SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapseTime = 0f;
    float scaleX;
    float scaleY;

    public Explosion(){
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


}

