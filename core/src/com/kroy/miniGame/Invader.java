package com.kroy.miniGame;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

public class Invader {

    MiniGame miniGame;
    Sprite sprite;
    Vector2 position;
    Vector2 velocity;
    Vector2 acceleration;
    float size;
    float movementSpeed;
    float shynessRange;
    float shyness;
    float killRadius2;

    public Invader(MiniGame miniGame, JsonValue json) {
        this.miniGame = miniGame;
        position = new Vector2(json.getFloat("spawnDistance"), 0).rotate((float) (Math.random() * 360));
        movementSpeed = json.getFloat("movementSpeed");
        sprite = miniGame.getSprite(json.getString("img"));
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        shyness = json.getFloat("shyness");
        shynessRange = json.getFloat("shynessRange");
        killRadius2 = json.getFloat("killRadius");
        size = json.getFloat("size");
        killRadius2 *= killRadius2;
    }

    public void update(float deltaTime) {
        // Since the virus is always heading towards the centre, we can use the length of its position as the distance
        // to the target.
        acceleration = position.cpy().scl(-1).setLength(1f);
        Vector2 mousePositionDelta = miniGame.getMousePosition().cpy().sub(position);
        float mousePositionDeltaLength = mousePositionDelta.len();

        // This function computes the strength by which the alien is scared away from the mouse
        float scalar = (float) (shyness / (Math.pow(shynessRange * mousePositionDeltaLength, 2) + 1));
        acceleration.sub(mousePositionDelta.setLength(scalar));

        velocity.add(acceleration).clamp(0, movementSpeed * deltaTime);
        position.add(velocity);

        if (position.len2() < killRadius2) {
            miniGame.onLoose();
        }
    }

    public void render() {
        SpriteBatch batch = miniGame.getBatch();
        // Apply necessary transformations to the sprite
        sprite.setRotation(velocity.angle());
        sprite.setPosition(position.x - sprite.getWidth() * 0.5f, position.y - sprite.getHeight() * 0.5f);
        sprite.setScale(size / Math.min(sprite.getHeight(), sprite.getWidth()));
        sprite.draw(batch);
    }
}
