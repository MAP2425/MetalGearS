package org.it.uniba.fox.Entity;
//import org.it.uniba.fox.DB_Web.DatabaseConnection;

/**
 * The class that represents a personage.
 */
public class Personage extends Agent {
    /**
     * Prints the description of the personage.
     *
     * @param room the room
     */
    public void getDescription(Room room) {
        String name = getName();
        //DatabaseConnection.printFromDB("Osserva", room.getName(), room.getState(), name, "0", "0");
    }
}
