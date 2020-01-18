package com.kroy.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kroy.game.Point;

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

    public int increaseHealth(){
        return health++;
    }

    public void render (SpriteBatch batch){
        batch.draw(drawable,position.x,position.y);
    }

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



    public void attackFireEngine(FireEngine engine){
        engine.setHealth(engine.getHealth() - 1);
    }

}
