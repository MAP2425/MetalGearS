package org.it.uniba.fox.Entity;

public class Item {

    private String name;
    private String description;
    private boolean reusable=true;



    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean getReusable(){
        return this.reusable;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public void setReusable(boolean reusable) {
        this.reusable=reusable;
    }

}
