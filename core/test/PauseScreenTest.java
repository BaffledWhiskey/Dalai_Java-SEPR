/*import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kroy.entities.Entity;
import com.kroy.game.Point;
import com.kroy.screens.PauseScreen;
import com.kroy.game.KROY;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static com.badlogic.gdx.Gdx.graphics;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PauseScreenTest {
    @Before
    public void setup() {
        graphics = PowerMockito.mock(Graphics.class);
        PauseScreen p = new PauseScreen();

    }
    //Width - 1080, Button_Width = 175, Height = 900, Play_Button_Y = 175, Button_Height = 50,
    @Test
    public void pauseScreenResumesWhenResumeButtonPressed() {
        when(Gdx.input.getX()).thenReturn(540);
        when(Gdx.input.getY()).thenReturn(700);
        when(Gdx.input.isTouched()).thenReturn(true);
        Assertions.assertTrue(p.getGamePaused());
    }
}
*/