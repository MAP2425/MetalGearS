package org.it.uniba.fox.Entity;

import java.util.List;

public class Character extends Item{

     Room position;

     public Room getPosition(){

         return this.position;
     }

     public void setPosition(Room position){
         this.position=position;
     }

     public Character(String name, String description, boolean reusable, boolean isPicked, List<String> aliases, String roomName) {
        super(name, description, reusable, isPicked, aliases);
        this.position = null; // La posizione può essere risolta successivamente tramite il nome stanza
    }

}
