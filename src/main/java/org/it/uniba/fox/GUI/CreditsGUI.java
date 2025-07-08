package org.it.uniba.fox.GUI;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import javax.swing.SwingConstants;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.CardLayout;

/**
 * The GUI of the credits.
 */
public class CreditsGUI extends JPanel {
    /**
     * The button to go back to the main menu.
     */
    private JButton goBack;
    /**
     * The label of the title.
     */
    private JLabel titleLabel;
    /**
     * The panel of the background.
     */
    private JPanel backgroundPanel;
    /**
     * The panel of the icon of Marcu.
     */
    private JPanel marcoIcon;
    /**
     * The panel of the icon of Fabry.
     */
    private JPanel fabryIcon;
    /**
     * The panel of the icon of Ricci.
     */
    private JPanel ricciIcon;
    /**
     * The label of the credits text
     */
    private JLabel contentLabel;

    /**
     * Constructor of the class.
     */
    public CreditsGUI() {
        initComponents();
    }

    /**
     * Initialize the components.
     */
    private void initComponents() {
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("src/main/resources/img/creditsBackground.jpg");
                Image image = img.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        goBack = new JButton();
        marcoIcon = new JPanel();
        fabryIcon = new JPanel();
        ricciIcon = new JPanel();
        titleLabel = new JLabel();
        contentLabel = new JLabel();

        // Set the properties of the main panel
        setPreferredSize(new Dimension(800, 600));

        // Set the properties of the background panel
        backgroundPanel.setMinimumSize(new Dimension(800, 600));
        backgroundPanel.setPreferredSize(new Dimension(800, 600));

        // Set the properties of the go back button
        goBack.setUI(new MetalButtonUI() {
            protected Color getSelectColor() {
                return new Color(133, 106, 5, 50);
            }
        });
        goBack.setFocusPainted(false);
        goBack.setBackground(new Color(204, 173, 27));
        goBack.setForeground(new Color(255, 0, 0, 202));
        goBack.setBorderPainted(true);
        goBack.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0, 128), 2));
        goBack.setFont(goBack.getFont().deriveFont(26f));
        goBack.setText("⮜");
        goBack.setMaximumSize(new Dimension(40, 40));
        goBack.setMinimumSize(new Dimension(40, 40));
        goBack.setPreferredSize(new Dimension(40, 40));
        goBack.setOpaque(false);
        goBack.setContentAreaFilled(false);
        goBack.addActionListener(this::goBackActionPerformed);

        // Set the properties of the icon of Marco
        marcoIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("src/main/resources/docs/img/marcoIcon.png");
                Image image = img.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        marcoIcon.setMinimumSize(new Dimension(150, 150));
        marcoIcon.setPreferredSize(new Dimension(150, 150));

        // Set layout of the icon of Marco
        GroupLayout marcoIconLayout = new GroupLayout(marcoIcon);
        marcoIcon.setLayout(marcoIconLayout);
        marcoIconLayout.setHorizontalGroup(
                marcoIconLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 150, Short.MAX_VALUE)
        );
        marcoIconLayout.setVerticalGroup(
                marcoIconLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 150, Short.MAX_VALUE)
        );

        // Set the properties of the icon of fabry
        fabryIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("src/main/resources/docs/img/fabryIcon.png");
                Image image = img.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        fabryIcon.setMinimumSize(new Dimension(150, 150));
        fabryIcon.setPreferredSize(new Dimension(150, 150));

        // Set layout of the icon of fabry
        GroupLayout fabryIconLayout = new GroupLayout(fabryIcon);
        fabryIcon.setLayout(fabryIconLayout);
        fabryIconLayout.setHorizontalGroup(
                fabryIconLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 150, Short.MAX_VALUE)
        );
        fabryIconLayout.setVerticalGroup(
                fabryIconLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 150, Short.MAX_VALUE)
        );

        // Set the properties of the icon of ricci
        ricciIcon = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon("src/main/resources/docs/img/ricciIcon.png");
                Image image = img.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        ricciIcon.setMinimumSize(new Dimension(150, 150));
        ricciIcon.setPreferredSize(new Dimension(150, 150));

        // Set layout of the icon of ricci
        GroupLayout ricciIconLayout = new GroupLayout(ricciIcon);
        ricciIcon.setLayout(ricciIconLayout);
        ricciIconLayout.setHorizontalGroup(
                ricciIconLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 150, Short.MAX_VALUE)
        );
        ricciIconLayout.setVerticalGroup(
                ricciIconLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 150, Short.MAX_VALUE)
        );

        // Set the properties of the title label
        titleLabel.setFont(new Font("Otacon", 0, 34));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(255, 0, 0, 128));
        titleLabel.setText("Riconoscimenti");
        titleLabel.setPreferredSize(new Dimension(220, 39));

        // Set the properties of the content label
        contentLabel.setBackground(new Color(255, 0, 0, 205));
        contentLabel.setForeground(new Color(255, 255, 255));
        contentLabel.setOpaque(true);
        contentLabel.setVerticalAlignment(SwingConstants.TOP);
        contentLabel.setFont(new Font("Georgia", 0, 14));
        contentLabel.setText(
                "<html><center>"
                        + "<p>\"Metal Gear\" è un'avventura testuale realizzata da Fabrizio Giuseppe Mazzilli, Francesco Ricci e Marco Virardi nell’ambito del corso universitario di \"Metodi Avanzati di Programmazione\". Pur essendo un progetto focalizzato sull'applicazione pratica dei principali concetti affrontati durante le lezioni, gli autori non hanno trascurato la componente ludica, creando qualcosa di ben fatto e divertente, al limite delle loro possibilità.</p>"
                        + "</html><center>"
        );
        contentLabel.setPreferredSize(new Dimension(500, 132));
        contentLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set the layout of the background panel
        GroupLayout backgroundPanelLayout = new GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
                backgroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGroup(backgroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(goBack, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                                .addGap(105, 105, 105)
                                                .addComponent(marcoIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(70, 70, 70)
                                                .addComponent(fabryIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(70, 70, 70)
                                                .addComponent(ricciIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                                .addGap(143, 143, 143)
                                .addGroup(backgroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                                                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
                                                .addGap(300, 300, 300))
                                        .addGroup(GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                                                .addComponent(contentLabel, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
                                                .addGap(160, 160, 160))))
        );
        backgroundPanelLayout.setVerticalGroup(
                backgroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(goBack, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addGroup(backgroundPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(fabryIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(ricciIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(marcoIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50)
                                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(17, 17, 17)
                                .addComponent(contentLabel, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(112, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(backgroundPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(backgroundPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

    }

    /**
     * The button to go back to the main menu.
     *
     * @param evt the evt
     */
    private void goBackActionPerformed(ActionEvent evt) {
        CardLayout cl = (CardLayout) getParent().getLayout();
        cl.show(getParent(), "MenuGUI");
    }
}
