import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.entities.Dimensions;
import com.kroy.entities.FireEngine;
import com.kroy.entities.Fortress;
import com.kroy.game.Point;
import com.badlogic.gdx.graphics.g2d.Animation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SpriteBatch.class})
public class FireEngineTest {

    @Mock
    public Texture mockedImg;
    public Animation mockedAnimation;
    public FireEngine f;
    public SpriteBatch mockBatch;

    @Before
    public void setup() throws Exception {
        mockedImg = mock(Texture.class);
        mockedAnimation = mock(Animation.class);
        when(mockedImg.getWidth()).thenReturn(0);
        when(mockedImg.getHeight()).thenReturn(0);
        PowerMockito.mockStatic(SpriteBatch.class);
        //mockBatch = Mockito.mock(SpriteBatch.class);
        PowerMockito.whenNew(SpriteBatch.class).withAnyArguments().thenReturn(mockBatch);
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void engineDoesNotDestroyWhenHealthyTest() throws Exception {
        FireEngine f = new FireEngine(1,1,100,1,new Point(1,1),mockedImg);
        f.destroy(mockedAnimation,1f);
    }

    @Test
    public void engineShouldDestroyWhenHealthBelowZero() throws Exception {
        FireEngine f = new FireEngine(1,1,0,1,new Point(1,1),mockedImg);
        f.destroy(mockedAnimation,1f);
    }

    @Test
    public void engineShouldDecreaseHealthOfFortressTest() {
        FireEngine e = new FireEngine(5,100,100,5, new Point(5,5),mockedImg);
        Fortress f = new Fortress(new Dimensions(5,5), 100,5, new Point(5,5), mockedImg);
        e.attackFortress(f);
        Assertions.assertEquals(f.getHealth(),95);
    }
}
