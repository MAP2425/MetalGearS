package org.it.uniba.fox.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGUI {

    public static void main(String[] args) {
        // Creazione del frame principale
        JFrame frame = new JFrame("Metal Gear Menu");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false); // Finestra non resizable

        // Imposta l'icona della finestra
        ImageIcon windowIcon = new ImageIcon("src/main/resources/img/solid_snake_icon.jpg");
        frame.setIconImage(windowIcon.getImage());

        // Pannello di sfondo
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("src/main/resources/img/titleScreen.jpg");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setBounds(0, 0, 800, 600);
        backgroundPanel.setLayout(null);

        // Pulsante "Nuova Partita"
        JButton newGameButton = new JButton("Nuova Partita");
        newGameButton.setBounds(50, 200, 200, 50);
        styleTransparentButton(newGameButton);

        // Pulsante "Continua"
        JButton continueButton = new JButton("Continua");
        continueButton.setBounds(50, 280, 200, 50);
        styleTransparentButton(continueButton);

        // Pulsante "Crediti"
        JButton creditsButton = new JButton("Crediti");
        creditsButton.setBounds(50, 360, 200, 50);
        styleTransparentButton(creditsButton);

        // Pulsante "Impostazioni"
        JButton settingsButton = new JButton("⚙");
        settingsButton.setBounds(10, 10, 40, 40); // Ridotto nelle dimensioni
        styleTransparentSmallButton(settingsButton);

        // Pulsante "Musica"
        JButton musicButton = new JButton("♫");
        musicButton.setBounds(60, 10, 40, 40); // Ridotto nelle dimensioni
        styleTransparentSmallButton(musicButton);

        // Aggiunta dei pulsanti al pannello
        backgroundPanel.add(newGameButton);
        backgroundPanel.add(continueButton);
        backgroundPanel.add(creditsButton);
        backgroundPanel.add(settingsButton);
        backgroundPanel.add(musicButton);

        // Azioni dei pulsanti
        newGameButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Nuova partita cominciata!"));
        continueButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Continua la Partita!"));
        creditsButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Crediti: Creato da Fabryk!"));
        settingsButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Impostazioni aperte!"));
        musicButton.addActionListener(new ActionListener() {
            private boolean musicOn = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                musicOn = !musicOn;
                String status = musicOn ? "Musica Attivata!" : "Musica Disattivata!";
                JOptionPane.showMessageDialog(frame, status);
            }
        });

        // Aggiunta del pannello al frame
        frame.add(backgroundPanel);

        // Visualizzazione della finestra
        frame.setVisible(true);
    }

    // Metodo per personalizzare i pulsanti principali con trasparenza
    private static void styleTransparentButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(50, 50, 50, 150)); // Trasparenza aggiunta
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setOpaque(false);
    }

    // Metodo per personalizzare i pulsanti piccoli trasparenti
    private static void styleTransparentSmallButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(50, 50, 50, 150)); // Trasparenza aggiunta
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setOpaque(false);
    }
}
