package org.it.uniba.fox.Util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.it.uniba.fox.Entity.Corridor;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Room;
import org.it.uniba.fox.Entity.Agent;
import org.it.uniba.fox.Logic.GameManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The class that manages the conversion of json files to java classes and vice versa.
 */
public class Converter {

    /**
     * Method that manages the conversion of json files to java classes in the case of a new game.
     *
     * @return the map of all the agents
     */
    public Map<String, Item> convertJsonToJavaClass() {
        ensureResourceDirectoriesExist();
        return processJsonFiles("src/main/resources/static/Game.json", "src/main/resources/static/Agents.json");

    }

    /**
     * Method that manages the conversion of json files to java classes in the case of a loaded game.
     *
     * @return the map of all the agents
     */
    public Map<String, Item> loadGame() {
        ensureResourceDirectoriesExist();
        return processJsonFiles("src/main/resources/LoadedGame.json", "src/main/resources/LoadedAgents.json");
    }

    /**
     * Ensures that the necessary resource directories and files exist.
     * If they do not exist, they are created with default content.
     */
    private void ensureResourceDirectoriesExist() {
        try {
            Path staticDir = Paths.get("src", "main", "resources", "static");
            if (!Files.exists(staticDir)) {
                Files.createDirectories(staticDir);
            }
            Path gameJson = staticDir.resolve("Game.json");
            Path itemsJson = staticDir.resolve("Agents.json");
            if (!Files.exists(gameJson)) {
                Files.createFile(gameJson);
                Files.write(gameJson, "{}".getBytes());
            }
            if (!Files.exists(itemsJson)) {
                Files.createFile(itemsJson);
                Files.write(itemsJson, "[]".getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore creazione risorse", e);
        }
    }

    /**
     * Converts the json files of a game and its agents to java classes.
     * Returns a map containing all the agents mapped to their names.
     *
     * @param gameFilePath   the game file path
     * @param itemsFilePath the agents file path
     * @return the map of the agents
     */
    private Map<String, Item> processJsonFiles(String gameFilePath, String itemsFilePath) {
        Gson gson = new Gson();
        Map<String, Item> allItems = new HashMap<>();
        Map<String, Room> allRooms = new HashMap<>();

        // Lettura Game.json
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(gameFilePath));
            if (fileBytes.length == 0) {
                return allItems;
            }
            JsonReader reader = new JsonReader(new FileReader(gameFilePath));
            Game game = gson.fromJson(reader, Game.class);
            if (game == null) {
                return allItems;
            }
            Game.setUpGame(game);
            if (game.getInventory() != null) {
                game.getInventory().forEach(item -> allItems.put(item.getName(), item));
            }
            if (game.getCorridorsMap() != null) {
                game.getCorridorsMap().forEach(corridor -> {
                    Room room = corridor.getStartingRoom();
                    if (!allRooms.containsKey(room.getName())) {
                        allRooms.put(room.getName(), room);
                        if (room.getItems() != null) {
                            room.getItems().forEach(Item -> allItems.put(Item.getName(), Item));
                        }
                    } else {
                        Room existingRoom = allRooms.get(room.getName());
                        corridor.setStartingRoom(existingRoom);
                    }
                    room = corridor.getArrivingRoom();
                    if (!allRooms.containsKey(room.getName())) {
                        allRooms.put(room.getName(), room);
                        if (room.getItems() != null) {
                            room.getItems().forEach(Item -> allItems.put(Item.getName(), Item));
                        }
                    } else {
                        Room existingRoom = allRooms.get(room.getName());
                        corridor.setArrivingRoom(existingRoom);
                    }
                });
            }
            if (game.getCurrentRoom() != null) {
                game.setCurrentRoom(allRooms.get(game.getCurrentRoom().getName()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(itemsFilePath));
            if (fileBytes.length == 0) {
                return allItems;
            }
            JsonArray array = JsonParser.parseReader(new FileReader(itemsFilePath)).getAsJsonArray();
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String type = obj.get("type").getAsString();
                Item item;
                if ("Agent".equals(type)) {
                    item = gson.fromJson(obj, Agent.class);
                } else if ("Item".equals(type)) {
                    item = gson.fromJson(obj, Item.class);
                } else {
                    continue;
                }
                allItems.put(item.getName(), item);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(() -> printgameFiles()).start();

        return allItems;
    }
    /**
     * Prints the details of the game, including the current room, inventory, rooms, corridors, all items, and Agents.
     */
    private void printgameFiles() {
        Game game = Game.getInstance();
        if (game == null) {
            System.out.println("\n======= ERRORE: ISTANZA DI GAME NON TROVATA =======");
            return;
        }

        System.out.println("\n================== DETTAGLI DEL GIOCO ================");
        Room currentRoom = game.getCurrentRoom();
        System.out.println("Stanza corrente: " +
                (currentRoom != null ? currentRoom.getName() : "Nessuna"));

        System.out.println("\n--- INVENTARIO ---");
        List<Item> inventory = game.getInventory();
        if (inventory == null || inventory.isEmpty()) {
            System.out.println("L’inventario è vuoto.");
        } else {
            System.out.println("Numero oggetti nell’inventario: " + inventory.size());
            for (Item item : inventory) {
                System.out.println("  - Nome: " + item.getName());
            }
        }

        System.out.println("\n--- STANZE E CORRIDOI ---");
        List<Corridor> corridors = game.getCorridorsMap();
        if (corridors == null || corridors.isEmpty()) {
            System.out.println("Non ci sono corridoi nel gioco.");
        } else {
            Set<Room> rooms = corridors.stream()
                    .map(Corridor::getStartingRoom)
                    .collect(Collectors.toSet());
            rooms.addAll(corridors.stream()
                    .map(Corridor::getArrivingRoom)
                    .collect(Collectors.toSet()));
            System.out.println("\nSTANZE (" + rooms.size() + "):");
            for (Room room : rooms) {
                if (room == null) {
                    System.out.println("  [Stanza NULL]");
                    continue;
                }
                System.out.println("  - Nome: " + room.getName());
                List<Item> roomItems = room.getItems();
                List<Agent> agentsInRoom = roomItems == null ? Collections.emptyList() :
                        roomItems.stream()
                                .filter(item -> item instanceof Agent)
                                .map(item -> (Agent) item)
                                .collect(Collectors.toList());
                if (agentsInRoom.isEmpty()) {
                    System.out.println("    Personaggi: Nessuno");
                } else {
                    System.out.println("    Personaggi (" + agentsInRoom.size() + "):");
                    for (Agent agent : agentsInRoom) {
                        System.out.println("      - Nome: " + agent.getName());
                        System.out.println("        Descrizione: " +
                                (agent.getDescription() != null ? agent.getDescription() : "N/D"));
                    }
                }
            }
            System.out.println("\nCORRIDOI (" + corridors.size() + "):");
            for (int i = 0; i < corridors.size(); i++) {
                Corridor c = corridors.get(i);
                System.out.println("  Corridoio #" + (i + 1));
                System.out.println("    Da: " +
                        (c.getStartingRoom() != null ? c.getStartingRoom().getName() : "NULL"));
                System.out.println("    A: " +
                        (c.getArrivingRoom() != null ? c.getArrivingRoom().getName() : "NULL"));
                System.out.println("    Direzione: " + c.getDirection());
            }
        }

        System.out.println("\n--- TUTTI GLI OGGETTI ---");
        Set<Item> allItems = new GameManager().getAllItems();
        if (allItems == null || allItems.isEmpty()) {
            System.out.println("Non ci sono oggetti nel gioco.");
        } else {
            System.out.println("Numero totale oggetti: " + allItems.size());
            for (Item it : allItems) {
                System.out.println("  - Nome: " + it.getName());
                System.out.println("    Descrizione: " +
                        (it.getDescription() != null ? it.getDescription() : "N/D"));
                System.out.println("Raccolto  " + it.getPicked());
            }


        System.out.println("\n--- PERSONAGGI ---");
        List<Agent> Agents = allItems.stream()
                .filter(i -> i instanceof Agent)
                .map(i -> (Agent) i)
                .collect(Collectors.toList());
        if (Agents.isEmpty()) {
            System.out.println("Non ci sono personaggi nel gioco.");
        } else {
            System.out.println("Numero personaggi: " + Agents.size());
            for (Agent ch : Agents) {
                System.out.println("  - Nome: " + ch.getName());
                System.out.println("    Descrizione: " +
                        (ch.getDescription() != null ? ch.getDescription() : "N/D"));
                System.out.println();
            }
        }

        System.out.println("=======================================================");
    }
    }

    /**
     * Converts the game instance to json file to save the game.
     */
    public void ConvertGameToJson() {
        Gson gson = new Gson();
        Game game = Game.getInstance();
        String json = gson.toJson(game);

        try {
            Files.write(Paths.get("src", "main", "resources", "LoadedGame.json"), json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts the items to json file to save the game.
     */
    public void ConvertItemsToJson() {
        Gson gson = new Gson();
        Game game = Game.getInstance();
        GameManager gameManager = new GameManager();
        Set<Item> allItems = gameManager.getAllItems();

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
            Files.write(Paths.get("src", "main", "resources", "LoadedItems.json"), json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
