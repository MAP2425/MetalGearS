package org.it.uniba.fox.Entity;
//import org.it.uniba.fox.DB_Web.DatabaseConnection;

/**
 * The class that represents an item.
 */
public class Item extends Agent{
    /**
     * Represents if the item is pickable.
     */
    private boolean isPickable;
    /**
     * Represents if the item is movable.
     */
    private boolean isMovable;

    /**
     * Prints the description of the item.
     */
    @Override
    public void getDescription(Room room) {
        String name = getName();
        if (isMovable) {
            //DatabaseConnection.printFromDB("Osserva", "0", "0", "0", name, "0");
        } else {
            //DatabaseConnection.printFromDB("Osserva", room.getName(), room.getState(), "0", name, "0");
        }
    }

    /**
     * Returns if the item is pickable.
     *
     * @return isPickable
     */
    public boolean isPickable() {
        return isPickable;
    }

    /**
     * Sets if the item is pickable.
     *
     * @param b if the item is pickable
     */
    public void setPickable(boolean b) {
        isPickable = b;
    }
}
