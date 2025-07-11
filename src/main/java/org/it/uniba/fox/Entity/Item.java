package org.it.uniba.fox.Entity;

public class Item {

    private String name;
    private String description;
    private boolean reusable=true;
    private boolean isPicked=false;



    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean getReusable(){
        return this.reusable;
    }

    public boolean getPicked(){ return this.isPicked;}


    public boolean hasName(String name) {
        return this.getName().equals(name);
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

    public void setPicked(boolean isPicked) {
        this.isPicked=isPicked;
    }



}
