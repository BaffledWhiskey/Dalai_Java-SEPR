import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.entities.*;
import com.kroy.screens.Kroy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CombatComponentTest {
    @Mock
    public Sprite mockedSprite1;
    public Sprite mockedSprite2;
    public Kroy mockedKroy;

    @Before
    public void setup() {
        mockedSprite1 = mock(Sprite.class);
        mockedSprite2 = mock(Sprite.class);
        mockedKroy = mock(Kroy.class);
    }

    @Test       //Tests if not in range at the boundary
    public void ShouldNotBeInRangeTest(){
        TestUnit unit1 = new TestUnit(mockedKroy, new Vector2(1.0f, 6.0f), 2.0f, mockedSprite1, 100);
        TestUnit unit2 = new TestUnit(mockedKroy, new Vector2(11.0f, 6.0f), 3.0f, mockedSprite2, 100);
        /**Combatant returns null apart from getKroy() gets mockedKroy and getPosition() get (6, 6). */
        CombatComponent combat1 = new CombatComponent(new Combatant() {
            @Override
            public CombatComponent getCombatComponent() {
                return null;
            }

            @Override
            public Kroy getKroy() {
                return mockedKroy;
            }

            @Override
            public void onAttack(Projectile projectile) {

            }

            @Override
            public float attackDamage(Unit target) {
                return 0;
            }

            @Override
            public Vector2 getPosition() {
                return new Vector2(6,6);
            }
        }, 25, 4, 10);
        Assert.assertFalse(combat1.isInRange(unit1));
        Assert.assertFalse(combat1.isInRange(unit2));

    }


    @Test       //Tests if in range at the boundary
    public void ShouldBeInRangeTest(){
        TestUnit unit1 = new TestUnit(mockedKroy, new Vector2(2.0f, 6.0f), 2.0f, mockedSprite1, 100);
        TestUnit unit2 = new TestUnit(mockedKroy, new Vector2(10.0f, 6.0f), 3.0f, mockedSprite2, 100);
        /**Combatant returns null apart from getKroy() gets mockedKroy and getPosition() get (6, 6). */
        CombatComponent combat1 = new CombatComponent(new Combatant() {
            @Override
            public CombatComponent getCombatComponent() {
                return null;
            }

            @Override
            public Kroy getKroy() {
                return mockedKroy;
            }

            @Override
            public void onAttack(Projectile projectile) {

            }

            @Override
            public float attackDamage(Unit target) {
                return 0;
            }

            @Override
            public Vector2 getPosition() {
                return new Vector2(6,6);
            }
        }, 25, 4, 10);
        Assert.assertTrue(combat1.isInRange(unit1));
        Assert.assertTrue(combat1.isInRange(unit2));

    }
}
