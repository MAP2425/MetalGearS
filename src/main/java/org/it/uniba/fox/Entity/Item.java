package org.it.uniba.fox.Entity;

import java.util.List;

/**
 * The class that represents an item.
 */
public class Item extends Agent {

    public Item(String name, String description, boolean reusable, boolean pickable, boolean talkable, List<String> aliases) {
        setName(name);
        setDescription(description);
        setReusable(reusable);
        setPickable(pickable);
        setTalkable(talkable);
        setAliases(aliases);
    }

    public Item() {
        // Default constructor
    }

    /**
     * Prints the description of the item.
     */
    @Override
    public void getDescription(Room room) {
        String name = getName();
        // descrizione non ancora implementata
    }
}
