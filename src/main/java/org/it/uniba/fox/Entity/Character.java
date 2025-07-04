package org.it.uniba.fox.Entity;

public class Character extends Item{

     Room position;

     public Room getPosition(){

         return this.position;
     }

     public void setPosition(Room position){
         this.position=position;
     }



}
