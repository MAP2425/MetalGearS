package org.it.uniba.fox.InteractionManager;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.InteractionManager.OutputDisplayManager;
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
     * The triviaGame object that manages the trivia game in case 2.
     */
    private static TriviaGame triviaGame;
    /**
     * The wordleGame object that manages the wordle game in case 1.
     */
    private static WordleGame wordleGame;
    /**
     * The parser object that parses the user input in case 0.
     */
    private static Parser parser;



    public static void gameFlow(final String text) {
        OutputDisplayManager.displayText(text);

        switch (Event) {
            case 0: // Command parsing and execution
                parserFlow(text);
                break;
            case 1: // Trivia game
                triviaFlow(text);
                // triviaGame.handleInput(text);
                break;
            case 2: // Hangman game
                wordleFlow(text);
                // hangmanGame.handleInput(text);
                break;
            default:
                OutputDisplayManager.displayText("Comando non riconosciuto.");
        }
    }

    /**
     * The method to manage command parsing and execution.
     *
     * @param text the user input
     */
    private static void parserFlow(final String text) {

    }

    public static void setUpGameFlow(final Game game) {
        OutputDisplayManager.displayText("Benvenuto nel gioco! Digita un comando per iniziare.");
        Event = 0;
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
            if (Event == 1) triviaGame.getQAndA();
        } catch (Exception e) {
            e.printStackTrace();
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

}
