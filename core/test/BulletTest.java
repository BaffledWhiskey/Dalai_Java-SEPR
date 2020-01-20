import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.kroy.entities.Bullet;
import com.kroy.entities.Dimensions;
import com.kroy.entities.Entity;
import com.kroy.entities.Tower;
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
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;


@RunWith(PowerMockRunner.class)
public class BulletTest {

    @Mock
    public Texture mockedImg;
    public Vector2 mockVector;

    @Before
    public void setup() throws Exception {
        mockedImg = mock(Texture.class);
        lenient().when(mockedImg.getWidth()).thenReturn(5);
        lenient().when(mockedImg.getHeight()).thenReturn(5);
        Gdx.graphics = PowerMockito.mock(Graphics.class);
        mockVector = mock(Vector2.class);
        PowerMockito.whenNew(Vector2.class).withAnyArguments().thenReturn(mockVector);
    }

    @Test
    public void bulletShouldUpdateCorrectlyTest() {
        Bullet b = new Bullet(5f,5f, mockedImg, 5);
        when(Gdx.graphics.getHeight()).thenReturn(400);
        b.update(1f);
        Assertions.assertEquals(b.position.y, 305f);
    }

    @Test
    public void bulletShouldBeRemovedWhenOffScreenTest() {
        Bullet b = new Bullet(5f,5f, mockedImg, 5);
        when(Gdx.graphics.getHeight()).thenReturn(100);
        b.update(1f);
        Assertions.assertTrue(b.remove);
    }

    @Test
    public void bulletShouldHitTargetTest() {
        Bullet b = new Bullet(5f,5f, mockedImg, 5);
        Tower t = new Tower(new Dimensions(1,1), 5, 5, new Point(5,5), mockedImg);
        Assertions.assertTrue(b.isHit(t));
    }

}