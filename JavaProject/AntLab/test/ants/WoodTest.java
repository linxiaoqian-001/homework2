package ants;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WoodTest {

    private Wood wood = new Wood(30);
    private Ant ant = new Ant();

    @Before
    public void setUp() throws Exception {
        ant.setPosition(6);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isOut() {
        ant.setPosition(-1);
        wood.isOut(ant);
        Assert.assertEquals(false, ant.isAlive);
    }
}