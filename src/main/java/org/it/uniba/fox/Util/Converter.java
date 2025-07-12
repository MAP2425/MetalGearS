package org.it.uniba.fox.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.it.uniba.fox.Entity.Corridor;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Room;
import org.it.uniba.fox.Logic.GameManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * The class that manages the conversion of json files to java classes and vice versa.
 */
public class Converter {
    /**
     * Method that manages the conversion of json files to java classes in the case of a new game.
     *
     * @return the map of all the Items
     */
    public Map<String, Item> convertJsonToJavaClass() {
        return processJsonFiles("src/main/resources/static/Game.json", "src/main/resources/static/Items.json");
    }

    /**
     * Method that manages the conversion of json files to java classes in the case of a loaded game.
     *
     * @return the map of all the Items
     */
    public Map<String, Item> loadGame() {
        return processJsonFiles("src/main/resources/LoadedGame.json", "src/main/resources/LoadedItems.json");
    }

    /**
     * Converts the json files of a game and its Items to java classes.
     * Returns a map containing all the Items mapped to their names.
     *
     * @param gameFilePath   the game file path
     * @param ItemsFilePath the Items file path
     * @return the map of the Items
     */
    private Map<String, Item> processJsonFiles(String gameFilePath, String ItemsFilePath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Item.class, new CharacterDeserializer())
                .create();
        Map<String, Item> allItems = new HashMap<>();
        Map<String, Room> allRooms = new HashMap<>();

        // Read the game file
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(gameFilePath));
            if (fileBytes.length == 0) {
                return null;
            }
            File file = new File(gameFilePath);
            JsonReader reader = new JsonReader(new FileReader(file));
            Game game = gson.fromJson(reader, Game.class);
            if (game == null) {
                return null;
            }
            Game.setUpGame(game);
            game.getInventory().forEach(item -> allItems.put(item.getName(), item));
            game.getCorridorsMap().forEach(corridor -> {
                Room room = corridor.getStartingRoom();
                if (!allRooms.containsKey(room.getName())) {
                    allRooms.put(room.getName(), room);
                    room.getItems().forEach(Item -> allItems.put(Item.getName(), Item));
                } else {
                    Room existingRoom = allRooms.get(room.getName());
                    corridor.setStartingRoom(existingRoom);
                }
                room = corridor.getArrivingRoom();
                if (!allRooms.containsKey(room.getName())) {
                    allRooms.put(room.getName(), room);
                    room.getItems().forEach(Item -> allItems.put(Item.getName(), Item));
                } else {
                    Room existingRoom = allRooms.get(room.getName());
                    corridor.setArrivingRoom(existingRoom);
                }
            });

            game.setCurrentRoom(allRooms.get(game.getCurrentRoom().getName()));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read the Items file
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(ItemsFilePath));
            if (fileBytes.length == 0) {
                return null;
            }
            File file = new File(ItemsFilePath);
            JsonReader reader = new JsonReader(new FileReader(file));
            Type ItemListType = new TypeToken<ArrayList<Item>>() {}.getType();
            List<Item> ItemList = gson.fromJson(reader, ItemListType);
            ItemList.forEach(Item -> allItems.put(Item.getName(), Item));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allItems;
    }

    /**
     * Converts the game instance to json file to save the game.
     */
    public void ConvertGameToJson() {
        Gson gson = new Gson();
        Game game = Game.getInstance();
        String json = gson.toJson(game);

        try {
            Files.write(Paths.get("src/main/resources/LoadedGame.json"), json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts the Items to json file to save the game.
     */
    public void ConvertItemsToJson() {
        Gson gson = new Gson();
        Game game = Game.getInstance();
        GameManager gameManager = new GameManager();
        Set<Item> allItems = gameManager.getAllItems();

        // Save only the items that are not in the inventory or in a room
        Set<Room> rooms = game.getCorridorsMap().stream()
                .map(Corridor::getStartingRoom)
                .collect(Collectors.toSet());

        Set<Item> itemsToSave = allItems.stream()
                .filter(item -> !game.getInventory().contains(item))
                .filter(item -> rooms.stream()
                        .noneMatch(room -> room.getItems().contains(item)))
                .collect(Collectors.toSet());

        String json = gson.toJson(itemsToSave);
        try {
            Files.write(Paths.get("src/main/resources/LoadedItems.json"), json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
