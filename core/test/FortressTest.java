import com.badlogic.gdx.graphics.Texture;
import com.kroy.entities.*;
import com.kroy.game.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class FortressTest {

    @Mock
    public Texture mockedImg;

    @Before
    public void setup() {
        mockedImg = mock(Texture.class);
        when(mockedImg.getWidth()).thenReturn(5);
        when(mockedImg.getHeight()).thenReturn(5);
    }

    @Test
    public void fortressShouldDecreaseHealthOfEngineTest() {
        FireEngine e = new FireEngine(5,5,100,5, new Point(5,5),mockedImg);
        Fortress f = new Fortress(new Dimensions(5,5), 5,5, new Point(5,5), mockedImg);
        f.attackFireEngine(e);
        Assertions.assertEquals(e.getHealth(),99);
    }
}
