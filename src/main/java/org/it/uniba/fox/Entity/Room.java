
package org.it.uniba.fox.Entity;
import org.it.uniba.fox.DB_Web.DatabaseConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a room in the game, containing its name, description, state (free or not),
 * the characters and items present, and a static list of all rooms.
 */
public class Room {


    /**
     * The name of the room.
     */
    private String name;


    /**
     * The description of the room.
     */
    private String description;

    /**
     * Indicates whether the room is free (unmonitored).
     */
    private boolean free;

    /**
     * The list of characters present in the room.
     */
    private List <Character> characters;

    /**
     * The list of items present in the room.
     */
    private List <Item> items;

    /**
     * Static list containing all rooms in the game.
     */
    public static List<Room> allRooms = new ArrayList<>();

    /**
     * Constructs a Room with the specified attributes.
     *
     * @param name the name of the room
     * @param description the description of the room
     * @param free whether the room is free (accessible)
     * @param characters the list of characters in the room
     * @param items the list of items in the room
     */
    public Room(String name, String description, boolean free, List <Character> characters, List <Item> items){
        this.name=name;
        this.description=description;
        this.free = free;
        this.items = (items != null) ? items : new ArrayList<>();
        this.characters = (characters != null) ? characters : new ArrayList<>();
    }

    /**
     * Returns the room with the specified name from the list of all rooms.
     *
     * @param position the name of the room to search for
     * @return the Room object if found, null otherwise
     */
    public static Room getRoomByName(String position) {
        for (Room room : allRooms) {
            if (room.getName().equalsIgnoreCase(position)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Returns the name of the room.
     *
     * @return the name of the room
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the description of the room.
     *
     * @return the description of the room
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Returns the list of items present in the room.
     *
     * @return the list of items
     */
    public List <Item> getItems(){
        return this.items;
    }

    /**
     * Returns the list of characters present in the room.
     *
     * @return the list of characters
     */
    public List <Character> getCharacters(){
        return this.characters;
    }

    /**
     * Returns whether the room is free.
     *
     * @return true if the room is free, false otherwise
     */
    public boolean getFree(){
        return this.free;
    }


    /**
     * Sets whether the room is free.
     *
     * @param free true if the room is free, false otherwise
     */
    public void setFree(boolean free){
        this.free = free;
    }

    /**
     * Adds one or more items to the room.
     *
     * @param items the items to add
     */
    public void addItems(Item... items) {
        this.items.addAll(Arrays.asList(items));
    }

    /**
     * Adds one or more characters to the room.
     *
     * @param characters the characters to add
     */
    public void addCharacters(Character... characters) {
        this.characters.addAll(Arrays.asList(characters));
    }


    /**
     * Removes a character from the room by name.
     *
     * @param name the name of the character to remove
     * @return true if the character was removed, false otherwise
     */
    public boolean removeCharacter(String name){

        return this.characters.removeIf(ch->ch.getName().equals(name));
    }


    /**
     * Removes a character from the room by name.
     *
     * @param name the name of the character to remove
     * @return true if the character was removed, false otherwise
     */
    public void removeItem(String name){

        this.items.removeIf(item -> item.getName().equals(name));
    }

    /**
     * Prints the description of the room.
     */
    public void printDescription() {
        DatabaseConnection.printFromDB("Osserva", name, "Libero", "0", "0");
    }
}
