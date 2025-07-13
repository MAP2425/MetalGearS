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
        if (c.getPosition().toString().equals("Stanza5")) {
            UserInputFlow.Event = 1;
            return;
        }
        if (c.hasName("Dottoressa") && game.getCurrentRoom().getName().equals("Stanza10")) {
            GameGUI.displayTextPaneSetText("> Senza distogliere lo sguardo dal terminale, la dottoressa nota l’uomo che appena entrato nella stanza. La sua voce risuona, carica di un misto di frustrazione e determinazione: \"Snake, giusto? Non sapevo se qualcuno sarebbe mai arrivato… Ma ora che sei qui, abbiamo una possibilità. Non posso uscire finché non riesco a bypassare il sistema di sicurezza del progetto BLACK HAND. Serve una chiave speciale... un dispositivo capace di accedere direttamente al cuore del sistema.'");
            return;
        }
        if (c.hasName("IA") && game.getCurrentRoom().getName().equals("Stanza12")) {
            UserInputFlow.Event = 1;
            return;
        }
        GameGUI.displayTextPaneSetText("> " + c.getName() + " non è qui!");

    }

        /**
         * The actions to perform when the player gives an item to a personage.
         *
         * @param i the item to give
         * @param c the personage to give the item to
         * @return true if the action is performed, false otherwise
         */
        public boolean executeGiveCombination(Item i, Character c) {
            if (i.hasName("Chiavetta") && c.hasName("Dottoressa") && game.getCurrentRoom().getName().equals("Stanza10")) {
                game.removeInventory(i);
                game.getCurrentRoom().addItems(i);
                game.unlockCorridor("Stanza10", "Stanza11");
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
            if (i.hasName("Passkey") && game.getCurrentRoom().getName().equals("Stanza1")){

                game.unlockCorridor("Stanza1", "Stanza2");
                return true;
            }
            if ((i.hasName("Pistola") || i.hasName("Uniforme")) && game.getCurrentRoom().getName().equals("Stanza2"))
            {
                game.unlockCorridor("Stanza2", "Stanza3");
                return true;
            }
            if( i.hasName("Pistola") && game.getCurrentRoom().getName().equals("Stanza6"))
            {
                game.unlockCorridor("Stanza6", "Stanza7");
                game.unlockCorridor("Stanza6", "Stanza8");
                return true;
            }
            if (i.hasName("Radio") && game.getCurrentRoom().getName().equals("Stanza8"))
            {
                game.unlockCorridor("Stanza8", "Stanza9");
                return true;
            }
            if (i.hasName("Foglietto") && game.getCurrentRoom().getName().equals("Stanza9"))
            {
                game.unlockCorridor("Stanza9", "Stanza10");
                return true;
            }
            if (i.hasName("Sigarette") && game.getCurrentRoom().getName().equals("Stanza11"))
            {
                game.unlockCorridor("Stanza11", "Stanza12");
                return true;
            }

            return false;

        }
}
