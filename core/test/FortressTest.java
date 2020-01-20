import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.entities.*;
import com.kroy.game.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class FortressTest {

    @Mock
    public Texture mockedImg;
    public Animation mockedAnimation;
    public SpriteBatch mockBatch;

    @Before
    public void setup() {
        mockedImg = mock(Texture.class);
        when(mockedImg.getWidth()).thenReturn(5);
        when(mockedImg.getHeight()).thenReturn(5);
        mockedAnimation = mock(Animation.class);
        mockBatch = mock(SpriteBatch.class);
        Mockito.doThrow(new NullPointerException()).when(mockBatch).end();
    }

    @Test
    public void fortressShouldDecreaseHealthOfEngineTest() {
        FireEngine e = new FireEngine(5,5,100,5, new Point(5,5),mockedImg);
        Fortress f = new Fortress(new Dimensions(5,5), 5,5, new Point(5,5), mockedImg);
        f.attackFireEngine(e, 1);
        Assertions.assertEquals(e.getHealth(),99);
    }

    @Test(expected=NullPointerException.class)
    public void fortressShouldDestroyWhenHealthBelowZero() {
        Fortress f = new Fortress(new Dimensions(1,1),0,1,new Point(1,1),mockedImg);
        f.destroy(mockedAnimation,1f, mockBatch);
    }
}
