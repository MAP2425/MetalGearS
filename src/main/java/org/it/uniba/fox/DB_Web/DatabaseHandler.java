package org.it.uniba.fox.DB_Web;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The class that handles the database requests.
 */
public class DatabaseHandler extends HttpHandler {

    /**
     * Handles the service request.
     *
     * @param request  the request
     * @param response the response
     * @throws Exception the exception
     */
    @Override
    public void service(final Request request, final Response response) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod().toString())) {
            handleGet(request, response);
        } else if ("POST".equalsIgnoreCase(request.getMethod().toString())) {
            handlePost(request, response);
        }
    }

    /**
     * Handles the GET request.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException the io exception
     */
    private void handleGet(final Request
                                   request, final  Response response) throws IOException, SQLException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = new PrintWriter(response.getWriter());
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/database", "sa", "");
             out.println("<!DOCTYPE html>\n" +
                     "<!-- Pagina Metal Gear: La Missione MAP -->\n" +
                     "<html lang=\"it\">\n" +
                     "<head>\n" +
                     "    <meta charset=\"UTF-8\">\n" +
                     "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                     "    <title>Metal Gear – La Missione MAP</title>\n" +
                     "    <style>\n" +
                     "        body {\n" +
                     "            margin: 0;\n" +
                     "            padding: 0;\n" +
                     "            font-family: Arial, sans-serif;\n" +
                     "            background-color: #f5f5dc;\n" +
                     "        }\n" +
                     "        .container {\n" +
                     "            display: flex;\n" +
                     "            justify-content: center;\n" +
                     "            align-items: flex-start;\n" +
                     "        }\n" +
                     "        .side-image {\n" +
                     "            width: 25%;\n" +
                     "            height: 100vh;\n" +
                     "            background-image: url('../../../../docs/img/immagine\\ per\\ sito.jpeg');\n" +
                     "            background-size: cover;\n" +
                     "            background-position: center;\n" +
                     "        }\n" +
                     "        .content {\n" +
                     "            width: 80%;\n" +
                     "            text-align: center;\n" +
                     "            display: flex;\n" +
                     "            flex-direction: column;\n" +
                     "            align-items: center;\n" +
                     "        }\n" +
                     "        h1, h2, h3 { color: brown; font-family: 'Papyrus', Times, serif; }\n" +
                     "        h1 { font-weight: bold; font-size: 46px; }\n" +
                     "        h2 { font-weight: normal; font-size: 40px; margin-top: 80px; margin-bottom: 15px; }\n" +
                     "        h3 { font-weight: normal; font-size: 30px; margin-top: 5px; margin-bottom: 5px; }\n" +
                     "        a { text-decoration: none; color: purple; }\n" +
                     "        p { text-align: center; line-height: 2; font-family: 'Georgia'; font-size: 20px; }\n" +
                     "        p.link { line-height: 0; font-size: 24px; }\n" +
                     "    </style>\n" +
                     "</head>\n" +
                     "<body>\n" +
                     "<div class=\"container\">\n" +
                     "    <div class=\"side-image\"></div>\n" +
                     "    <div class=\"content\">\n" +
                     "\n" +
                     "        <h1>METAL GEAR: LA MISSIONE MAP</h1>\n" +
                     "        <p class=\"link\"><a href=\"#cap1\">Capitolo 1 – Livello Esterno</a></p>\n" +
                     "        <p class=\"link\"><a href=\"#cap2\">Capitolo 2 – Livello Intermedio</a></p>\n" +
                     "        <p class=\"link\"><a href=\"#cap3\">Capitolo 3 – Livello Sotterraneo</a></p>\n" +
                     "\n" +
                     "        <!-- CAPITOLO 1 -->\n" +
                     "        <h2 id=\"cap1\">CAPITOLO 1 – IL LIVELLO ESTERNO: OLTRE IL CANCELLO</h2>\n" +
                     "        <p>Il cielo era plumbeo. Il vento soffiava gelido tra le antenne e le pareti di cemento armato.<br>\n" +
                     "           Snake ascoltava in silenzio la voce di Campbell nel codec:<br>\n" +
                     "           <em>«Snake… il mondo è in pericolo. La dottoressa Irene Weissmann è stata rapita…»</em></p>\n" +
                     "        <p>Travestito con un’uniforme da guardia e armato di passkey, attraversò il cortile sorvegliato.<br>\n" +
                     "           Nel magazzino trovò la celebre <strong>scatola di cartone</strong>, poi disattivò le telecamere risolvendo l’enigma del codice <strong>453238</strong>.</p>\n" +
                     "\n" +
                     "        <!-- CAPITOLO 2 -->\n" +
                     "        <h2 id=\"cap2\">CAPITOLO 2 – IL LIVELLO INTERMEDIO: ENIGMI E INGANNO</h2>\n" +
                     "        <p>Una IA lo sfidò con indovinelli nel corridoio rosso d’allarme. Snake rispose correttamente e proseguì.</p>\n" +
                     "        <p>Nell’atrio ottenne un <strong>badge</strong>, poi scoprì in officina una <strong>radio retrò</strong> e, nella sala server,<br>\n" +
                     "           la chiavetta USB <em>Eclipse Omega</em>. Sintonizzando la radio trovò il codice nascosto che apriva la sala comunicazioni.</p>\n" +
                     "\n" +
                     "        <!-- CAPITOLO 3 -->\n" +
                     "        <h2 id=\"cap3\">CAPITOLO 3 – IL LIVELLO SOTTERRANEO: VERITÀ E SALVEZZA</h2>\n" +
                     "        <p>Nel laboratorio sotterraneo, Snake liberò la dottoressa. Con la chiavetta, lei avviò il sabotaggio di<br>\n" +
                     "           Black Hand: dieci minuti per cancellare tutti i dati.</p>\n" +
                     "        <p>L’ultima stanza era una griglia di <strong>raggi infrarossi</strong>. Snake accese le sigarette; il fumo rivelò i laser.<br>\n" +
                     "           Guidò Weissmann al sicuro. Nel corridoio finale, l’IA propose l’ultimo enigma – un’ultima vittoria.</p>\n" +
                     "        <p><em>«Campbell, qui è Snake. La dottoressa è con me. Missione compiuta.»</em></p>\n" +
                     "        <p>Un elicottero li sollevò nella notte. Il progetto BLACK HAND era distrutto. Il mondo, per ora, era salvo.</p>\n" +
                     "\n" +
                     "    </div>\n" +
                     "</div>\n" +
                     "</body>\n" +
                     "</html>");


        } catch (SQLException e) {
            out.println("SQL Error: " + e.getMessage() + "\n");
            e.printStackTrace(out);
        } finally {
            out.flush();
        }

    /**
     * Handles the POST request.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException the io exception
     */
    private void handlePost(final Request request, final Response response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = new PrintWriter(response.getWriter());
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/database/db_map", "sa", "");
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO CLASSIFICA (USERNAME, TEMPO, FINALE) VALUES ('" + request.getParameter("username") + "', '" + request.getParameter("tempo") + "', '" + request.getParameter("finale") + "')");
        } catch (SQLException e) {
            out.println("SQL Error: " + e.getMessage() + "\n");
            e.printStackTrace(out);
        }
    }
}

