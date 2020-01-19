import com.badlogic.gdx.graphics.Texture;
 import com.kroy.entities.Entity;
 import com.kroy.game.Point;
import org.junit.Before;
import org.junit.Test;
 import org.junit.jupiter.api.Assertions;
 import org.junit.jupiter.params.ParameterizedTest;
 import org.junit.jupiter.params.provider.ValueSource;
 import org.junit.runner.RunWith;
 import org.mockito.Mock;
 import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class EntityTest {

     @Mock
     public Texture mockedImg;

     @Before
     public void setup() {
         mockedImg = mock(Texture.class);
         when(mockedImg.getWidth()).thenReturn(5);
         when(mockedImg.getHeight()).thenReturn(5);
     }

    @Test
    public void pointShouldBeInRangeTest() {
        Entity e = new Entity(0,5, new Point(5,5));
        Assertions.assertTrue(e.inRange(new Entity(0,0,new Point(3,3), mockedImg)));
    }

    @Test
    public void pointShouldNotBeInRangeTest() {
        Entity e = new Entity(0,5, new Point(5,5));
        Assertions.assertFalse(e.inRange(new Entity(0,0,new Point(15,15), mockedImg)));
    }

    @ParameterizedTest
    @ValueSource(ints = {11,12})
    public void insideBoundaryInRangeTest(int val) {
        Entity e = new Entity(0,5, new Point(5,5), mockedImg);
        Assertions.assertTrue(e.inRange(new Entity(0,0,new Point(val,5), mockedImg)));
        Assertions.assertTrue(e.inRange(new Entity(0,0,new Point(5,val), mockedImg)));
    }

    @Test
    public void outsideBoundaryInRangeTest() {
        Entity e = new Entity(0,5, new Point(5,5), mockedImg);
        Assertions.assertFalse(e.inRange(new Entity(0,0,new Point(13,5), mockedImg)));
        Assertions.assertFalse(e.inRange(new Entity(0,0,new Point(5,13), mockedImg)));
    }
}