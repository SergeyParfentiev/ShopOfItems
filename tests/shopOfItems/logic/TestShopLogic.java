package shopOfItems.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import shopOfItems.DBManagers.DBManagementSystem;
import shopOfItems.tools.audioPlayer.AudioPlayer;


import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class TestShopLogic {
    ShopOfItems shopOfItems;
    AudioPlayer audioPlayer;
    DBManagementSystem mockDB;

    @Before
    public void init() {
        audioPlayer = new AudioPlayer();
        shopOfItems = new ShopOfItems(audioPlayer);
        mockDB = mock(DBManagementSystem.class);
        shopOfItems.setManagementSystem(mockDB);
    }

    @Test
    public void correctAddItemMockTest() {
        assertTrue(shopOfItems.addItem("Duck", "2", "20"));
    }

    @Test
    public void noCorrectAddItemMockTest() {
        assertFalse(shopOfItems.addItem("", "2", "20"));
    }

    @Test
    public void correctAddCustomerMockTest() {
        assertTrue(shopOfItems.addCustomer("Kolya", "Pepin", "18", 1, "Marsianina st, 8", "(067)456-78-90"));
    }

    @Test
    public void noCorrectAddCustomerMockTest() {
        assertFalse(shopOfItems.addCustomer("", "", "", 3, "", ""));
    }
}
