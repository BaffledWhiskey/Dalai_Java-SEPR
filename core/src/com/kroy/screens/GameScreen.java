package com.kroy.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class GameScreen implements Screen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public static int WIDTH = 1280;
    public static int HEIGHT = 720;



    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1) ;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setMap(map);
        renderer.setView(camera);
        renderer.render();

        // Sound does play and so map should have been rendered. WHY???
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("service-bell_daniel_simion.mp3"));
        sound.play();

    }
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();

    }

    @Override
    public void show(){
        map = new TmxMapLoader().load("maps/2/Map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();

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
        map.dispose();
        renderer.dispose();
    }
}
