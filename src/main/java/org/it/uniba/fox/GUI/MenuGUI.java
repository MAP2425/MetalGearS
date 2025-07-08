package org.it.uniba.fox.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class MenuGUI extends JFrame {

    public MenuGUI() {
        setTitle("Metalgear");

        // Imposta l'icona della finestra
        try (InputStream iconStream = getClass().getClassLoader().getResourceAsStream("img/solid_snake_icon.jpg")) {
            if (iconStream != null) {
                Image icon = ImageIO.read(iconStream);
                setIconImage(icon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Disabilita la massimizzazione
        setMaximizedBounds(getBounds());
        setExtendedState(JFrame.NORMAL);
        setUndecorated(false);

        // Pannello con immagine di sfondo
        JPanel backgroundPanel = new JPanel() {
            BufferedImage bgImage;
            {
                try (InputStream is = getClass().getClassLoader().getResourceAsStream("img/titleScreen.jpg")) {
                    if (is != null) {
                        bgImage = ImageIO.read(is);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout());

        // Bottone classifica online in alto a destra
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        JButton onlineRankingButton = new JButton();
        try (InputStream iconStream = getClass().getClassLoader().getResourceAsStream("img/world_icon.png")) {
            if (iconStream != null) {
                ImageIcon worldIcon = new ImageIcon(ImageIO.read(iconStream));
                onlineRankingButton.setIcon(worldIcon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        onlineRankingButton.setContentAreaFilled(false);
        onlineRankingButton.setOpaque(false);
        onlineRankingButton.setBorderPainted(false);
        onlineRankingButton.setFocusPainted(false);
        onlineRankingButton.setToolTipText("Classifica Online");
        topPanel.add(onlineRankingButton, BorderLayout.EAST);
        backgroundPanel.add(topPanel, BorderLayout.NORTH);

        // Pannello per i bottoni in basso, orizzontali e trasparenti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 30, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 180, 40, 180));

        Font boldFont = new Font("Arial", Font.BOLD, 18);

        JButton startButton = new JButton("Inizia Partita");
        startButton.setFont(boldFont);
        startButton.setForeground(Color.WHITE);
        startButton.setContentAreaFilled(false);
        startButton.setOpaque(false);
        startButton.setBorderPainted(false);

        JButton loadButton = new JButton("Carica Partita");
        loadButton.setFont(boldFont);
        loadButton.setForeground(Color.WHITE);
        loadButton.setContentAreaFilled(false);
        loadButton.setOpaque(false);
        loadButton.setBorderPainted(false);

        JButton audioButton = new JButton("Audio On/off");
        audioButton.setFont(boldFont);
        audioButton.setForeground(Color.WHITE);
        audioButton.setContentAreaFilled(false);
        audioButton.setOpaque(false);
        audioButton.setBorderPainted(false);

        buttonPanel.add(startButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(audioButton);

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(backgroundPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuGUI gui = new MenuGUI();
            gui.setVisible(true);
        });
    }
}