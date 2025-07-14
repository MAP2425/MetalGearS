package org.it.uniba.fox.Entity;

import java.util.List;

/**
 * The class that represents a character of the game.
 */
public class Character extends Item{

    /**
     * The room where the character is currently located.
     */
     Room position;
    /**
     * Returns the room where the character is currently located.
     *
     * @return the current room of the character
     */
     public Room getPosition(){

         return this.position;
     }

    /**
     * Sets the character's position using the room name.
     *
     * @param position the name of the room to set as the character's position
     */
     public void setPosition(String position){
            this.position = Room.getRoomByName(position);
     }

    /**
     * Constructs a Character with the specified attributes.
     *
     * @param name        the name of the character
     * @param description the description of the character
     * @param reusable    whether the object is reusable
     * @param isPicked    whether the object has been picked up
     * @param isPickable  whether the object can be picked up
     * @param aliases     list of aliases for the character
     * @param roomName    the initial room name of the character
     */
     public Character(String name, String description, boolean reusable, boolean isPicked, boolean isPickable,  List<String> aliases, String roomName) {
        super(name, description, reusable, isPicked, isPickable, aliases);
        this.position = null; // La posizione può essere risolta successivamente tramite il nome stanza
    }

    /**
     * Default constructor for Character.
     */
    public Character(){}

}
