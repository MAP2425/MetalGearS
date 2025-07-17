package org.it.uniba.fox.Util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.it.uniba.fox.Entity.Character;
import org.it.uniba.fox.Entity.Corridor;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Room;
import org.it.uniba.fox.Entity.Agent;
import org.it.uniba.fox.Logic.GameManager;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
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
    public Map<String, Agent> convertJsonToJavaClass() {
        ensureResourceDirectoriesExist();
        return processJsonFiles("src/main/resources/static/Game.json", "src/main/resources/static/Agents.json");

    }

    /**
     * Method that manages the conversion of json files to java classes in the case of a loaded game.
     *
     * @return the map of all the agents
     */
    public Map<String, Agent> loadGame() {
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
    private Map<String, Agent> processJsonFiles(String gameFilePath, String itemsFilePath) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Agent.class, new AgentDeserializer())
                .create();

        Map<String, Agent> allItems = new HashMap<>();
        Map<String, Room> allRooms = new HashMap<>();

        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(gameFilePath));
            if (fileBytes.length == 0) return null;

            JsonReader reader = new JsonReader(new FileReader(gameFilePath));
            Game game = gson.fromJson(reader, Game.class);
            if (game == null) return null;

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

        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(itemsFilePath));
            if (fileBytes.length == 0) {
                return null;
            }
            try (JsonReader reader = new JsonReader(new FileReader(itemsFilePath))) {
                Type agentListType = new TypeToken<ArrayList<Agent>>() {}.getType();
                List<Agent> agentList = gson.fromJson(reader, agentListType);
                agentList.forEach(agent -> allItems.put(agent.getName(), agent));
            }
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
        Set<Agent> allItems = gameManager.getAllItems();

        Set<Room> rooms = game.getCorridorsMap().stream()
                .map(Corridor::getStartingRoom)
                .collect(Collectors.toSet());

        Set<Agent> itemsToSave = allItems.stream()
                .filter(item -> !game.getInventory().contains(item))
                .filter(item -> rooms.stream()
                        .noneMatch(room -> room.getItems().contains(item)))
                .collect(Collectors.toSet());

        String json = gson.toJson(itemsToSave);
        try {
            Files.write(Paths.get("src", "main", "resources", "LoadedAgents.json"), json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
