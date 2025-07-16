
package org.it.uniba.fox.Logic;
import org.it.uniba.fox.Entity.*;
import org.it.uniba.fox.Entity.Character;
import org.it.uniba.fox.Type.CommandType;
import org.it.uniba.fox.Util.Converter;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The class that manages the game.
 */
public class GameManager {

    /**
     * A map containing all the agents mapped to their names.
     */
    private static Map<String, Item> allAgents = new HashMap<>();
    /**
     * The converter.
     */
    private final Converter converter = new Converter();

    private static Set<Character> allCharacters = new HashSet<>();

    /**
     * Instantiates a new game and creates all the agents.
     */
    public void createGame() {
        allAgents = converter.convertJsonToJavaClass();
    }

    /**
     * The method to save the game and the agents in a json file.
     *
     */
    public void saveGame() {
        converter.ConvertGameToJson();
        converter.ConvertItemsToJson();
    }

    /**
     * Load game boolean.
     *
     * @return the boolean
     */
    public boolean loadGame() {
        allAgents = converter.loadGame();

        try {
            allAgents.get(1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Returns a set with all the items.
     *
     * @return the set of all items
     */
    public Set<Item> getAllItems() {
        if (allAgents == null) {
            return new HashSet<>();
        }
        return allAgents.values().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a set with all the agents of type Character.
     *
     * @return the set of all Characters
     */
    public Set<Character> getAllAgents() {
        Set<Character> characters = new HashSet<>();
        Game game = Game.getInstance();

        if (game == null || game.getCorridorsMap() == null) {
            return characters;
        }

        // Raccogliamo tutte le stanze dal gioco
        Set<Room> allRooms = game.getCorridorsMap().stream()
                .flatMap(c -> Stream.of(c.getStartingRoom(), c.getArrivingRoom()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Raccogliamo tutti i personaggi dalle stanze
        for (Room room : allRooms) {
            List<Character> roomCharacters = room.getAgents();
            if (roomCharacters != null) {
                characters.addAll(roomCharacters);
            }
        }

        System.out.println("Personaggi trovati nelle stanze: " + characters.size());
        return characters;
    }

    /**
     * Instantiates all the commands and returns them.
     *
     * @return the all commands set
     */
    public Set<Command> getAllCommands() {
        Set<Command> availableCommands = new HashSet<>();

        availableCommands.add(new Command("Codec", List.of("h", "help", "comandi", "comando", "guida"), CommandType.CODEC));
        availableCommands.add(new Command("Nord", List.of("n", "north", "avanti", "vaiAvanti"), CommandType.NORD));
        availableCommands.add(new Command("Sud", List.of("s", "south", "indietro", "vaiIndietro"), CommandType.SUD));
        availableCommands.add(new Command("Est", List.of("east", "destra", "vaiDestra", "vaiADestra"), CommandType.EST));
        availableCommands.add(new Command("Ovest", List.of("west", "sinistra", "vaiSinistra", "vaiASinistra"), CommandType.OVEST));
        availableCommands.add(new Command("Inventario", List.of("i", "inventory", "borsa", "zaino", "valigia", "inv"), CommandType.INVENTARIO));
        availableCommands.add(new Command("Osserva", List.of("g", "l", "look", "vedi", "esamina", "osserva", "ammira", "ispeziona"), CommandType.OSSERVA));
        availableCommands.add(new Command("Prendi", List.of("p", "t", "take", "raccogli", "recupera", "intasca"), CommandType.PRENDI));
        availableCommands.add(new Command("Usa", List.of("u", "use", "utilizza", "poggia", "appoggia", "poni"), CommandType.USA));
        availableCommands.add(new Command("Parla", List.of("talk", "dialoga"), CommandType.PARLA));
        availableCommands.add(new Command("Dai", List.of("give", "d", "passa", "consegna", "regala", "dona", "porgi"), CommandType.DAI));

        return availableCommands;
    }

    /**
     * Reset all agents map.
     */
    public void resetAllAgents() {
        allAgents = new HashMap<>();
    }
}