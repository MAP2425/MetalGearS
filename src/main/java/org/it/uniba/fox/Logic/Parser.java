package org.it.uniba.fox.Logic;

import org.it.uniba.fox.Entity.Command;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Agent;
import org.it.uniba.fox.Entity.Character;
import org.it.uniba.fox.Type.ParserOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Parser {

    private final Set<Command> availableCommands;
    private final Set<Agent> availableItems;
    private final Set<String> stopWords = new HashSet<>();
    private final Set<Character> availableAgents;

    public Parser() {
        GameManager gameManager = new GameManager();
        availableCommands = gameManager.getAllCommands();
        availableItems = gameManager.getAllItems();
        availableAgents = gameManager.getAllAgents();

        try {
            setupUselessWords();
        } catch (IOException e) {
            throw new RuntimeException("Errore durante il caricamento delle stopwords", e);
        }
    }


    /**
     * Parses the input and returns the output of the operation.
     *
     * @param input the input
     * @return the parser output
     */
    public ParserOutput parse(String input) {
        ParserOutput output = new ParserOutput();

        String[] words = Arrays.stream(input.split(" "))
                .map(String::toLowerCase)
                .filter(w -> !stopWords.contains(w))
                .toArray(String[]::new);

        if (words.length == 0) {
            return output;
        }

        // Cerca il comando
        for (Command command : availableCommands) {
            if (command.getName().equalsIgnoreCase(words[0]) ||
                    command.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(words[0]))) {
                output.setCommand(command.getType());
                output.setArgs(1);
                break;
            }
        }

        if (output.getCommand() == null) {
            return output;
        }

// Cerca il primo argomento
        if (words.length > 1) {
            Object found = findObjectByName(words[1]);
            if (found != null) {
                output.setArg1(found);
                output.setArgs(2);
            } else {
                output.setArgs(1);
            }
        }

        // Cerca il secondo argomento
        if (words.length > 2) {
            Object found = findObjectByName(words[2]);
            if (found != null) {
                output.setArg2(found);
                output.setArgs(3);
            }
        }

        return output;
    }

    /**
     * Finds an object by its name or alias in the available items and Agents.
     * @param name the name or alias of the object to find
     * @return the found object, or null if not found
     */

    private Object findObjectByName(String name) {
        for (Agent item : availableItems) {
            if (item.getName().equalsIgnoreCase(name) ||
                    item.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(name))) {
                return item;
            }
        }

        if (availableAgents != null) {
            for (Character character : availableAgents) {
                // Controllo nome e alias del personaggio
                String charName = character.getName();
                boolean nameMatches = charName.equalsIgnoreCase(name);
                boolean aliasMatches = character.getAliases().stream()
                        .anyMatch(alias -> alias.equalsIgnoreCase(name));

                if (nameMatches || aliasMatches) {
                    return character;
                }
            }
        }

        return null;
    }

    /**
     * Sets up the stop words.
     *
     * @throws Exception the exception
     */
    private void setupUselessWords() throws IOException {
        File file = new File("src/main/resources/utilities/stopWords.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim().toLowerCase());
            }
        }
    }
}
