package org.it.uniba.fox.Entity;

import java.util.List;

/**
 * Represents an Item in the game.
 */
public class Item {

    /**
     * The type of the item.
     */
    private String type;
    /**
     * The name of the item.
     */
    private String name;

    /**
     * The description of the item.
     */
    private String description;

    /**
     * Indicates whether the item is reusable.
     */
    private boolean reusable=true;

    /**
     * Indicates whether the item has been picked up.
     */
    private boolean isPicked= false;

    /**
     * Indicates whether the item can be picked up.
     */
    private boolean isPickable=true;

    /**
     * The list of aliases for the item.
     */
    private List<String> aliases;


    /**
     * Returns the name of the item.
     *
     * @return the name of the item
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the description of the item.
     *
     * @return the description of the item
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Returns whether the item is reusable.
     *
     * @return true if the item is reusable, false otherwise
     */
    public boolean getReusable(){
        return this.reusable;
    }

    /**
     * Returns whether the item has been picked up.
     *
     * @return true if the item has been picked up, false otherwise
     */
    public boolean getPicked(){ return this.isPicked;}

    /**
     * Returns whether the item can be picked up.
     *
     * @return true if the item can be picked up, false otherwise
     */
    public boolean isPickable() {
        return isPickable;
    }

    /**
     * Returns the list of aliases for the item.
     *
     * @return the list of aliases
     */
    public List<String> getAliases() {
        return aliases;
    }

    /**
     * Sets the list of aliases for the item.
     * @param aliases the list of aliases to set
     */
    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    /**
     * Checks if the item has a specific name.
     * @param name the name to check
     * @return true if the item has the specified name, false otherwise
     */
    public boolean hasName(String name) {
        return this.getName().equals(name);
    }

    /**
     * Sets the name of the item.
     * @param name the name to set
     */
    public void setName(String name){
        this.name=name;
    }

    /**
     * Sets the description of the item.
     * @param description the description to set
     */
    public void setDescription(String description){
        this.description=description;
    }

    /**
     * Sets whether the item is reusable.
     * @param reusable true if the item is reusable, false otherwise
     */
    public void setReusable(boolean reusable) {
        this.reusable=reusable;
    }

    /**
     * Sets whether the item has been picked up.
     * @param isPicked true if the item has been picked up, false otherwise
     */
    public void setPicked(boolean isPicked) {
        this.isPicked=isPicked;
    }

    /**
     * Sets whether the item can be picked up.
     * @param b true if the item can be picked up, false otherwise
     */
    public void setPickable(boolean b) {
        isPickable = b;
    }

    /**
     * Constructs an Item with the specified attributes.
     * @param name the name of the item
     * @param description the description of the item
     * @param reusable whether the item is reusable
     * @param isPicked whether the item has been picked up
     * @param isPickable whether the item can be picked up
     * @param aliases list of aliases for the item
     */
    public Item(String name, String description, boolean reusable, boolean isPicked, boolean isPickable, List<String> aliases) {
        this.name = name;
        this.description = description;
        this.reusable = reusable;
        this.isPicked = isPicked;
        this.isPickable = isPickable;
        this.aliases = aliases;
    }

    /**
     * Default constructor for Item.
     * This constructor is used by the initializer.
     */
    public Item() {
        // Default constructor for inizializer
    }


    public void setType(String type) {
        this.type = type;
    }

    /**
     * Checks if this item is equal to another object.
     * @param obj the object to compare with
     * @return true if the object is an Item with the same name (case-insensitive), false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Item item = (Item) obj;
        return name != null && name.equalsIgnoreCase(item.name);
    }

    /**
     * Returns the hash code for this item.
     * The hash code is based on the lowercase name of the item.
     * @return the hash code of the item
     */
    @Override
    public int hashCode() {
        return name != null ? name.toLowerCase().hashCode() : 0;
    }
}
