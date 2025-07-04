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

    }




    private void fillStopWords() throws Exception {
        Files.readAllBytes(Paths.get("src/main/resources/Utilities/stopWords.txt"));
        File file = new File("src/main/resources/Utilities/stopWords.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while (reader.ready()) {
            stopWords.add(reader.readLine().trim().toLowerCase());
        }
        reader.close();
    }


}
