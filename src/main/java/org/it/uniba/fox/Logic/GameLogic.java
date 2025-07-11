package org.it.uniba.fox.Logic;

import org.it.uniba.fox.Entity.Character;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Entity.Room;
import org.it.uniba.fox.GUI.GameGUI;
import org.it.uniba.fox.InteractionManager.UserInputFlow;

/**
 * The class that manages the game logic.
 */
public class GameLogic {
    /**
     * The game instance.
     */
    private Game game;
    /**
     * The instance of the game manager.
     */
    private final GameManager gameManager = new GameManager();

    /**
     * Constructor of the class.
     *
     * @param game the game instance
     */
    public GameLogic(Game game) {
        this.game = game;
    }


    /**
     * The actions to perform when the player talks to a personage.
     *
     * @param c the personage to talk to
     * @return true if the action is performed, false otherwise
     */
    public boolean talkToPersonage(Character c) {
        if(c.getPosition().toString().equals("Stanza5")) {
            UserInputFlow.Event = 1;
            return true;
        } else {
            GameGUI.displayTextPaneSetText("> " + c.getName() + " non è qui!");
            return false;
        }
}
