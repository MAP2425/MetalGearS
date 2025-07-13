
package org.it.uniba.fox.Entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {

    private String name;
    private String description;
    private boolean free;
    private List <Character> characters;
    private List <Item> items;
    public static List<Room> allRooms = new ArrayList<>();


    public Room(String name, String description, boolean free, List <Character> characters, List <Item> items){
        this.name=name;
        this.description=description;
        this.free = free;
        this.items = (items != null) ? items : new ArrayList<>();
        this.characters = (characters != null) ? characters : new ArrayList<>();
    }


    public static Room getRoomByName(String position) {
        for (Room room : allRooms) {
            if (room.getName().equalsIgnoreCase(position)) {
                return room;
            }
        }
        return null;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public List <Item> getItems(){
        return this.items;
    }

    public List <Character> getCharacters(){
        return this.characters;
    }

    public boolean getFree(){
        return this.free;
    }


    public void setFree(boolean free){
        this.free = free;
    }


    public void addItems(Item... items) {
        this.items.addAll(Arrays.asList(items));
    }

    public void addCharacters(Character... characters) {
        this.characters.addAll(Arrays.asList(characters));
    }


    public boolean removeCharacter(String name){

        return this.characters.removeIf(ch->ch.getName().equals(name));
    }

    public void removeItem(String name){

        this.items.removeIf(item -> item.getName().equals(name));
    }

    public void printDescription() {
        //DatabaseConnection.printFromDB("Osserva", name, currentState, "0", "0", "0");
    }
}