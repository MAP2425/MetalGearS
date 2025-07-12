package org.it.uniba.fox.Logic;


import ch.qos.logback.core.pattern.Converter;
import org.it.uniba.fox.Entity.Character;
import org.it.uniba.fox.Entity.Command;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Type.CommandType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The class that manages the game.
 */
public class GameManager {
    /**
     * A map containing all the agents mapped to their names.
     */
    private static Map<String, Character> allAgents;
    /**
     * The converter.
     */
    //private final Converter converter = new Converter();

    /**
     * Instantiates a new game and creates all the agents.
     */
   // public void createGame() {
       // allAgents = converter.convertJsonToJavaClass();
   // }

    /**
     * The method to save the game and the agents in a json file.
     *
     */
    public void saveGame() {
       // converter.ConvertGameToJson();
       // converter.ConvertAgentsToJson();
    }

    /**
     * Load game boolean.
     *
     * @return the boolean
     */
    public boolean loadGame() {
        //allAgents = converter.loadGame();

        try {
            allAgents.get(1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets an agent from its name.
     *
     * @param name the name of the agent
     * @return the agent
     */
    public Character getAgentFromName(String name) {
        return allAgents.get(name);
    }

    /**
     * Returns a set with all the agents.
     *
     * @return the all agents set
     */
    public Set<Character> getAllAgents() {
        return new HashSet<>(allAgents.values());
    }

    /**
     * Returns a set with all the items.
     *
     * @return the all items set
     */
    public Set<Item> getAllItems() {
        return allAgents.values().stream()
                .filter(Objects::nonNull)
                .map(agent -> (Item) agent)
                .collect(Collectors.toSet());
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
        availableCommands.add(new Command("Est", List.of("e", "east", "destra", "vaiDestra", "vaiADestra"), CommandType.EST));
        availableCommands.add(new Command("Ovest", List.of("o", "west", "sinistra", "vaiSinistra", "vaiASinistra"), CommandType.OVEST));
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
        allAgents = null;
    }
}
