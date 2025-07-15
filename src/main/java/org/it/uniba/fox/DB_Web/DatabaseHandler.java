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
                               <link href="https://fonts.googleapis.com/css2?family=Orbitron:wght@400;700&family=Rajdhani:wght@400;600&display=swap" rel="stylesheet">
                               <style>
                                   body {
                                       margin: 0;
                                       padding: 0;
                                       font-family: 'Rajdhani', sans-serif; /* Font futuristico per il corpo */
                                       background-color: #1a1a2e; /* Sfondo più scuro per un tocco "tech" */
                                       color: #e0e0e0; /* Testo chiaro per contrasto */
                                       display: flex;
                                   }
                               \s
                                   .container {
                                       display: flex;
                                       flex-grow: 1;
                                       justify-content: center;
                                       align-items: flex-start;
                                   }
                                   .content {
                                       width: 100%;
                                       max-width: 900px;
                                       text-align: center;
                                       display: flex;
                                       flex-direction: column;
                                       align-items: center;
                                       padding: 20px;
                                       box-sizing: border-box;
                                       background-color: #2a2a4a; /* Un po' più scuro del body per il contenuto */
                                       border-radius: 8px; /* Bordi leggermente arrotondati */
                                       box-shadow: 0 0 15px rgba(0, 255, 255, 0.4); /* Ombra luminosa ciano per effetto tech */
                                   }
                                   h1, h2, h3 {\s
                                       color: #00ffff; /* Ciano brillante per i titoli */
                                       font-family: 'Orbitron', sans-serif; /* Font futuristico per i titoli */
                                       text-shadow: 0 0 10px rgba(0, 255, 255, 0.6); /* Effetto neon */
                                   }
                                   h1 {\s
                                       font-weight: 700; /* Più spesso */
                                       font-size: 48px;\s
                                       letter-spacing: 3px; /* Spaziatura tra le lettere */
                                   }
                                   h2 {\s
                                       font-weight: 400;\s
                                       font-size: 38px;\s
                                       margin-top: 60px;\s
                                       margin-bottom: 10px;\s
                                       letter-spacing: 2px;
                                   }
                                   h3 {\s
                                       font-weight: 400;\s
                                       font-size: 28px;\s
                                       margin-top: 5px;\s
                                       margin-bottom: 5px;\s
                                   }
                                   a {\s
                                       text-decoration: none;\s
                                       color: #00ff00; /* Verde brillante per i link */
                                       transition: color 0.3s ease-in-out; /* Effetto al passaggio del mouse */
                                   }
                                   a:hover {
                                       color: #00ffff; /* Cambia colore al passaggio del mouse */
                                   }
                                   p {\s
                                       text-align: center;\s
                                       line-height: 1.8;\s
                                       font-family: 'Rajdhani', sans-serif;\s
                                       font-size: 20px;\s
                                       color: #c0c0c0; /* Grigio chiaro per il testo */
                                   }
                                   p.link {\s
                                       line-height: 0;\s
                                       font-size: 22px;\s
                                       margin: 10px 0;
                                   }
                                   strong {
                                       color: #ffff00; /* Giallo brillante per il testo in grassetto */
                                   }
                                   em {
                                       color: #aaffaa; /* Verde acqua per il testo in corsivo */
                                   }
                               </style>
                           </head>
                           <body>
                           <div class="side-image"></div>
                           <div class="container">
                               <div class="content">
                                   <h1>METAL GEAR</h1>
                                   <p class="link"><a href="#cap1">Capitolo 1 – Livello Esterno</a></p>
                                   <p class="link"><a href="#cap2">Capitolo 2 – Livello Intermedio</a></p>
                                   <p class="link"><a href="#cap3">Capitolo 3 – Livello Sotterraneo</a></p>
                           
                                   <h2 id="cap1">CAPITOLO 1 – IL LIVELLO ESTERNO: OLTRE IL CANCELLO</h2>
                                   <p>Il cielo era plumbeo. Il vento soffiava gelido tra le antenne e le pareti di cemento armato.<br>
                                       Snake ascoltava in silenzio la voce di Campbell nel codec:<br>
                                       <em>«Snake… il mondo è in pericolo. La dottoressa Irene Weissmann è stata rapita…»</em></p>
                                   <p>Travestito con un’uniforme da guardia e armato di passkey, attraversò il cortile sorvegliato.<br>
                                       Nel magazzino trovò la celebre <strong>scatola di cartone</strong>, poi disattivò le telecamere risolvendo l’enigma del codice <strong>14112</strong>.</p>
                           
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
