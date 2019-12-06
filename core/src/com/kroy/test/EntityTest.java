package com.kroy.test;

 /**import com.kroy.entities.Entity;
 import com.kroy.game.Point;
 import org.junit.jupiter.api.Test;
 import org.junit.jupiter.api.Assertions;

class EntityTest {

@Test
    public void testInRangeBoundary() {
        Entity e = new Entity(0,5, new Point(5,5));
        Assertions.assertTrue(e.inRange(new Entity(0,0,new Point(9,0))));
        Assertions.assertTrue(e.inRange(new Entity(0,0,new Point(10,0))));
        Assertions.assertFalse(e.inRange(new Entity(0,0,new Point(11,0))));
        Assertions.assertTrue(e.inRange(new Entity(0,0,new Point(0,9))));
        Assertions.assertTrue(e.inRange(new Entity(0,0,new Point(0,10))));
        Assertions.assertFalse(e.inRange(new Entity(0,0,new Point(0,11))));
    }
}**/