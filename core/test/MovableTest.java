import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.entities.Movable;
import com.kroy.screens.Kroy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

class TestMovable extends Movable {
    public TestMovable(Kroy gameScreen, Vector2 position, float size, Sprite sprite, int health, float movementSpeed, boolean checkCollisions) {
        super(gameScreen, position, size, sprite, health, movementSpeed, checkCollisions);
    }
    public TestMovable(Kroy gameScreen, JsonValue json) {
        super(gameScreen, json);
    }
}

    @RunWith(MockitoJUnitRunner.class)
public class MovableTest {
    @Mock
    public Sprite mockedSprite1;
    public Sprite mockedSprite2;
    public Kroy mockedKroy;

    @Before
    public void setup() {
        mockedSprite1 = mock(Sprite.class);
        mockedSprite2 = mock(Sprite.class);
        mockedKroy = mock(Kroy.class);
    }

    @Test
    public void ShouldBeAbleToChangeVelocity(){
        TestMovable movable1 = new TestMovable(mockedKroy, new Vector2(0.0f, 0.0f)
                , 5.0f, mockedSprite1, 100, 0
                , false);

        movable1.setVelocity(new Vector2(1,1));     //Tests for typical positive velocity
        Assert.assertEquals(new Vector2(1,1), movable1.getVelocity());

        movable1.setVelocity(new Vector2(-1,-1));     //Tests for typical negative velocity
        Assert.assertEquals(new Vector2(-1,-1), movable1.getVelocity());
    }

    @Test
    public void MovableNextPositionValidMoveTest(){
        TestMovable movable1 = new TestMovable(mockedKroy, new Vector2(0.0f, 0.0f)
                , 5.0f, mockedSprite1, 100, 5
                , false);

        movable1.setVelocity(new Vector2(1,0));    //checks x position
        movable1.update(1);
        Assert.assertEquals(new Vector2 (5,0), movable1.getPosition());

        movable1.setVelocity(new Vector2(0,1));    //checks y position
        movable1.update(1); //moving from (5,0) remember
        Assert.assertEquals(new Vector2 (5,5), movable1.getPosition());
    }

    @Test
    public void MovableNextPositionInValidMoveTest(){
        TestMovable movable1 = new TestMovable(mockedKroy, new Vector2(0.0f, 0.0f)
                , 5.0f, mockedSprite1, 100, 0
                , false);

        movable1.setVelocity(new Vector2(-1,0));    //checks negative -1 x coord
        movable1.update(1);
        Assert.assertEquals(new Vector2 (0,0), movable1.getPosition());

        movable1.setVelocity(new Vector2(0,-1));    //checks negative -1 y coord
        movable1.update(1);
        Assert.assertEquals(new Vector2 (0,0), movable1.getPosition());
    }
}
