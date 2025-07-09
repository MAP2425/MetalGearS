package org.it.uniba.fox.Logic;
import org.it.uniba.fox.Entity.Command;
import org.it.uniba.fox.Entity.Item;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Parser {


    private final Set<String> stopWords = new HashSet<>();

    private final Set<Command> availableCommands;

    private final Set <Item> availableItems;

    public Parser(Set <Command> availableCommands,Set <Item> availableItems){

        this.availableCommands=availableCommands;
        this.availableItems=availableItems;
        try {
            this.fillStopWords();
        } catch (Exception e) {
            System.err.println("Error loading stop words: " + e.getMessage());
        }

    }


    public String[] parseInput(String input) {
        String cleaned = input.trim().toLowerCase();
        String[] words = cleaned.split("\\s+");
        return java.util.Arrays.stream(words)
                .filter(word -> !stopWords.contains(word))
                .toArray(String[]::new);
    }

    public boolean containsCommand(String[] words) {
        for (String word : words) {
            boolean foundCommand = availableCommands.stream().anyMatch(cmd ->
                    cmd.getName().equalsIgnoreCase(word) ||
                            cmd.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(word))
            );
            if (foundCommand) {
                return true;
            }
        }
        return false;
    }

    public boolean containsItem(String[] words) {
        for (String word : words) {
            boolean foundItem = availableItems.stream().anyMatch(item ->
                    item.getName().equalsIgnoreCase(word)
            );
            if (foundItem) {
                return true;
            }
        }
        return false;
    }


    public void fillStopWords() throws Exception {
        Files.readAllBytes(Paths.get("src/main/resources/Utilities/stopWords.txt"));
        File file = new File("src/main/resources/Utilities/stopWords.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while (reader.ready()) {
            stopWords.add(reader.readLine().trim().toLowerCase());
        }
        reader.close();
    }


    public Command getMatchedCommand(String[] words) {
        for (String word : words) {
            for (Command cmd : availableCommands) {
                if (cmd.getName().equalsIgnoreCase(word) ||
                        cmd.getAliases().stream().anyMatch(alias -> alias.equalsIgnoreCase(word))) {
                    return cmd;
                }
            }
        }
        // Non serve return null, la funzione sarà chiamata solo se c'è match
        throw new IllegalStateException("Nessun comando trovato, ma la funzione non dovrebbe essere chiamata in questo caso.");
    }

    public Item getMatchedItem(String[] words) {
        for (String word : words) {
            for (Item item : availableItems) {
                if (item.getName().equalsIgnoreCase(word)) {
                    return item;
                }
            }
        }
        throw new IllegalStateException("Nessun item trovato, ma la funzione non dovrebbe essere chiamata in questo caso.");
    }

}
