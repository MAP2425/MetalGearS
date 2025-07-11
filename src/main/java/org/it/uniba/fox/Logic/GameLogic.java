package org.it.uniba.fox.Logic;

import org.it.uniba.fox.Entity.Character;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Entity.Item;
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
     */
    public void talkToPersonage(Character c) {
        if(c.getPosition().toString().equals("Stanza5")) {
            UserInputFlow.Event = 1;
        } else {
            GameGUI.displayTextPaneSetText("> " + c.getName() + " non è qui!");
        }
    }

        /**
         * The actions to perform when the player gives an item to a personage.
         *
         * @param i the item to give
         * @param c the personage to give the item to
         * @return true if the action is performed, false otherwise
         */
        public boolean executeGiveCombination(Item i, Character c) {
            if (i.hasName("Hekat") || i.hasName("Nekhekh") || i.hasName("Ankh") && c.hasName("Osiride")) {
                game.removeInventory(i);
                game.getCurrentRoom().addItems(i);
                i.setPicked(false);
                return true;
            }
            return false;
        }

        /**
         * The actions to perform when the player uses a single item.
         *
         * @param i the item used
         * @return true if the action is performed, false otherwise
         */
        public boolean executeUseSingleItem(Item i) {
            if (i.hasName("Pala") && game.getCurrentRoom().getName().equals("Desert")
                    && !game.getCurrentRoom().getItems().contains(gameManager.getAgentFromName("Chiave"))
                    && !game.getInventory().contains(gameManager.getAgentFromName("Chiave"))) {

                game.addInventory((Item) gameManager.getAgentFromName("Chiave"));
                return true;
            }
            if (i.hasName("Leva") && game.getCurrentRoom().getName().equals("Stanza3")) {
                return true;
            }
            return false;
        }
}
