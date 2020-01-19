import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.entities.Bullet;
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
import org.powermock.api.mockito.PowerMockito;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class BulletTest {

    @Mock
    public Texture mockedImg;

    @Before
    public void setup() {
        mockedImg = mock(Texture.class);
        lenient().when(mockedImg.getWidth()).thenReturn(5);
        lenient().when(mockedImg.getHeight()).thenReturn(5);
        Gdx.graphics = PowerMockito.mock(Graphics.class);
    }

    @Test
    public void bulletShouldUpdateCorrectlyTest() {
        Bullet b = new Bullet(5f,5f, mockedImg);
        when(Gdx.graphics.getHeight()).thenReturn(400);
        b.update(1f);
        Assertions.assertEquals(b.y, 305f);
    }

    @Test
    public void bulletShouldBeRemovedWhenOffScreen() {
        Bullet b = new Bullet(5f,5f, mockedImg);
        when(Gdx.graphics.getHeight()).thenReturn(100);
        b.update(1f);
        Assertions.assertTrue(b.remove);
    }

}