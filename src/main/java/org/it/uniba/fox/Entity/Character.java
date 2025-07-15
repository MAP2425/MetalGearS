package org.it.uniba.fox.Entity;

import org.it.uniba.fox.DB_Web.DatabaseConnection;

import java.util.Arrays;

/**
 * The class that represents a personage.
 */
public class Character extends Agent {
    /**
     * Prints the description of the personage.
     *
     * @param room the room
     */
    @Override
    public void getDescription(Room room) {
        String name = getName();
        DatabaseConnection.printFromDB("Osserva", room.getName(), Arrays.toString(new boolean[]{room.getFree()}), name , "0");
    }
}
