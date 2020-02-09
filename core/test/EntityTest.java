import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.entities.Entity;
import com.kroy.screens.Kroy;
import org.junit.Before;
import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.mockito.Mock;
 import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

class TestEntity extends Entity {
    public TestEntity(Kroy kroy, Vector2 position, float size, Sprite sprite) {
        super(kroy, position, size, sprite);
    }

    public TestEntity(Kroy kroy, JsonValue json) {
        super(kroy, json);
    }
}

@RunWith(MockitoJUnitRunner.class)
public class EntityTest {

     @Mock
     public Sprite mockedSprite;
     public Kroy mockedKroy;

     @Before
     public void setup() {
         mockedSprite = mock(Sprite.class);
         mockedKroy = mock(Kroy.class);
         when(mockedKroy.getSprite("Sprites/FireEngine1.png")).thenReturn(mockedSprite);
     }

    @Test
    public void testCollides() {
         TestEntity e = new TestEntity(mockedKroy, new Vector2(10.0f, 10.0f), 5.0f, mockedSprite);
         assertTrue(e.collides(new Vector2(10.0f, 10.0f)));
         assertTrue(e.collides(new Vector2(10.0f, 14.0f)));
         assertFalse(e.collides(new Vector2(4.0f, 10.0f)));
    }

    @Test
    public void testRemoveSelf() {
        TestEntity e = new TestEntity(mockedKroy, new Vector2(10.0f, 10.0f), 5.0f, mockedSprite);
        e.removeSelf();
        assertTrue(e.isToBeRemoved());
    }

    @Test
    public void testJSONInitializer() {
        JsonValue json = new JsonReader().parse("{\"position\": {\"x\": 10, \"y\": 10}, \"size\": 5, \"img\": \"Sprites/FireEngine1.png\"}");
        TestEntity e = new TestEntity(mockedKroy, json);
        assertEquals(e.getPosition(), new Vector2(10f, 10f));
        assertEquals(e.getSize(), 5f, 0.0);
        assertEquals(e.getRotation(), 0, 0);
    }
}