package com.noname.mrch;

import com.badlogic.gdx.scenes.scene2d.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Room Manager Unit Test
 */
public class RoomManagerTest {
    RoomManager roomManager;
    Stage testStage;

    @Before
    public void setUp() throws Exception {
//        roomManager = RoomManager.getInstance();

        testStage = new Stage();
    }

    @After
    public void tearDown() throws Exception {
        roomManager = null;
        testStage = null;
    }

    @Test
    public void getRoomArrayTest() throws Exception {
        assertNotNull(roomManager.getRoomArray());
    }

}