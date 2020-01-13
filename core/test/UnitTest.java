import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.entities.Unit;
import com.kroy.game.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@PowerMockIgnore("org.myproject.*")
@RunWith(PowerMockRunner.class)
@PrepareForTest({Graphics.class})
public class UnitTest {

    @Mock
    public Texture mockedImg;

    @Before
    public void setup() {
        mockedImg = mock(Texture.class);
        when(mockedImg.getWidth()).thenReturn(0);
        when(mockedImg.getHeight()).thenReturn(0);
        Gdx.graphics = PowerMockito.mock(Graphics.class);
        when(Gdx.graphics.getDeltaTime()).thenReturn(1f);
    }

    @Test
    public void doesItWork() {
        Assertions.assertEquals(Gdx.graphics.getDeltaTime(),1f);
    }


    @Test
    public void pointShouldBeInRangeTest() {
        Unit u = new Unit(1,5,5,new Point(5,5), mockedImg);
        u.updatePosition("RIGHT");
        Assertions.assertEquals(u.position.x,6);
        Assertions.assertEquals(u.position.y,5);
    }

    @Test
    public void multipleMovementsShouldAddUpTest() {
        Unit u = new Unit(1,5,5,new Point(5,5), mockedImg);
        u.updatePosition("RIGHT");
        u.updatePosition("RIGHT");
        u.updatePosition("DOWN");
        Assertions.assertEquals(u.position.x,7);
        Assertions.assertEquals(u.position.y,4);
    }

    @Test
    public void movingEquallyInAllDirectionsShouldResultInOriginalPositionTest() {
        Unit u = new Unit(1,5,5,new Point(5,5), mockedImg);
        u.updatePosition("RIGHT");
        u.updatePosition("UP");
        u.updatePosition("LEFT");
        u.updatePosition("DOWN");
        Assertions.assertEquals(u.position.x,5);
        Assertions.assertEquals(u.position.y,5);
    }
}
