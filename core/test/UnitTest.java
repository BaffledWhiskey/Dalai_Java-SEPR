import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.kroy.entities.HitBox;
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

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void spriteShouldMoveWhenButtonPressed() {
        Unit u = new Unit(1,5,5,new Point(5,5), mockedImg);
        u.updatePosition("RIGHT", new ArrayList<HitBox>());
        Assertions.assertEquals(u.position.x,6);
        Assertions.assertEquals(u.position.y,5);
    }

    @Test
    public void multipleMovementsShouldAddUpTest() {
        Unit u = new Unit(1,5,5,new Point(5,5), mockedImg);
        u.updatePosition("RIGHT", new ArrayList<HitBox>());
        u.updatePosition("RIGHT", new ArrayList<HitBox>());
        u.updatePosition("DOWN", new ArrayList<HitBox>());
        Assertions.assertEquals(u.position.x,7);
        Assertions.assertEquals(u.position.y,4);
    }

    @Test
    public void movingEquallyInAllDirectionsShouldResultInOriginalPositionTest() {
        Unit u = new Unit(1,5,5,new Point(5,5), mockedImg);
        u.updatePosition("RIGHT", new ArrayList<HitBox>());
        u.updatePosition("UP", new ArrayList<HitBox>());
        u.updatePosition("LEFT", new ArrayList<HitBox>());
        u.updatePosition("DOWN", new ArrayList<HitBox>());
        Assertions.assertEquals(u.position.x,5);
        Assertions.assertEquals(u.position.y,5);
    }

    @Test
    public void spriteShouldNotEnterHitBoxTest() {
        Unit u = new Unit(2,5,5,new Point(5,5), mockedImg);
        ArrayList<HitBox> h = new ArrayList<HitBox>();
        h.add(new HitBox(2,2,6,5));
        u.updatePosition("RIGHT", h);
        Assertions.assertEquals(u.position.x,-2);
        Assertions.assertEquals(u.position.y,5);
    }

    @Test
    public void hitBoxBoundaryTest() {
        Unit u = new Unit(1,5,5,new Point(5,5), mockedImg);
        ArrayList<HitBox> h = new ArrayList<HitBox>();
        h.add(new HitBox(2,2,7,5));
        u.updatePosition("RIGHT", h);
        Assertions.assertEquals(u.position.x,6);
        Assertions.assertEquals(u.position.y,5);
    }
}
