package com.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kroy.game.Point;

/**
 * A Fortress does not move but can attack fire engines within range, as well as be attacked
 */
public class Fortress extends Tower{
    /**
     * @param dimensions The size of the tower
     * @param health     The current health of the tower
     * @param range      The maximun range of the tower
     * @param position   The position of the tower on the map
     */

    public Fortress(Dimensions dimensions, int health, int range, Point position, Texture img) {
        super(dimensions, health, range, position, img);
    }

    /**
     * Increases the health of the fortress
     * @return
     */
    public void increaseHealth(){
        this.health++;
    }

    /**
     * Destroys the fortress
     * @param animation The animation which indicates destruction
     * @param elapseTime The amount of time taken to destroy the fortress
     */
    public void destroy(Animation animation, float elapseTime){
        if(!(this.health <= 0)){
            try {
                throw new Exception("can't destroy Entity with health greater than 0");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            SpriteBatch sb = new SpriteBatch();
            sb.begin();
            sb.draw((TextureRegion) animation.getKeyFrame(elapseTime, true), this.position.x, this.position.y, 0, 0, 80, 80, 1, 1, 9, true);
            sb.end();
            sb.dispose();
        }
    }


    /**
     * Reduces the health of a given Fire Engine
     * @param engine The Fire Engine which is being attacked
     */
    public void attackFireEngine(FireEngine engine){
        engine.setHealth(engine.getHealth() - 1);
    }

}
