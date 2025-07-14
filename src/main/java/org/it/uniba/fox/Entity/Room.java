
package org.it.uniba.fox.Entity;
import org.it.uniba.fox.DB_Web.DatabaseConnection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a room in the game, containing its name, description, state (free or not),
 * the Agents and items present, and a static list of all rooms.
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
     * The list of Agents present in the room.
     */
    private List <Agent> Agents;

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
     * @param Agents the list of Agents in the room
     * @param items the list of items in the room
     */
    public Room(String name, String description, boolean free, List <Agent> Agents, List <Item> items){
        this.name=name;
        this.description=description;
        this.free = free;
        this.items = (items != null) ? items : new ArrayList<>();
        this.Agents = (Agents != null) ? Agents : new ArrayList<>();
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
     * Returns the list of Agents present in the room.
     *
     * @return the list of Agents
     */
    public List <Agent> getAgents(){
        return this.Agents;
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
     * Adds one or more Agents to the room.
     *
     * @param Agents the Agents to add
     */
    public void addAgents(Agent... Agents) {
        this.Agents.addAll(Arrays.asList(Agents));
    }


    /**
     * Removes a Agent from the room by name.
     *
     * @param name the name of the Agent to remove
     * @return true if the Agent was removed, false otherwise
     */
    public boolean removeAgent(String name){

        return this.Agents.removeIf(ch->ch.getName().equals(name));
    }


    /**
     * Removes a Agent from the room by name.
     *
     * @param name the name of the Agent to remove
     * @return true if the Agent was removed, false otherwise
     */
    public void removeItem(String name){

        this.items.removeIf(item -> item.getName().equals(name));
    }

    /**
     * Prints the description of the room.
     */
    public void printDescription() {
        DatabaseConnection.printFromDB("Osserva", name, String.valueOf(free), "0", "0");
    }
}
