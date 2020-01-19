package com.kroy.entities;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kroy.game.Point;
import com.kroy.screens.GameScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.kroy.screens.GameScreen.HEIGHT;

/**
 * A HitBox is defined as a rectangle which the Fire Enigne cannot enter
 */
public class HitBox{
    public int HEIGHT;
    public int WIDTH;
    public Point position;

    /**
     * A simple HitBox class to allow collisions by testing intersection with it
     * @param HEIGHT the height of the hitbox
     * @param WIDTH the width of the hitbox
     * @param x the x coord - from the bottom left corner
     * @param y the y coord - from the bottom left corner
     */
    public HitBox(int HEIGHT, int WIDTH, int x, int y){
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.position = new Point(x,y);

    }

    /**
     * Determines whether a given entity is in range
     * @param target The Entity which may or may not be in range
     * @return true if the entity is in range, false otherwise
     */
    public boolean inRange(Entity target) {
        List<Integer> rangeBox = Arrays.asList(position.x+WIDTH/2, WIDTH/2, position.y+HEIGHT/2, HEIGHT/2);
        List<Integer> targetBox = Arrays.asList(target.position.x, target.width / 2, target.position.y, target.height / 2);
        if (targetBox.get(0) - targetBox.get(1) < rangeBox.get(0) + rangeBox.get(1)
                && rangeBox.get(0) - rangeBox.get(1) < targetBox.get(0) + targetBox.get(1)
                && targetBox.get(2) - targetBox.get(3) < rangeBox.get(2) + rangeBox.get(3)
                && rangeBox.get(2) - rangeBox.get(3) < targetBox.get(2) + targetBox.get(3)) {
            return true;
        } else {
            return false;
        }
    }


    public void drawBox(ArrayList<FireEngine> target, OrthographicCamera camera) {
        ShapeRenderer shape = new ShapeRenderer();
        shape.setProjectionMatrix(camera.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        boolean redBox = false;
        for(Entity entity : target) {
            if (this.inRange(entity)) {
                redBox = true;
            }
        }
        if (redBox) {
            shape.setColor(Color.RED);
        }
        else {
            shape.setColor(Color.GREEN);
        }
        shape.rect(position.x, position.y, WIDTH, HEIGHT);
        shape.end();
    }

}
