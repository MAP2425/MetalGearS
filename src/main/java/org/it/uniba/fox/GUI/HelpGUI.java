package org.it.uniba.fox.GUI;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;

/**
 * The GUI of the help dialog.
 */
public class HelpGUI extends JFrame {
    /**
     * The instance of the class.
     */
    private static HelpGUI instance;

    /**
     * Constructor of the class.
     */
    HelpGUI() {
        initComponents();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static HelpGUI getInstance() {
        if (instance == null) {
            instance = new HelpGUI();
        }
        return instance;
    }

    /**
     * Initializes the components.
     */
    private void initComponents() {
        // Create the label
        JLabel listaComandi = new JLabel();

        // Set the properties of the frame
        setTitle("Codec - Comandi di Gioco");
        setPreferredSize(new Dimension(460, 780));
        setMaximumSize(new Dimension(460, 780));
        setMinimumSize(new Dimension(460, 780));
        setResizable(false);
        getContentPane().setBackground(new Color(181, 8, 8));
        setIconImage(new ImageIcon("src/main/resources/docs/img/solid_snake_icon.jpg").getImage());

        // Set the properties of the label
        Font font = new Font("Georgia", Font.CENTER_BASELINE, 16);
        listaComandi.setOpaque(true);
        listaComandi.setBackground(new Color(0, 0, 0));
        listaComandi.setForeground(Color.WHITE);
        listaComandi.setFont(font);
        listaComandi.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, new Color(181, 8, 8)));
        listaComandi.setText(
                "<html>" +
                        "<center>" +
                        "<p><b>Comandi di movimento:</b></p><br>" +
                        "<p><b>Nord</b> - Permette all'utente di muoversi in avanti</p><br>" +
                        "<p><b>Est</b> - Permette all'utente di muoversi a destra</p><br>" +
                        "<p><b>Sud</b> - Permette all'utente di muoversi indietro</p><br>" +
                        "<p><b>Ovest</b> - Permette all'utente di muoversi a sinistra</p><br>" +
                        "<p><b>Comandi di gioco:</b></p><br>" +
                        "<p><b>Inventario</b> - Mostra l'inventario dell'utente</p><br>" +
                        "<p><b>Codec</b> - Mostra i comandi disponibili</p><br>" +
                        "<p><b>Osserva</b> - Mostra la descrizione della stanza</p><br>" +
                        "<p><b>Osserva</b> [<i>oggetto</i>|<i>personaggio</i>] - Mostra la descrizione dell'oggetto o del personaggio, se presente nella stanza<br><br>" +
                        "<p><b>Usa</b> [<i>oggetto</i>] - Utilizza l'oggetto specificato</p><br>" +
                        "<p><b>Prendi</b> [<i>oggetto</i>] - Prendi l'oggetto specificato</p><br>" +
                        "<p><b>Parla</b> [<i>personaggio</i>] - Parla con il personaggio specificato</p><br>" +
                        "<p><b>Dai</b> [<i>oggetto</i>] [<i>personaggio</i>] - Dai l'oggetto specificato al personaggio specificato</p>" +
                        "</center>" +
                        "</html>"
        );


        // Set the layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(listaComandi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(listaComandi, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }
}
