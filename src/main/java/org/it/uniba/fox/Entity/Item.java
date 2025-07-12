package org.it.uniba.fox.Entity;

import java.util.List;

public class Item {

    private String name;
    private String description;
    private boolean reusable=true;
    private boolean isPicked=false;
    private List<String> aliases;




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


    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }


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
