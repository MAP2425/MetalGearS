package org.it.uniba.fox.Entity;
import java.util.List;

public class Room {

    private String name;
    private String description;
    private boolean free;
    private List <Character> characters;
    private List <Item> items;
    private List <Command> commands;

    public Room(String name, String description, boolean free, List <Character> characters, List <Item> items, List <Command> commands){
        this.name=name;
        this.description=description;
        this.free = free;
        this.items=items;
        this.characters=characters;
        this.commands=commands;
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

    public void setName(String name){
        this.name=name;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public void setFree(boolean free){
        this.free = free;
    }

    public void setItems(List <Item> items){
        this.items=items;
    }

    public void addItems(Item item){
        this.items.add(item);
    }

    public void setCharacters(List <Character> characters){
        this.characters=characters;
    }

    public void addCharacters(Character character){
        this.characters.add(character);
    }

    public void addCommand(Command command){
        this.commands.add(command);
    }

    public List <Command> getCommands(){
        return this.commands;
    }

    public void setCommands(List <Command> commands){
        this.commands=commands;
    }
    public boolean removeCommand(String name){
        return this.commands.removeIf(cmd->cmd.getName().equals(name));
    }

    public boolean removeCharacter(String name){

       return this.characters.removeIf(ch->ch.getName().equals(name));
    }

    public boolean removeItem(String name){

        return this.items.removeIf(item->item.getName().equals(name));
    }

    public void printDescription() {
        //DatabaseConnection.printFromDB("Osserva", name, currentState, "0", "0", "0");
    }
}
