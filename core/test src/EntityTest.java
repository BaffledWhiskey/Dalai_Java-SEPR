 import com.kroy.entities.Entity;
 import com.kroy.game.Point;
 import org.junit.jupiter.api.Test;
 import org.junit.jupiter.api.Assertions;
 import org.junit.jupiter.params.ParameterizedTest;
 import org.junit.jupiter.params.provider.ValueSource;

 class EntityTest {

    @Test
    public void insideBoundaryTest() {
        Entity e = new Entity(0,5, new Point(5,5));
        Assertions.assertTrue(e.inRange(new Entity(0,0,new Point(9,5))));
        Assertions.assertTrue(e.inRange(new Entity(0,0,new Point(5,9))));
    }

     @ParameterizedTest
     @ValueSource(ints = {10,11})
     public void outsideBoundaryTest(int val) {
         Entity e = new Entity(0,5, new Point(5,5));
         Assertions.assertFalse(e.inRange(new Entity(0,0,new Point(val,5))));
         Assertions.assertFalse(e.inRange(new Entity(0,0,new Point(5,val))));
     }

    @Test
    public void pointShouldBeInRangeTest() {
        Entity e = new Entity(0,5, new Point(5,5));
        Assertions.assertTrue(e.inRange(new Entity(0,0,new Point(3,3))));
    }

    @Test
    public void pointShouldNotBeInRangeTest() {
        Entity e = new Entity(0,5, new Point(5,5));
        Assertions.assertFalse(e.inRange(new Entity(0,0,new Point(15,15))));
    }
}