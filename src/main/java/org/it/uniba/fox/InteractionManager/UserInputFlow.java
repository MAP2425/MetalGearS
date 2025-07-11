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
     * The parser object that parses the user input in case 0.
     */
    private static Parser parser;



    public static void gameFlow(final String text) {
        OutputDisplayManager.displayText(text);
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
    }


}
