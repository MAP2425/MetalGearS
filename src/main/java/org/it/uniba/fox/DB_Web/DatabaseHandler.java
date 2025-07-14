package org.it.uniba.fox.DB_Web;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        }
    }

    /**
     * Handles the GET request.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException the io exception
     */
    private void handleGet(final Request request, final Response response) throws IOException, SQLException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = new PrintWriter(response.getWriter());
        try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/main/resources/database", "sa", "")) {
            String htmlContent = """
                    <!DOCTYPE html>
                    <html lang="it">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Metal Gear – La Missione MAP</title>
                        <style>
                            body {
                                margin: 0;
                                padding: 0;
                                font-family: Arial, sans-serif;
                                background-color: #f5f5dc;
                                display: flex; /* Rende il body un flex container */
                            }
                         
                            .container {
                                display: flex;
                                flex-grow: 1; /* Il container prende tutto lo spazio rimanente */
                                justify-content: center; /* Centra orizzontalmente i figli (il div content) */
                                align-items: flex-start;
                            }
                            .content {
                                width: 100%; /* Occupa il 100% dello spazio disponibile nel container (che è flessibile) */
                                max-width: 900px; /* Limita la larghezza massima del contenuto per leggibilità */
                                text-align: center;
                                display: flex;
                                flex-direction: column;
                                align-items: center; /* Centra gli elementi figli lungo l'asse orizzontale */
                                padding: 20px; /* Aggiungi un po' di spazio interno */
                                box-sizing: border-box; /* Include il padding nella larghezza */
                            }
                            h1, h2, h3 { color: brown; font-family: 'Papyrus', Times, serif; }
                            h1 { font-weight: bold; font-size: 46px; }
                            h2 { font-weight: normal; font-size: 40px; margin-top: 80px; margin-bottom: 15px; }
                            h3 { font-weight: normal; font-size: 30px; margin-top: 5px; margin-bottom: 5px; }
                            a { text-decoration: none; color: purple; }
                            p { text-align: center; line-height: 2; font-family: 'Georgia'; font-size: 20px; }
                            p.link { line-height: 0; font-size: 24px; }
                        </style>
                    </head>
                    <body>
                    <div class="side-image"></div>
                    <div class="container">
                        <div class="content">
                            <h1>METAL GEAR: LA MISSIONE MAP</h1>
                            <p class="link"><a href="#cap1">Capitolo 1 – Livello Esterno</a></p>
                            <p class="link"><a href="#cap2">Capitolo 2 – Livello Intermedio</a></p>
                            <p class="link"><a href="#cap3">Capitolo 3 – Livello Sotterraneo</a></p>

                            <h2 id="cap1">CAPITOLO 1 – IL LIVELLO ESTERNO: OLTRE IL CANCELLO</h2>
                            <p>Il cielo era plumbeo. Il vento soffiava gelido tra le antenne e le pareti di cemento armato.<br>
                                Snake ascoltava in silenzio la voce di Campbell nel codec:<br>
                                <em>«Snake… il mondo è in pericolo. La dottoressa Irene Weissmann è stata rapita…»</em></p>
                            <p>Travestito con un’uniforme da guardia e armato di passkey, attraversò il cortile sorvegliato.<br>
                                Nel magazzino trovò la celebre <strong>scatola di cartone</strong>, poi disattivò le telecamere risolvendo l’enigma del codice <strong>453238</strong>.</p>

                            <h2 id="cap2">CAPITOLO 2 – IL LIVELLO INTERMEDIO: ENIGMI E INGANNO</h2>
                            <p>Una IA lo sfidò con indovinelli nel corridoio rosso d’allarme. Snake rispose correttamente e proseguì.</p>
                            <p>Nell’atrio ottenne un <strong>badge</strong>, poi scoprì in officina una <strong>radio retrò</strong> e, nella sala server,<br>
                                la chiavetta USB <em>Eclipse Omega</em>. Sintonizzando la radio trovò il codice nascosto che apriva la sala comunicazioni.</p>

                            <h2 id="cap3">CAPITOLO 3 – IL LIVELLO SOTTERRANEO: VERITÀ E SALVEZZA</h2>
                            <p>Nel laboratorio sotterraneo, Snake liberò la dottoressa. Con la chiavetta, lei avviò il sabotaggio di<br>
                                Black Hand: dieci minuti per cancellare tutti i dati.</p>
                            <p>L’ultima stanza era una griglia di <strong>raggi infrarossi</strong>. Snake accese le sigarette; il fumo rivelò i laser.<br>
                                Guidò Weissmann al sicuro. Nel corridoio finale, l’IA propose l’ultimo enigma – un’ultima vittoria.</p>
                            <p><em>«Campbell, qui è Snake. La dottoressa è con me. Missione compiuta.»</em></p>
                            <p>Un elicottero li sollevò nella notte. Il progetto BLACK HAND era distrutto. Il mondo, per ora, era salvo.</p>
                        </div>
                    </div>
                    </body>
                    </html>
                    """;
            out.println(htmlContent);
        } catch (SQLException e) {
            out.println("SQL Error: " + e.getMessage() + "\n");
            e.printStackTrace(out);
        } finally {
            out.flush();
        }
    }
}