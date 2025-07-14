package org.it.uniba.fox.Type;

/**
 * The enum representing the command types in the game.
 */
public enum CommandType {
    /**
     * Codec command type, shows the list of available commands.
     */
    CODEC,

    /**
     * Nord command type, moves the player to the north room.
     */
    NORD,

    /**
     * Sud command type, moves the player to the south room.
     */
    SUD,

    /**
     * Est command type, moves the player to the east room.
     */
    EST,

    /**
     * Ovest command type, moves the player to the west room.
     */
    OVEST,

    /**
     * Observe command type, allows the player to observe objects, rooms, and Agents.
     */
    OSSERVA,

    /**
     * Inventory command type, lists the objects in the player's inventory.
     */
    INVENTARIO,

    /**
     * Take command type, allows the player to pick up objects.
     */
    PRENDI,

    /**
     * Use command type, allows the player to use objects and use objects on other objects.
     */
    USA,

    /**
     * Talk command type, allows the player to talk to Agents.
     */
    PARLA,

    /**
     * Give command type, allows the player to give objects to Agents.
     */
    DAI,
}
