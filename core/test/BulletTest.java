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
public class BulletTest {

    @Mock
    public Texture mockedImg;

    @Before
    public void setup() {
        mockedImg = mock(Texture.class);
        when(mockedImg.getWidth()).thenReturn(5);
        when(mockedImg.getHeight()).thenReturn(5);
    }

    @Test
    public void bulletShouldDoDamageToEntityTest() {

    }

    @Test
    public void bulletShouldUpdateCorrectlyTest() {

    }

    @Test
    public void bulletShouldDestroyCorrectlyTest() {

    }

}