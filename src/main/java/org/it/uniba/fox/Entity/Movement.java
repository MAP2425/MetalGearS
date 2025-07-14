package org.it.uniba.fox.Entity;

import org.it.uniba.fox.Type.CommandType;

/**
 * Represents a movement action in the game, containing information about the current room,
 * the next room, the direction of movement, and whether the next room is locked.
 */
public class Movement {

    /**
     * The current room where the movement starts.
     */
    private Room currentRoom;

    /**
     * The room to which the movement is directed.
     */
    private Room nextRoom;

    /**
     * The direction of the movement, represented by a command type.
     */
    private CommandType direction;

    /**
     * Indicates whether the next room is locked.
     */
    private boolean nextLocked;

    /**
     * Returns the current room.
     *
     * @return the current room
     */
    private Room getCurrentRoom(){
        return this.currentRoom;
    }

    /**
     * Returns the direction of the movement.
     *
     * @return the direction as a CommandType
     */
    private CommandType getDirection(){
        return this.direction;
    }

    /**
     * Returns the next room.
     *
     * @return the next room
     */
    private Room getNextRoom(){
        return  this.nextRoom;
    }

    /**
     * Returns whether the next room is locked.
     *
     * @return true if the next room is locked, false otherwise
     */
    private boolean isNextLocked(){
        return this.nextLocked;
    }

}
