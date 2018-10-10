package ants;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreepGameTest {

    private CreepGame creepGame = new CreepGame();

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < creepGame.ants.length; i++) {
            creepGame.ants[i].setPosition(i * 7);
            creepGame.ants[i].setDirection(false);
        }
        creepGame.ants[1].setDirection(true);
        creepGame.ants[1].setPosition(13);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void checkCollision() {
        creepGame.checkCollision();
        Assert.assertEquals(false, creepGame.ants[1].getDirection());
        Assert.assertEquals(true, creepGame.ants[2].getDirection());
    }
}