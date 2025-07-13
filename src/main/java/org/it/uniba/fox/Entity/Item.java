package org.it.uniba.fox.Entity;

import java.util.List;

public class Item {

    private String name;
    private String description;
    private boolean reusable=true;
    private boolean isPicked= false;
    private boolean isPickable=true;
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

    public boolean isPickable() {
        return isPickable;
    }

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

    public void setPickable(boolean b) {
        isPickable = b;
    }

    public Item(String name, String description, boolean reusable, boolean isPicked, boolean isPickable, List<String> aliases) {
        this.name = name;
        this.description = description;
        this.reusable = reusable;
        this.isPicked = isPicked;
        this.isPickable = isPickable;
        this.aliases = aliases;
    }

    public Item() {
        // Default constructor for inizializer
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return name != null && name.equalsIgnoreCase(item.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.toLowerCase().hashCode() : 0;
    }
}
