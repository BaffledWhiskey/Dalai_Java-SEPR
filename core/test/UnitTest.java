import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;
import com.kroy.entities.Entity;
import com.kroy.entities.Unit;
import com.kroy.screens.Kroy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Since the Entity class is abstract, we can not instantiate it directly. To circumvent that, we run our tests against
 * this dummy class.*/
class TestUnit extends Unit{

    public TestUnit(Kroy kroy, Vector2 position, float size, Sprite sprite, float health) {
        super(kroy, position, size, sprite, health);
    }
}

@RunWith(MockitoJUnitRunner.class)
public class UnitTest {
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
    /**         Test should work but gets an error since Unit[] should be Class[]
     @Test
     public void MethodShouldReturnClosestUnitOfType(){
     TestUnit unit1 = new TestUnit(mockedKroy, new Vector2(0.0f, 0.0f), 5.0f, mockedSprite1, 100);
     TestUnit unit2 = new TestUnit(mockedKroy, new Vector2(5.0f, 5.0f), 5.0f, mockedSprite2, 100);
     Unit[] unitList = new Unit[]{unit2};
     Assert.assertEquals(unit2 , unit1.getClosestOfTypes(unitList));
     }
     */
    @Test
    public void ShouldBeAbleToSetHealthTest(){
        TestUnit unit1 = new TestUnit(mockedKroy, new Vector2(0.0f, 0.0f), 5.0f, mockedSprite1, 100);

        unit1.setHealth(50);    //Tests if the health can be changed to a value within typical range
        assertEquals(50, unit1.getHealth(), 0.0);

        unit1.setHealth(-1);    //Testing for lower boundary values
        assertEquals(0, unit1.getHealth(), 0.0);
        unit1.setHealth(0);
        assertEquals(0, unit1.getHealth(), 0.0);

        unit1.setHealth(101);   //Testing for upper boundary
        assertEquals(100, unit1.getHealth(), 0.0);
        unit1.setHealth(100);
        assertEquals(100, unit1.getHealth(), 0.0);
    }

    @Test
    public void ShouldBeAbleToAddXAmountOfHealthTest(){
        TestUnit unit1 = new TestUnit(mockedKroy, new Vector2(0.0f, 0.0f), 5.0f, mockedSprite1, 100);

        unit1.setHealth(50);        //Tests addHealth with data within a non potential erroneous range
        unit1.addHealth(25);
        assertEquals(75, unit1.getHealth(), 0.0);

        unit1.setHealth(100);        //Upper boundary
        unit1.addHealth(1);
        assertEquals(100, unit1.getHealth(), 0.0);

        unit1.setHealth(50);        //Tests lower bound with a negative
        unit1.addHealth(-75);
        assertEquals(0, unit1.getHealth(), 0.0);
    }

    @Test
    public void UnitShouldUpdateTest(){
        TestUnit unit1 = new TestUnit(mockedKroy, new Vector2(0.0f, 0.0f), 5.0f, mockedSprite1, 100);

        unit1.setHealth(0);      //Changes health to zero and checks if it should be removed
        unit1.update(1);
        Assert.assertTrue(unit1.isToBeRemoved());
    }
}
