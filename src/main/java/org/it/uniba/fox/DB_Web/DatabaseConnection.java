package org.it.uniba.fox.DB_Web;
import org.it.uniba.fox.InteractionManager.OutputDisplayManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * The type Database connection.
 */
public class DatabaseConnection {

    /**
     * The Jdbc driver.
     */
    static final String JDBC_DRIVER = "org.h2.Driver";
    /**
     * The Db url.
     */
    static final String DB_URL = "jdbc:h2:./src/main/resources/database";
    /**
     * The User.
     */
    static final String USER = "sa";
    /**
     * The Pass.
     */
    static final String PASS = "";

    /**
     * Connect connection.
     *
     * @return the connection
     */
    public static Connection connect() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String start = "RUNSCRIPT FROM 'src/main/resources/database/db_start.sql'";
        String fill = "RUNSCRIPT FROM 'src/main/resources/database/db_info.sql'";

        boolean emptyDescr = true;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.prepareStatement(start);
            stmt.execute();
            stmt.close();

            String sql2 = "SELECT * FROM DESCRIZIONI";
            stmt = conn.prepareStatement(sql2);
            rs = stmt.executeQuery();
            while (rs.next()) {
                emptyDescr = false;
            }
            rs.close();

            if (emptyDescr) {
                stmt = conn.prepareStatement(fill);
                stmt.execute();
                stmt.close();
            }

            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Close.
     *
     * @param conn the conn
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Print from db
     * @param comando the command to search in the database
     * @param stanza the room where the command is executed
     * @param stato the state of the command (true for "Libero", false for "Sorvegliato")
     * @param personaggio the character related to the command
     * @param oggetto the object related to the command
     */
    public static void printFromDB(String comando, String stanza, String stato, String personaggio, String oggetto) {
        String statoDB = stato.equalsIgnoreCase("true") ? "Libero" : "Sorvegliato";
        OutputDisplayManager.displayText(statoDB);
        OutputDisplayManager.displayText(stanza);
        OutputDisplayManager.displayText(comando);
        OutputDisplayManager.displayText(personaggio);
        OutputDisplayManager.displayText(oggetto);
        System.out.println(statoDB);
        System.out.println(stanza);
        System.out.println(comando);
        System.out.println(personaggio);
        System.out.println(oggetto);
        String query = "SELECT DESCRIZIONE FROM DESCRIZIONI WHERE COMANDO = ? AND STANZA = ? AND STATO = ? AND PERSONAGGIO = ? AND OGGETTO = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, comando.replaceAll("[^a-zA-Z0-9 ]", ""));
            stmt.setString(2, stanza.replaceAll("[^a-zA-Z0-9 ]", ""));
            stmt.setString(3, statoDB);
            stmt.setString(4, personaggio);
            stmt.setString(5, oggetto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                OutputDisplayManager.displayText(rs.getString("DESCRIZIONE"));
            } else {
                OutputDisplayManager.displayText("No String Found");


            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
