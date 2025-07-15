package org.it.uniba.fox.GUI;
import javax.imageio.ImageIO;
import org.it.uniba.fox.Util.Mixer;
import javax.swing.JFrame;
import java.io.File;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.CardLayout;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * The manager of the GUIs.
 */
public class ManagerGUI extends JFrame {
    static GameGUI game;

    /**
     * Instantiates a new Gui manager.
     */
    public ManagerGUI() {
        // Set the properties of the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Metal Gear");
        setPreferredSize(new Dimension(800, 600));
        setResizable(false);
        try {
            Image icon = ImageIO.read(new File("src/main/resources/img/solid_snake_icon.jpg"));
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create the cards
        JPanel cards = new JPanel(new CardLayout());
        MenuGUI menu = new MenuGUI();
        CreditsGUI credits = new CreditsGUI();
        game = new GameGUI();

        // Add the panels to cards
        cards.add(menu, "MenuGUI");
        cards.add(credits, "CreditsGUI");
        cards.add(game, "GameGUI");

        // Start the frame
        add(cards);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        // Start the music
        Mixer.getInstance().start();
    }

    /**
     * Closes the game GUI.
     */
    public static void closeGame() {
        game.goBack();
    }
}
