import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kroy.screens.PauseScreen;
import com.kroy.game.KROY;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PauseScreenTest {

    List<Texture> mockedTextures;
    KROY game;

    @Before
    public void setup() {
        mockedTextures = Arrays.asList(mock(Texture.class), mock(Texture.class), mock(Texture.class),
                mock(Texture.class), mock(Texture.class));
        Gdx.input = mock(Input.class);
        Gdx.app = mock(Application.class);
        game = new KROY();
        game.batch = mock(SpriteBatch.class);
        //doNothing().when(game.batch).begin();
    }

    @Test
    public void pauseScreenShouldBePausedWhenCalledTest() {
        PauseScreen p = new PauseScreen(true, mockedTextures);
        p.pauseScreen(game);
        Assertions.assertTrue(p.isPaused());
    }

    @Test
    public void pauseScreenShouldResumeWhenResumeButtonPressedTest() {
        PauseScreen p = new PauseScreen(true, mockedTextures);
        lenient().when(Gdx.input.getX()).thenReturn(540);
        lenient().when(Gdx.input.getY()).thenReturn(700);
        lenient().when(Gdx.input.isTouched()).thenReturn(true);
        p.pauseScreen(game);
        Assertions.assertFalse(p.isPaused());
    }

    @Test
    public void pauseScreenShouldNotResumeWhenElsewherePressedTest() {
        PauseScreen p = new PauseScreen(true, mockedTextures);
        lenient().when(Gdx.input.getX()).thenReturn(1000);
        lenient().when(Gdx.input.getY()).thenReturn(1000);
        lenient().when(Gdx.input.isTouched()).thenReturn(true);
        p.pauseScreen(game);
        Assertions.assertTrue(p.isPaused());
    }

    @ParameterizedTest
    @ValueSource(ints = {239, 240, 280, 281})
    public void pauseScreenOutsideBoundaryTest(int val) {
        PauseScreen p = new PauseScreen(true, mockedTextures);
        lenient().when(Gdx.input.getX()).thenReturn(val);
        lenient().when(Gdx.input.getY()).thenReturn(700);
        lenient().when(Gdx.input.isTouched()).thenReturn(true);
        p.pauseScreen(game);
        Assertions.assertTrue(p.isPaused());
    }

    @ParameterizedTest
    @ValueSource(ints = {241, 279})
    public void pauseScreenInsideBoundaryTest(int val) {
        PauseScreen p = new PauseScreen(true, mockedTextures);
        lenient().when(Gdx.input.getX()).thenReturn(val);
        lenient().when(Gdx.input.getY()).thenReturn(700);
        lenient().when(Gdx.input.isTouched()).thenReturn(true);
        p.pauseScreen(game);
        Assertions.assertFalse(p.isPaused());
    }
}