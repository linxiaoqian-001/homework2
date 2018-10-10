package ants;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayRoomTest {

    private PlayRoom playRoom = new PlayRoom();

    @Before
    public void setUp() throws Exception {
        PlayRoom.initDirections();
        playRoom.setDirs(5);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setDirs() {
        Assert.assertEquals(false, playRoom.creepGame.ants[0].getDirection());
        Assert.assertEquals(false, playRoom.creepGame.ants[1].getDirection());
        Assert.assertEquals(true, playRoom.creepGame.ants[2].getDirection());
        Assert.assertEquals(false, playRoom.creepGame.ants[3].getDirection());
        Assert.assertEquals(true, playRoom.creepGame.ants[4].getDirection());
    }
}