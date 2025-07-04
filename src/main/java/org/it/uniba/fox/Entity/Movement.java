package org.it.uniba.fox.Entity;

import org.it.uniba.fox.Type.CommandType;

public class Movement {



    private Room currentRoom;
    private Room nextRoom;
    private CommandType direction;
    private boolean nextLocked;

    private Room getCurrentRoom(){
        return this.currentRoom;
    }

    private CommandType getDirection(){
        return this.direction;
    }

    private Room getNextRoom(){
        return  this.nextRoom;
    }

    private boolean isNextLocked(){
        return this.nextLocked;
    }

}
