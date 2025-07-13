package org.it.uniba.fox.Entity;

import java.util.List;

public class Character extends Item{

     Room position;

     public Room getPosition(){

         return this.position;
     }

     public void setPosition(String position){
            this.position = Room.getRoomByName(position);
     }

     public Character(String name, String description, boolean reusable, boolean isPicked, boolean isPickable,  List<String> aliases, String roomName) {
        super(name, description, reusable, isPicked, isPickable, aliases);
        this.position = null; // La posizione può essere risolta successivamente tramite il nome stanza
    }

    public Character(){}

}
