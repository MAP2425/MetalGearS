package org.it.uniba.fox.Entity;
import org.it.uniba.fox.GUI.GameGUI;
import org.it.uniba.fox.InteractionManager.OutputDisplayManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class that represents the game.
 */
public class Game {
    /**
     * The inventory of the player.
     */
    private List<Item> inventory;
    /**
     * The starting time of the game.
     */
    private String currentTime;
    /**
     * The current room in which the player is in.
     */
    private Room currentRoom;
    /**
     * The map of the corridors.
     */
    private List<Corridor> corridorsMap;
    /**
     * The map of the states of the rooms.
     */
    private Map<String, Boolean> freeMap;
    /**
     * The instance of the game.
     */
    private static Game game = new Game();

    /**
     * Constructor for Game class
     */
    public Game() {
        this.inventory = new ArrayList<>();
        this.corridorsMap = new ArrayList<>();
        this.freeMap = new HashMap<>();
    }

    /**
     * Sets up the instance of the game.
     *
     * @param game the game
     */
    public static void setUpGame(Game game) {
        Game.game = game;
        GameGUI.setImagePanel(game.getCurrentRoom().getName());
    }

    /**
     * Gets the instance of the game.
     *
     * @return the instance
     */
    public static Game getInstance() {
        return game;
    }

    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public List<Item> getInventory() {
        return game.inventory;
    }

    /**
     * Add an item to the inventory.
     *
     * @param item the item
     */
    public void addInventory(Item item) {
        game.inventory.add(item);
        List<String> itemsNames = game.inventory.stream().map(Item::getName).toList();
        String[] itemsNamesArray = itemsNames.toArray(new String[0]);
        GameGUI.updateInventoryTextArea(itemsNamesArray);
    }
    /**
     * Remove an item from the inventory.
     *
     * @param item the item
     */
    public void removeInventory(Item item) {
        game.inventory.remove(item);
        List<String> itemsNames = game.inventory.stream().map(Item::getName).toList();
        String[] itemsNamesArray = itemsNames.toArray(new String[0]);
        GameGUI.updateInventoryTextArea(itemsNamesArray);
    }

    /**
     * Print the inventory.
     */
    public void printInventory() {
        OutputDisplayManager.displayText("> Inventario: ");
        for (Item item : game.inventory) {
            OutputDisplayManager.displayText(">  - " + item.getName());
        }
    }

    /**
     * Gets current room.
     *
     * @return the current room
     */
    public Room getCurrentRoom() {
        return game.currentRoom;
    }

    /**
     * Sets current room.
     *
     * @param room the room
     */
    public void setCurrentRoom(Room room) {
        if (game.corridorsMap != null) {
            for (Corridor corridor : game.corridorsMap) {
                if (corridor.getStartingRoom().equals(room)) {
                    game.currentRoom = corridor.getStartingRoom();
                    GameGUI.setImagePanel(game.currentRoom.getName());
                    return;
                }
            }
        }
        game.currentRoom = room;
        GameGUI.setImagePanel(game.currentRoom.getName());
    }

    /**
     * Gets the starting time.
     *
     * @return the starting time
     */
    public String getCurrentTime() {
        return game.currentTime;
    }

    /**
     * Sets starting time.
     *
     * @param currentTime the starting time
     */
    public void setCurrentTime(String currentTime) {
        game.currentTime = currentTime;
    }

    /**
     * Gets corridors map.
     *
     * @return the corridors map
     */
    public List<Corridor> getCorridorsMap() {
        return game.corridorsMap;
    }

    /**
     * Sets corridors map.
     *
     * @param corridorsMap the corridors map
     */
    public void setCorridorsMap(List<Corridor> corridorsMap) {
        game.corridorsMap = corridorsMap;
    }

    /**
     * Unlocks a corridor.
     *
     * @param r1 the starting room
     * @param r2 the arriving room
     */
    public void unlockCorridor(String r1, String r2) {
        for (Corridor corridor : game.corridorsMap) {
            if (corridor.getStartingRoom().getName().equals(r1) && corridor.getArrivingRoom().getName().equals(r2)) {
                corridor.setLocked(false);
            }
        }
    }

    /**
     * Gets room state.
     *
     * @param room the room
     * @return the room state
     */
    public boolean getRoomState(String room) {
        return game.freeMap.get(room);
    }

    /**
     * Sets room state.
     *
     * @param room  the room
     * @param isFree the state of the room
     */
    /**
     * Sets room state.
     *
     * @param room  the room
     * @param isFree the state of the room
     */
    public void setRoomState(String room, Boolean isFree) {
        // Inizializza freeMap se è null
        if (game.freeMap == null) {
            game.freeMap = new HashMap<>();
        }

        game.freeMap.put(room, isFree);

        // Aggiorna lo stato della stanza nei corridoi se necessario
        if (game.corridorsMap != null) {
            game.corridorsMap.stream()
                    .filter(corridor -> corridor.getStartingRoom().getName().equals(room))
                    .forEach(corridor -> corridor.getStartingRoom().setFree(isFree));
        }
    }

}
