package org.it.uniba.fox.Util;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.it.uniba.fox.Entity.Character;
import org.it.uniba.fox.Entity.Corridor;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Room;
import org.it.uniba.fox.Entity.Agent;
import org.it.uniba.fox.Logic.GameManager;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Objects;

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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Agent.class, new AgentDeserializer())
                .create();

        Map<String, Item> allItems = new HashMap<>();
        Map<String, Room> allRooms = new HashMap<>();

        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(gameFilePath));
            if (fileBytes.length == 0) return allItems;

            JsonReader reader = new JsonReader(new FileReader(gameFilePath));
            Game game = gson.fromJson(reader, Game.class);
            if (game == null) return allItems;

            Game.setUpGame(game);

            if (game.getInventory() != null) {
                game.getInventory().forEach(item -> allItems.put(item.getName(), item));
            }

            if (game.getCorridorsMap() != null) {
                game.getCorridorsMap().forEach(corridor -> {
                    Room start = corridor.getStartingRoom();
                    Room end = corridor.getArrivingRoom();

                    if (!allRooms.containsKey(start.getName())) {
                        allRooms.put(start.getName(), start);
                        if (start.getItems() != null) {
                            start.getItems().forEach(item -> allItems.put(item.getName(), item));
                        }
                    } else {
                        corridor.setStartingRoom(allRooms.get(start.getName()));
                    }

                    if (!allRooms.containsKey(end.getName())) {
                        allRooms.put(end.getName(), end);
                        if (end.getItems() != null) {
                            end.getItems().forEach(item -> allItems.put(item.getName(), item));
                        }
                    } else {
                        corridor.setArrivingRoom(allRooms.get(end.getName()));
                    }
                });
            }

            if (game.getCurrentRoom() != null) {
                game.setCurrentRoom(allRooms.get(game.getCurrentRoom().getName()));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Items.json: solo se ti serve farci qualcosa
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(itemsFilePath));
            if (fileBytes.length > 0) {
                JsonArray array = JsonParser.parseReader(new FileReader(itemsFilePath)).getAsJsonArray();
                for (JsonElement elem : array) {
                    Agent agent = gson.fromJson(elem, Agent.class);
                    if (agent instanceof Item) {
                        Item item = (Item) agent;
                        allItems.put(item.getName(), item);
                    }
                    // Se vuoi usare anche Character, aggiungi qui
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Thread(this::printgameFiles).start();
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
            System.out.println("L'inventario è vuoto.");
        } else {
            System.out.println("Numero oggetti nell'inventario: " + inventory.size());
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

                // Stampa i personaggi nella stanza
                List<org.it.uniba.fox.Entity.Character> charactersInRoom = room.getAgents();
                if (charactersInRoom == null || charactersInRoom.isEmpty()) {
                    System.out.println("    Personaggi: Nessuno");
                } else {
                    System.out.println("    Personaggi (" + charactersInRoom.size() + "):");
                    for (org.it.uniba.fox.Entity.Character character : charactersInRoom) {
                        System.out.println("      - Nome: " + character.getName());
                    }
                }

                // Stampa gli oggetti nella stanza
                List<Item> roomItems = room.getItems();
                if (roomItems == null || roomItems.isEmpty()) {
                    System.out.println("    Oggetti: Nessuno");
                } else {
                    System.out.println("    Oggetti (" + roomItems.size() + "):");
                    for (Item item : roomItems) {
                        System.out.println("      - Nome: " + item.getName());
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

        System.out.println("\n--- TUTTI GLI OGGETTI  ---");
        Set<Item> allItems = new GameManager().getAllItems();
        if (allItems == null || allItems.isEmpty()) {
            System.out.println("Non ci sono oggetti nel gioco.");
        } else {
            System.out.println("Numero totale oggetti: " + allItems.size());
            for (Item it : allItems) {
                System.out.println("  - Nome: " + it.getName());
            }

            System.out.println("\n--- PERSONAGGI ---");
            // Raccogliamo i personaggi da tutte le stanze
            List<Character> characters = new ArrayList<>();
            if (corridors != null && !corridors.isEmpty()) {
                Set<Room> allRooms = corridors.stream()
                        .flatMap(c -> Stream.of(c.getStartingRoom(), c.getArrivingRoom()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());

                for (Room room : allRooms) {
                    List<Character> roomCharacters = room.getAgents();
                    if (roomCharacters != null) {
                        characters.addAll(roomCharacters);
                    }
                }
            }

            if (characters.isEmpty()) {
                System.out.println("Non ci sono personaggi nel gioco.");
            } else {
                System.out.println("Numero personaggi: " + characters.size());
                for (Character ch : characters) {
                    System.out.println("  - Nome: " + ch.getName());
                }
            }
        }

        System.out.println("=======================================================");


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
