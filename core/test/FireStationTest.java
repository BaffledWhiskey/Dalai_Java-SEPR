import com.badlogic.gdx.graphics.Texture;
import com.kroy.entities.Dimensions;
import com.kroy.entities.FireEngine;
import com.kroy.entities.FireStation;
import com.kroy.game.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class FireStationTest {

    @Mock
    public Texture mockedImg;

    @Before
    public void setup() {
        mockedImg = mock(Texture.class);
        when(mockedImg.getWidth()).thenReturn(5);
        when(mockedImg.getHeight()).thenReturn(5);
    }

    @Test
    public void engineShouldNotRepairWhenHealthIsMaxTest() {
        FireEngine f = new FireEngine(5,5,100,5, new Point(5,5),mockedImg);
        FireStation s = new FireStation(new Dimensions(5,5), 5,5, new Point(5,5), mockedImg);
        s.repair(f,2);
        Assertions.assertEquals(f.getHealth(),100);
    }

    public void engineHealthShouldNotExceedMaxTest() {
        FireEngine f = new FireEngine(5,5,100,5, new Point(5,5),mockedImg);
        FireStation s = new FireStation(new Dimensions(5,5), 5,5, new Point(5,5), mockedImg);
        f.setHealth(99);
        s.repair(f,2);
        Assertions.assertEquals(f.getHealth(),100);
    }

    public void engineShouldNotRepairWhenOutsideRangeTest() {
        FireEngine f = new FireEngine(5,5,100,5, new Point(15,15),mockedImg);
        FireStation s = new FireStation(new Dimensions(5,5), 5,5, new Point(5,5), mockedImg);
        f.setHealth(99);
        s.repair(f,2);
        Assertions.assertEquals(f.getHealth(),99);
    }

    public void engineShouldRepairCorrectlyTest() {
        FireEngine f = new FireEngine(5,5,100,5, new Point(15,15),mockedImg);
        FireStation s = new FireStation(new Dimensions(5,5), 5,5, new Point(5,5), mockedImg);
        f.setHealth(90);
        s.repair(f,2);
        Assertions.assertEquals(f.getHealth(),92);
    }
}