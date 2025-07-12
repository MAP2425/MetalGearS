package org.it.uniba.fox.GUI;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Logic.GameManager;
import org.it.uniba.fox.InteractionManager.UserInputFlow;
import org.it.uniba.fox.Util.Mixer;
import org.it.uniba.fox.Util.TimerManager;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;


/**
 * The GUI of the menu.
 */
public class MenuGUI extends JPanel {
    /**
     * The background panel.
     */
    private JPanel backgroundPanel;
    /**
     * The new game button.
     */
    private JButton newGame;
    /**
     * The sound button.
     */
    private static JButton sound;
    /**
     * The help button.
     */
    private JButton help;
    /**
     * The load game button.
     */
    private JButton loadGame;
    /**
     * The credits button.
     */
    private JButton credits;
    /**
     * The site button.
     */
    private JButton site;
    /**
     * The instance of game manager.
     */
    GameManager gameManager = new GameManager();
    /**
     * Constructor of the class.
     */
    public MenuGUI() {
        initComponents();
    }

    /**
     * Initializes the components.
     */
    private void initComponents() {
        // Create the components
        backgroundPanel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("src/main/resources/img/titleScreen.jpg");
                Image image = img.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        newGame = new JButton();
        newGame.setAlignmentX(Component.LEFT_ALIGNMENT);
        sound = new JButton( );
        help = new JButton();
        help.setText("?");
        loadGame = new JButton();
        credits = new JButton();
        site = new JButton();

        // Set the properties of the panel
        setPreferredSize(new Dimension(800, 600));
        setSize(new Dimension(800, 600));

        // Set the properties of the background panel
        backgroundPanel.setMinimumSize(new Dimension(800, 600));
        backgroundPanel.setPreferredSize(new Dimension(800, 600));
        backgroundPanel.setRequestFocusEnabled(false);

        // Set the properties of the site button
        site.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return new Color(89, 89, 86, 128);
            }
        });

        site.setFocusPainted(false);
        site.setBackground(new Color(204, 173, 27));
        site.setForeground(new Color(255, 255, 255));
        site.setBorderPainted(true);
        site.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
        site.setFont(site.getFont().deriveFont(40f));
        site.setText("\uD83C\uDF10");
        site.setHorizontalTextPosition(SwingConstants.CENTER);
        site.setMaximumSize(new Dimension(60, 60));
        site.setMinimumSize(new Dimension(60, 60));
        site.setPreferredSize(new Dimension(60, 60));
        site.setOpaque(false);
        site.setContentAreaFilled(false);
        site.addActionListener(evt -> {
            try {
                siteActionPerformed(evt);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Set the properties of the new game button
        newGame.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return new Color(89, 89, 86, 128);

            }
        });
        newGame.setFocusPainted(false);
        newGame.setBackground(new Color(204, 173, 27, 0));
        newGame.setForeground(new Color(255, 255, 255));
        newGame.setFont(new Font("Otacon", 1, 24));
        newGame.setBorderPainted(true);
        newGame.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 3));
        newGame.setText("Nuova Partita");
        newGame.setOpaque(false);
        newGame.setContentAreaFilled(false);


        newGame.setMaximumSize(new Dimension(240, 60));
        newGame.setMinimumSize(new Dimension(240, 60));
        newGame.setPreferredSize(new Dimension(240, 60));
        newGame.addActionListener(this::newGameActionPerformed);

        // Set the properties of the sound button
        sound.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return new Color(89, 89, 86, 128);

            }
        });
        sound.setFocusPainted(false);
        sound.setBackground(new Color(204, 173, 27, 0));
        sound.setForeground(new Color(255, 255, 255));
        sound.setFont(sound.getFont().deriveFont(24f));
        sound.setBorderPainted(true);
        sound.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
        sound.setText("🔊");
        sound.setMargin(new Insets(0, 0, 0, 0));
        sound.setMaximumSize(new Dimension(40, 40));
        sound.setMinimumSize(new Dimension(40, 40));
        sound.setPreferredSize(new Dimension(40, 40));
        sound.setOpaque(false);
        sound.setContentAreaFilled(false);
        sound.addActionListener(this::soundActionPerformed);

        // Set the properties of the help button
        help.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return new Color(89, 89, 86, 128);

            }
        });
        help.setFocusPainted(false);
        help.setBackground(new Color(204, 173, 27));
        help.setForeground(new Color(255, 255, 255));
        help.setFont(new Font("Otacon", 1, 24));
        help.setBorderPainted(true);
        help.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
        help.setMargin(new Insets(0, 0, 0, 0));
        help.setMaximumSize(new Dimension(40, 40));
        help.setMinimumSize(new Dimension(40, 40));
        help.setPreferredSize(new Dimension(40, 40));
        help.setOpaque(false);
        help.setContentAreaFilled(false);
        help.addActionListener(this::helpActionPerformed);

        // Set the properties of the load game button
        loadGame.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return new Color(89, 89, 86, 128);

            }
        });
        loadGame.setFocusPainted(false);
        loadGame.setBackground(new Color(204, 173, 27));
        loadGame.setForeground(new Color(255, 255, 255));
        loadGame.setFont(new Font("Otacon", 1, 24));
        loadGame.setBorderPainted(true);
        loadGame.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 3));
        loadGame.setText("Carica Partita");
        loadGame.setMaximumSize(new Dimension(240, 60));
        loadGame.setMinimumSize(new Dimension(240, 60));
        loadGame.setPreferredSize(new Dimension(240, 60));
        loadGame.setOpaque(false);
        loadGame.setContentAreaFilled(false);
        loadGame.addActionListener(evt -> {
            try {
                loadGameActionPerformed(evt);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        // Set the properties of the credits button
        credits.setUI(new MetalButtonUI() {
            protected Color getSelectColor () {
                return new Color(89, 89, 86, 128);

            }
        });
        credits.setFocusPainted(false);
        credits.setBackground(new Color(204, 173, 27));
        credits.setForeground(new Color(255, 255, 255));
        credits.setFont(new Font("Otacon", 1, 24));
        credits.setBorderPainted(true);
        credits.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 3));
        credits.setText("Riconoscimenti");
        credits.setMaximumSize(new Dimension(240, 60));
        credits.setMinimumSize(new Dimension(240, 60));
        credits.setPreferredSize(new Dimension(240, 60));
        credits.setOpaque(false);
        credits.setContentAreaFilled(false);
        credits.addActionListener(this::creditsActionPerformed);

        GroupLayout backgroundPanelLayout = new GroupLayout(backgroundPanel);
        backgroundPanelLayout.setHorizontalGroup(
                backgroundPanelLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGap(25)
                                .addGroup(backgroundPanelLayout.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(sound, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(help, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 420, Short.MAX_VALUE)
                                .addComponent(site, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(25))
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGap(25)
                                .addGroup(backgroundPanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(newGame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(loadGame, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(credits, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(515, Short.MAX_VALUE))
        );
        backgroundPanelLayout.setVerticalGroup(
                backgroundPanelLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGap(25)
                                .addGroup(backgroundPanelLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(site, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                .addComponent(sound, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                                .addGap(12)
                                                .addComponent(help, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                .addComponent(newGame, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                .addGap(32)
                                .addComponent(loadGame, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                .addGap(32)
                                .addComponent(credits, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                .addGap(90))
        );
        backgroundPanel.setLayout(backgroundPanelLayout);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(backgroundPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    /**
     * Start a new game.
     *
     * @param evt the event
     */
    private void newGameActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout) getParent().getLayout();
        cl.show(getParent(), "GameGUI");
        //gameManager.createGame();
        Game game = Game.getInstance();
        new Thread(() -> UserInputFlow.setUpGameFlow(game)).start();
        TimerManager.getInstance().startTimer("00:00:00");
         //Reset the game state
         Mixer.startClip(); // Uncomment if you want to start the music when a new game starts
    }

    /**
     * Start or stop the music.
     */
    private void soundActionPerformed(ActionEvent evt) {
        if (Mixer.isRunning()) {
            Mixer.stopClip();
        } else {
            Mixer.startClip();
        }
    }
    /**
     * Open the help dialog.
     *
     * @param evt the event
     */
    private void helpActionPerformed(ActionEvent evt) {
        try {
            HelpGUI helpGUI = new HelpGUI();
            helpGUI.setVisible(true);
        } catch (Exception e) {
            showMessageDialog(this, "Errore durante l'apertura della guida.", "Errore", ERROR_MESSAGE);
        }
    }

    /**
     * Load a saved game.
     *
     * @param evt the event
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    private void loadGameActionPerformed(ActionEvent evt) throws IOException, ClassNotFoundException {
    }

    /**
     * Switch to the credits panel.
     *
     * @param evt the event
     */
    private void creditsActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout) getParent().getLayout();
        cl.show(getParent(), "CreditsGUI");
    }

    /**
     * Open the site.
     *
     * @param evt the event
     */
    private void siteActionPerformed(final ActionEvent evt) throws URISyntaxException, IOException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI("http://localhost:8080/api/data"));
        }
    }

    /**
     * Method to set the text of the sound button.
     *
     * @param text the text
     */
    public static void musicButtonSetTextMenu(String text) {
        sound.setText(text);
    }
}

