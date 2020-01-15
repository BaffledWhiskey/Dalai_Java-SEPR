import com.badlogic.gdx.graphics.Texture;
import com.kroy.entities.FireEngine;
import com.kroy.game.Point;
import com.badlogic.gdx.graphics.g2d.Animation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FireEngineTest {

    @Mock
    public Texture mockedImg;
    public Animation mockedAnimation;


    @Before
    public void setup() {
        mockedImg = mock(Texture.class);
        mockedAnimation = mock(Animation.class);
        when(mockedImg.getWidth()).thenReturn(0);
        when(mockedImg.getHeight()).thenReturn(0);
    }

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test(expected = Exception.class)
    public void engineDoesNotDestroyWhenHealthyTest() {
        FireEngine f = new FireEngine(1,1,100,1,new Point(1,1),mockedImg);
        f.destroy(mockedAnimation,1f);
    }
}
