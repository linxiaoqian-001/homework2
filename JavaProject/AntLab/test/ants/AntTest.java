package ants;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AntTest {

    private Ant ant = new Ant();

    @Before
    public void setUp() throws Exception {
        ant.setDirection(true);
        ant.setPosition(5);
        ant.setAlive(true);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void changeDirection() {
        ant.changeDirection();
        Assert.assertEquals(false, ant.getDirection());
    }

    @Test
    public void creep() {
        ant.creep();
        Assert.assertEquals(6, ant.getPosition());
    }
}