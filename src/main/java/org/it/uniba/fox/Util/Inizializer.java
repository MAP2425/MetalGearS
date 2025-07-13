
package org.it.uniba.fox.Util;

import com.google.gson.Gson;
import org.it.uniba.fox.Entity.Corridor;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Character;
import org.it.uniba.fox.Entity.Room;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Type.CommandType;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Inizializer {

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();
        Game game = Game.getInstance();
        items.add(new Item("Pistola", "Una pistola semiautomatica in dotazione standard dell'esercito degli Stati Uniti, modello Beretta 92FS. E' dotata di silenziatore e munizioni stordenti.", true, true, true, Arrays.asList("Beretta", "Arma", "PistolaSilenziata")));
        items.add(new Item("Sigarette", "Avvertenza del ministero della salute: fumare può essere pericoloso per la salute. Ha un uso limitato nel rivelare la posizione dei sensori a infrarossi.", false, true, true, Arrays.asList("Pacchetto", "Fumo", "SigaretteAmericane")));
        items.add(new Item("Tavolo", "Il tavolo di metallo è vecchio e segnato da graffi profondi. Sopra di esso, tra vecchi documenti e attrezzi abbandonati, un piccolo dispositivo elettronico attira l’attenzione di Snake. La luce tremolante del terminale spento riflette sulla superficie della passkey, facendola brillare leggermente.", false, false, false, Arrays.asList("Banco", "Tavola", "Scrivania")));
        items.add(new Item("Armadietto", "L’armadietto è leggermente socchiuso, come se qualcuno l’avesse lasciato in fretta. La lamiera è graffiata e opaca, ma all’interno si intravede chiaramente una uniforme piegata con cura su un ripiano. Il tessuto scuro porta ancora il distintivo della sicurezza della struttura. Un guanto penzola dal bordo, suggerendo che l’equipaggiamento è stato usato di recente... o abbandonato in fretta.", false, false, false, Arrays.asList("Armadio", "Comodino", "Cassettone") ));
        items.add(new Item("Passkey", "Una chiave elettronica indispensabile per disabilitare le serrature elettroniche della base.", true, true, true, Arrays.asList("Chiave", "Passkey", "ChiaveElettronica")));
        items.add(new Item("Uniforme", "Un travestimento completo che consente a Snake di muoversi più liberamente senza essere immediatamente riconosciuto.", true, true, true, Arrays.asList("Divisa", "Uniforme", "Travestimento")));
        items.add(new Item("Scatola", "Perfetta per passare inosservato tra le guardie. Un classico che non delude mai.", true, true, true, Arrays.asList("Scatola", "Cartone", "ScatolaCartone")));
        items.add(new Item("Terminale", "Il terminale risponde con un leggero ronzio mentre Snake digita rapidamente. Le opzioni sullo schermo includono diverse impostazioni di sicurezza, ma il suo obiettivo è chiaro. Dopo pochi istanti, trova l’opzione -Disattivazione Telecamere-. Con un clic deciso, seleziona il comando.", false, false, false, Arrays.asList("Console", "Punto di Accesso", "Postazione di Controllo")));
        items.add(new Item("Foglietto", "Il foglio è leggermente sgualcito, come se fosse stato afferrato in fretta. L’inchiostro nero risalta sulla carta, tracciato con una calligrafia semplice ma decisa. Al centro del foglio è scritto chiaramente: 14112.", true, false, true, Arrays.asList("Foglio", "Foglietto", "Biglietto")));
        items.add(new Item("Badge", "Il badge è di metallo, con un design distintivo e il logo della base inciso sopra. Sotto il logo, il badge riporta un numero di identificazione e un nome, che potrebbe essere utile per le identificazioni future.", true, true, true, Arrays.asList("Badge", "Tesserino", "Pass")));
        items.add(new Item("Scaffale", "Su uno dei ripiani inferiori, quasi nascosta tra una vecchia lampada a raggi infrarossi e un dispositivo di misurazione rotto, si trova una piccola radio… È di un modello retrò, con una cassa di legno graffiata e pulsanti argentati consumati dal tempo. La radio emette un leggero ronzio, come se fosse ancora collegata a una fonte di alimentazione. È un oggetto insolito in mezzo a tutta quella tecnologia avanzata, come se la sua presenza potesse essere più di quanto sembri a prima vista…", false, true, false, Arrays.asList("Ripiano", "Mensola", "Palchetto")));
        items.add(new Item("Radio", "Un dispositivo retrò in legno scuro con manopole consumate e piccoli led lampeggianti. Collegata al sistema del laboratorio, potrebbe trasmettere segnali segreti o attivare funzioni nascoste.", true, true, true, Arrays.asList("Radio", "Ricetrasmittente", "DispositivoRadio")));
        items.add(new Item("Pannello", "Snake si avvicina con cautela al pannello. Sul display è presente la scritta:\"Inserire la password numerica per procedere\". I tasti sotto il display brillano leggermente, pronti a ricevere l’input corretto.", false, false, false, Arrays.asList("Pannello", "Tastiera", "Pannello di Controllo")));
        items.add(new Item("Chiavetta", "La chiavetta contiene il protocollo \"Eclipse Omega\", un piano segreto e alternativo al progetto principale del laboratorio. Questo misterioso programma è stato ideato per attivarsi solo in situazioni critiche, capace di sovvertire le direttive attuali e prendere il controllo degli sviluppi in corso.", true, true, true, Arrays.asList("Chiavetta", "USB", "ChiavettaUSB")));

        items.add(new Character("Dottoressa", "La dottoressa ha un aspetto ordinato e professionale. Indossa un camice bianco sopra una camicia chiara e pantaloni pratici. I capelli biondi sono raccolti in uno chignon stretto, lasciando scoperto un volto dai tratti delicati ma decisi. Gli occhi chiari osservano con attenzione da dietro occhiali sottili, e ogni suo movimento è preciso, riflesso di un controllo totale su sé stessa e sull'ambiente che la circonda.", false, false, Arrays.asList("Weissman", "Ragazza", "Ricercatrice"), "Stanza10"));
        items.add(new Character("IA", "Nel cuore del corridoio immerso nella penombra, l'intelligenza artificiale si manifesta su uno schermo incastonato nella parete. Una sequenza di volti sfocati e in continuo mutamento scorre rapidamente, mescolando tratti umani e digitali in un flusso inquietante. Ogni volto si dissolve nell'altro in una danza ipnotica, come se l'IA stesse cercando una forma stabile che non riesce mai a raggiungere.Una sottile scarica elettrica attraversa l'aria, suggerendo che l'entità sta osservando... e aspettando una risposta.", false, false, Arrays.asList("AI", "Schermo", "Intelligenza Artificiale"), "Stanza5"));
        items.add(new Character("IA", "L'IA torna a manifestarsi su un nuovo schermo, più grande, incassato in una struttura metallica verticale. I volti sfocati scorrono ancora, ma ora sono più definiti, quasi familiari, come se l'entità stesse imparando. Attorno al monitor, luci gialle lampeggiano lentamente, segnalando uno stato di allerta moderato. Una serie di simboli criptici compare e scompare ai margini dello schermo, mentre il silenzio carico di attesa suggerisce che un nuovo enigma sta per essere proposto.", false, false, Arrays.asList("AI", "Schermo", "Intelligenza Artificiale"), "Stanza12"));

        Room stanza1 = new Room("Stanza1", "Ti trovi nell'ingresso principale della base, una stanza imponente e austera. Le pareti di cemento grigio sono scarsamente illuminate da luci fluorescenti che tremolano leggermente, proiettando ombre inquietanti. L'aria è densa e carica di tensione, interrotta solo dal monotono ronzio delle telecamere di sicurezza montate negli angoli del soffitto.\n" +
                "Di fronte a te, c'è un tavolo di metallo con un terminale di sicurezza spento. Accanto al terminale, si nota un piccolo dispositivo che brilla debolmente sotto la luce tremolante: sembra essere una passkey.\n" +
                "Sul lato destro della stanza, un piccolo armadietto di metallo è appoggiato contro la parete, con un gancio che sporge dalla porta leggermente aperta, rivelando un lembo di stoffa che sembra essere parte di un'uniforme.", true, null, null);
        Room stanza2 = new Room("Stanza2", "Ora ti trovi nel cortile, un'ampia area all'aperto circondata da alte mura di cemento. I fari montati lungo le pareti illuminano a intermittenza il terreno irregolare, creando un gioco di luci e ombre che offre sia copertura che pericolo.\n" +
                "Al centro del cortile c'è una torre di guardia con due guardie armate che osservano attentamente l'area sottostante.\n" +
                "Lungo il perimetro, altre guardie pattugliano in coppia, spostandosi con ritmo regolare.\n" +
                "L'unica porta si trova sul lato ovest: è la porta del magazzino, leggermente aperta. Essa rivela un bagliore interno che suggerisce la presenza di equipaggiamenti utili.\n" +
                "Muoviti con cautela e sfrutta l'oscurità a tuo vantaggio. Non devi essere individuato dal nemico...", false, null, null);
        Room stanza3 = new Room("Stanza3", "Il magazzino è uno spazio ampio e disordinato, con scaffali di metallo stipati di casse di legno e barili di metallo.\n" +
                "L'odore di olio e metallo riempie l'aria, mentre la luce fioca crea ombre spettrali sulle pareti.\n" +
                "Tra le varie casse e attrezzi, spicca una scatola di cartone, ben conservata e apparentemente ordinaria, ma con un potenziale inaspettato.", true, null, null);
        Room stanza4 = new Room("Stanza4", "La sala di controllo è il cuore pulsante del sistema di sicurezza della base; grandi schermi a parete mostrano immagini in tempo reale delle telecamere di sorveglianza, con feed che si aggiornano costantemente.\n" +
                "I monitor principali sono collegati direttamente alle IA che gestiscono la sicurezza, e mostrano dati criptati e flussi di codice che scorrono rapidamente.\n" +
                "Al centro della sala, una postazione di comando particolarmente elaborata attira l'attenzione: un terminale principale brilla con una luce fredda.\n" +
                "Sopra la tastiera, c'è un piccolo foglietto di carta, sgualcito e parzialmente nascosto, che sembra contenere un indizio importante.", true, null, null);
        Room stanza5 = new Room("Stanza5", "Questo è il corridoio d'accesso alla sezione intermedia della base: un lungo tunnel metallico, con pareti lisce e grigie che riflettono debolmente la luce del soffitto.\n" +
                "Un ronzio elettronico crescente riempie l'aria.\n" +
                "Il rumore degli ingranaggi che si avviano segnala l'attivazione di un blocco di sicurezza.\n" +
                "Su uno schermo centrale appaiono volti distorti e pixelati, vagamente umani, che scorrono rapidamente in sequenza.", true, null, null);
        Room stanza6 = new Room("Stanza6", "L'atrio di accesso è un'area vasta che collega il corridoio principale alle sezioni protette della base.\n" +
                "Al centro, Snake nota un gruppo di guardie disposte strategicamente: alcune pattugliano, altre sorvegliano accessi laterali.\n" +
                "I loro movimenti sincronizzati indicano un alto livello di addestramento.\n" +
                "Un badge di accesso potrebbe rivelarsi utile.", false, null, null);
        Room stanza7 = new Room("Stanza7", "L'officina è un ambiente angusto e poco illuminato, con un soffitto basso attraversato da tubi e cavi che si intrecciano in ogni direzione, creando un'atmosfera disordinata e quasi claustrofobica. Sul pavimento, sparsi in ogni angolo, giacciono circuiti spezzati, viti arrugginite e strumenti di precisione dimenticati. Qua e là emergono resti di vecchi progetti, tra cui parti di robot ormai smontati, monitor rotti e componenti dall'uso non immediatamente riconoscibile. In un angolo, appena visibile nella penombra, si trova un vecchio scaffale metallico, pieno di apparecchi inutilizzati: oggetti abbandonati e superati, ma che potrebbero ancora celare qualcosa di valore.", true, null, null);
        Room stanza8 = new Room("Stanza8", "La sala server si presenta come un luogo freddo, silenzioso e sterile, con una luce bluastra che emana dai grandi server allineati lungo le pareti. Al centro della stanza fluttua uno schermo olografico che proietta in tempo reale una complessa rete di dati, mentre eleganti tastiere di vetro sembrano attendere istruzioni. Tra i cavi intrecciati e le ventole in funzione, nascosta con discrezione, si nota una piccola chiavetta USB, diversa da tutto ciò che la circonda. È lucida, di colore nero opaco, e reca inciso su un lato un simbolo criptico che ne suggerisce un'origine e una funzione fuori dall'ordinario.", true, null, null);
        Room stanza9 = new Room("Stanza9", "La Sala Comunicazioni è un ambiente ampio e altamente tecnologico. Le pareti sono ricoperte da scaffalature metalliche disposte con ordine, ognuna carica di radio perfettamente sincronizzate sulla medesima frequenza. Un senso di ordine rigido e costante permea l'aria, amplificato dal ronzio regolare dei dispositivi. Al centro della sala, incastonato nella parete di fondo, si erge un ascensore di vetro e metallo dall'aspetto sofisticato. Tuttavia, l'accesso è bloccato da un pannello elettronico situato al suo fianco. Sul display compaiono simboli familiari mescolati a sequenze numeriche, ma il meccanismo resta impenetrabile in assenza di una chiave di decodifica, rendendo impossibile qualunque tentativo di attivazione immediata.", true, null, null);
        Room stanza10 = new Room("Stanza10", "Il laboratorio di ricerca è un luogo in apparenza sterile, ma profondamente caotico. Le luci fredde illuminano superfici piene di strumentazioni complesse, monitor accesi che scorrono ininterrottamente dati criptici e schemi tecnici di difficile interpretazione. In fondo alla sala, oltre un banco traboccante di apparecchiature, si scorge la figura di una donna, la dottoressa, che si muove con decisione e assoluta concentrazione. Il suo sguardo è fisso su un terminale collegato a un sistema centrale, da cui sembra monitorare ogni variabile dell'intero laboratorio. Il suo comportamento lascia intuire che ogni sua azione sia parte di un protocollo preciso, forse vitale.", true, null, null);
        Room stanza11 = new Room("Stanza11", "Questa stanza rappresenta uno degli ostacoli più pericolosi dell'intera base. È completamente protetta da un sistema di sicurezza composto da raggi infrarossi invisibili all'occhio umano, che si incrociano tra loro formando una griglia intricata e mortale. Sebbene non letali di per sé, questi raggi attivano all'istante un protocollo d'emergenza che sigilla ermeticamente l'ambiente e libera gas tossico nell'aria. Non ci sarebbe alcuna possibilità di fuga. Attraversare questa stanza significa affrontare un equilibrio perfetto tra calma, precisione e sangue freddo. Snake sa che oltre questa soglia si apre il corridoio che porta all'uscita, ma un solo errore decreterebbe la fine.", true, null, null);
        Room stanza12 = new Room("Stanza12", "Nel corridoio finale, l'atmosfera cambia drasticamente. Non si tratta più solo di dispositivi o protocolli: è come se la base stessa si stesse rivolgendo a chi è giunto fino a qui. Una voce si manifesta, solenne e consapevole, affermando di essere l'ultimo guardiano, non un semplice sistema, ma un'entità progettata per testare la volontà e l'intelligenza dell'intruso. Qui non si passa semplicemente superando un ostacolo fisico, ma affrontando una prova mentale e psicologica che mette alla prova il valore e la determinazione. L'uscita è vicina, ma ogni passo verso la libertà dovrà essere guadagnato con astuzia, coraggio e lucidità. Nessuna vittoria sarà concessa facilmente.", true, null, null);

        List<Item> temp = items.stream()
                .filter(i -> i.getName().equals("Uniforme") || i.getName().equals("Passkey") || i.getName().equals("Tavolo") || i.getName().equals("Armadietto"))
                .toList();
        stanza1.addItems(temp.toArray(new Item[0]));

        stanza3.addItems(items.stream()
                .filter(i -> i.getName().equals("Scatola"))
                .toList()
                .toArray(new Item[0]));

        stanza4.addItems(items.stream()
                .filter(i -> i.getName().equals("Foglietto") || i.getName().equals("Terminale"))
                .toList()
                .toArray(new Item[0]));

        stanza6.addItems(items.stream()
                .filter(i -> i.getName().equals("Badge"))
                .toList()
                .toArray(new Item[0]));

        stanza7.addItems(items.stream()
                .filter(i -> i.getName().equals("Radio") || i.getName().equals("Scaffale"))
                .toList()
                .toArray(new Item[0]));

        stanza8.addItems(items.stream()
                .filter(i -> i.getName().equals("Chiavetta") || i.getName().equals("Pannello"))
                .toList()
                .toArray(new Item[0]));

        // popolamento characters
        stanza10.addCharacters(items.stream()
                .filter(i -> i instanceof Character && i.getName().equals("Dottoressa"))
                .map(i -> (Character) i)
                .toList()
                .toArray(new Character[0]));

        stanza5.addCharacters(items.stream()
                .filter(i -> i instanceof Character && i.getName().equals("IA"))
                .map(i -> (Character) i)
                .limit(1)
                .toList()
                .toArray(new Character[0]));

        stanza12.addCharacters(items.stream()
                .filter(i -> i instanceof Character && i.getName().equals("IA"))
                .map(i -> (Character) i)
                .skip(1)
                .toList()
                .toArray(new Character[0]));
// Configurazione dei corridoi con setter
        Corridor c1 = new Corridor();
        c1.setStartingRoom(stanza1);
        c1.setDirection(CommandType.NORD);
        c1.setLocked(true);
        c1.setArrivingRoom(stanza2);

        Corridor c66 = new Corridor();
        c1.setStartingRoom(stanza2);
        c1.setDirection(CommandType.SUD);
        c1.setLocked(true);
        c1.setArrivingRoom(stanza1);



        Corridor c2 = new Corridor();
        c2.setStartingRoom(stanza2);
        c2.setLocked(false);
        c2.setArrivingRoom(stanza3);

        Corridor c3 = new Corridor();
        c3.setStartingRoom(stanza3);
        c3.setLocked(false);
        c3.setArrivingRoom(stanza4);

        Corridor c4 = new Corridor();
        c4.setStartingRoom(stanza4);
        c4.setLocked(false);
        c4.setArrivingRoom(stanza5);

        Corridor c5 = new Corridor();
        c5.setStartingRoom(stanza5);
        c5.setLocked(true);
        c5.setArrivingRoom(stanza6);

        Corridor c6 = new Corridor();
        c6.setStartingRoom(stanza6);
        c6.setLocked(false);
        c6.setArrivingRoom(stanza7);

        Corridor c7 = new Corridor();
        c7.setStartingRoom(stanza6);
        c7.setLocked(false);
        c7.setArrivingRoom(stanza8);

        Corridor c8 = new Corridor();
        c8.setStartingRoom(stanza8);
        c8.setLocked(true);
        c8.setArrivingRoom(stanza9);

        Corridor c9 = new Corridor();
        c9.setStartingRoom(stanza9);
        c9.setLocked(true);
        c9.setArrivingRoom(stanza10);

        Corridor c10 = new Corridor();
        c10.setStartingRoom(stanza10);
        c10.setLocked(true);
        c10.setArrivingRoom(stanza11);

        Corridor c11 = new Corridor();
        c11.setStartingRoom(stanza10);

        c11.setLocked(true);
        c11.setArrivingRoom(stanza12);

        // Raccolta e assegnazione al gioco
        List<Corridor> corridoi = Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11);

        // Assegna la lista dei corridoi al gioco
        game.setCorridorsMap(corridoi);

        game.setCurrentRoom(stanza1);
        game.setCurrentTime("00:00:00");

        List<Item> inventario = new ArrayList<>();
        inventario.add(new Item("Pistola", "Una pistola semiautomatica in dotazione standard dell'esercito degli Stati Uniti, modello Beretta 92FS. E' dotata di silenziatore e munizioni stordenti.", true, true, true, Arrays.asList("Beretta", "Arma", "PistolaSilenziata")));
        inventario.add(new Item("Sigarette", "Avvertenza del ministero della salute: fumare può essere pericoloso per la salute. Ha un uso limitato nel rivelare la posizione dei sensori a infrarossi.", false, true, true, Arrays.asList("Pacchetto", "Fumo", "SigaretteAmericane")));
        game.getInventory().addAll(inventario);

        HashMap<String, Boolean> freeMap = new HashMap<>();
        freeMap.put("Stanza1", true);
        freeMap.put("Stanza2", false);
        freeMap.put("Stanza3", true);
        freeMap.put("Stanza4", true);
        freeMap.put("Stanza5", true);
        freeMap.put("Stanza6", false);
        freeMap.put("Stanza7", true);
        freeMap.put("Stanza8", true);
        freeMap.put("Stanza9", true);
        freeMap.put("Stanza10", true);
        freeMap.put("Stanza11", true);
        freeMap.put("Stanza12", true);

        // Assegna la freeMap all'oggetto Game
        for (Map.Entry<String, Boolean> entry : freeMap.entrySet()) {
            game.setRoomState(entry.getKey(), entry.getValue());
        }
        // Crea il file Game.json
        Gson gson = new Gson();
        String gameJson = gson.toJson(game);
        writeJsonToFile("src/main/resources/static/Game.json", gameJson);

        // Crea il file Agents.json contentente gli oggetti e i personaggi
        String agentsJson = gson.toJson(items);

        writeJsonToFile("src/main/resources/static/Agents.json", agentsJson);

    }

    /**
     * Crea una directory se non esiste
     *
     * @param directoryPath percorso della directory da creare
     */
    private static void createDirectory(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                System.out.println("Directory creata: " + directoryPath);
            } catch (IOException e) {
                System.err.println("Errore nella creazione della directory " + directoryPath + ": " + e.getMessage());
            }
        }
    }

    /**
     * Scrive un contenuto JSON in un file
     *
     * @param filePath percorso del file
     * @param jsonContent contenuto JSON da scrivere
     */
    private static void writeJsonToFile(String filePath, String jsonContent) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(jsonContent);
            System.out.println("File creato: " + filePath);
        } catch (IOException e) {
            System.err.println("Errore nella scrittura del file " + filePath + ": " + e.getMessage());
        }
    }
}
