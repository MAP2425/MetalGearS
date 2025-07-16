package org.it.uniba.fox.InteractionManager;
import org.it.uniba.fox.DB_Web.DatabaseConnection;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.InteractionManager.OutputDisplayManager;
import org.it.uniba.fox.Logic.CommandExecutor;
import org.it.uniba.fox.Logic.Parser;
import org.it.uniba.fox.Type.ParserOutput;

import java.util.List;

/**
 * The class that redirects the user input to the correct part of the game.
 */
public class UserInputFlow {
    /**
     * The constant Event that represents the current event.
     */
    public static int Event;
    /**
     * The parser object that parses the user input in case 0.
     */
    private static Parser parser;
    /**
     * The commandExecutor object that executes the command in case 0.
     */
    private static CommandExecutor commandExecutor;
    /**
     * The wordleGame object that manages the wordle game in case 1.
     */
    private static WordleGame wordleGame;
    /**
     * The triviaGame object that manages the trivia game in case 2.
     */
    private static TriviaGame triviaGame;

    /**
     * Manages the flow of the game based on the current event.
     *
     * @param text the user input
     */
    public static void gameFlow(final String text) {
        OutputDisplayManager.displayText(text);

        switch (Event) {
            case 0:
                parserFlow(text);
                break;
            case 1:
                wordleFlow(text);
                break;
            case 2:
                triviaFlow(text);
                break;
            default:
                parserFlow(text);
                break;
        }
    }

    /**
     * The method to manage command parsing and execution.
     *
     * @param text the user input
     */
    private static void parserFlow(final String text) {
        if (parser==null){
            OutputDisplayManager.displayText("> Errore interno: parser non inizializzato. Avvia una nuova partita.");
            return;
        }
        ParserOutput output = parser.parse(text);


        // If the output is null, it means that the input was not recognized
        System.out.println("> " + output.getCharacter1() + " " + output.getCharacter2() + " " + output.getCommand() + " " + output.getArgs());
        if (output.getArgs() != 0) {
            commandExecutor.execute(output);
        } else {
            OutputDisplayManager.displayText("> Non ho capito cosa vuoi fare, riprova!");
        }

    }


    /**
     * The method to manage the wordle game event.
     *
     * @param text the user input
     */
    public static void wordleFlow(final String text) {
        wordleGame.manageGuess(text.trim().toUpperCase());
    }

    /**
     * The method to manage the trivia game event.
     *
     * @param text the user input
     */
    public static void triviaFlow(final String text) {
        triviaGame = TriviaGame.getInstance();

        try {
            triviaGame.checkGuess(text);
            if (Event == 2) triviaGame.getQAndA();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Set up a new game
     */
    public static void setUpGameFlow(final Game game) {
        Event = 0;
        if (game.getCurrentRoom() == null) {
            OutputDisplayManager.displayText("> Errore: stanza corrente non inizializzata. Impossibile avviare la partita.");
            return;
        }

         DatabaseConnection.printFromDB("0",game.getCurrentRoom().getName(), "true", "0", "0");
        // poiché la prima API è andata in down, spostiamo il setup del Wordle su un thread separato
        // così da non bloccare il flusso del gioco
        new Thread(() -> wordleGame = new WordleGame()).start();
        triviaGame = TriviaGame.getInstance();
        triviaGame.resetCorrectAnswers();
        parser = new Parser();
        commandExecutor = new CommandExecutor(game);
    }
}
