
package org.it.uniba.fox.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.it.uniba.fox.Entity.Character;
import org.it.uniba.fox.Entity.Corridor;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Agent;
import org.it.uniba.fox.Entity.Room;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.Type.CommandType;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Inizializer {

    public static void main(String[] args) {
        List<Agent> items = new ArrayList<>();
        Game game = Game.getInstance();
// Java
        Item pistola = new Item();
        pistola.setName("Pistola");
        pistola.setDescription("Una pistola semiautomatica in dotazione standard dell'esercito degli Stati Uniti, modello Beretta 92FS. E' dotata di silenziatore e munizioni stordenti.");
        pistola.setReusable(true);
        pistola.setPickable(false);
        pistola.setAliases(Arrays.asList("Beretta", "Arma", "PistolaSilenziata"));
        items.add(pistola);

        Item sigarette = new Item();
        sigarette.setName("Sigarette");
        sigarette.setDescription("Avvertenza del ministero della salute: fumare può essere pericoloso per la salute. Ha un uso limitato nel rivelare la posizione dei sensori a infrarossi.");
        sigarette.setReusable(false);
        sigarette.setPickable(true);
        sigarette.setAliases(Arrays.asList("Pacchetto", "Fumo", "SigaretteAmericane"));
        items.add(sigarette);

        Item tavolo = new Item();
        tavolo.setName("Tavolo");
        tavolo.setDescription("Il tavolo di metallo è vecchio e segnato da graffi profondi. Sopra di esso, tra vecchi documenti e attrezzi abbandonati, un piccolo dispositivo elettronico attira l’attenzione di Snake. La luce tremolante del terminale spento riflette sulla superficie della passkey, facendola brillare leggermente.");
        tavolo.setReusable(false);
        tavolo.setPickable(false);
        tavolo.setAliases(Arrays.asList("Banco", "Tavola", "Scrivania"));
        items.add(tavolo);

        Item armadietto = new Item();
        armadietto.setName("Armadietto");
        armadietto.setDescription("L’armadietto è leggermente socchiuso, come se qualcuno l’avesse lasciato in fretta. La lamiera è graffiata e opaca, ma all’interno si intravede chiaramente una uniforme piegata con cura su un ripiano. Il tessuto scuro porta ancora il distintivo della sicurezza della struttura. Un guanto penzola dal bordo, suggerendo che l’equipaggiamento è stato usato di recente... o abbandonato in fretta.");
        armadietto.setReusable(false);
        armadietto.setPickable(false);
        armadietto.setAliases(Arrays.asList("Armadio", "Comodino", "Cassettone"));
        items.add(armadietto);

        Item passkey = new Item();
        passkey.setName("Passkey");
        passkey.setDescription("Una chiave elettronica indispensabile per disabilitare le serrature elettroniche della base.");
        passkey.setReusable(false);
        passkey.setPickable(true);
        passkey.setAliases(Arrays.asList("Chiave", "Passkey", "ChiaveElettronica"));
        items.add(passkey);

        Item uniforme = new Item();
        uniforme.setName("Uniforme");
        uniforme.setDescription("Un travestimento completo che consente a Snake di muoversi più liberamente senza essere immediatamente riconosciuto.");
        uniforme.setReusable(true);
        uniforme.setPickable(true);
        uniforme.setAliases(Arrays.asList("Divisa", "Uniforme", "Travestimento"));
        items.add(uniforme);

        Item scatola = new Item();
        scatola.setName("Scatola");
        scatola.setDescription("Perfetta per passare inosservato tra le guardie. Un classico che non delude mai.");
        scatola.setReusable(true);
        scatola.setPickable(true);
        scatola.setAliases(Arrays.asList("Scatola", "Cartone", "ScatolaCartone"));
        items.add(scatola);

        Item terminale = new Item();
        terminale.setName("Terminale");
        terminale.setDescription("Il terminale risponde con un leggero ronzio mentre Snake digita rapidamente. Le opzioni sullo schermo includono diverse impostazioni di sicurezza, ma il suo obiettivo è chiaro. Dopo pochi istanti, trova l’opzione -Disattivazione Telecamere-. Con un clic deciso, seleziona il comando.");
        terminale.setReusable(false);
        terminale.setPickable(false);
        terminale.setAliases(Arrays.asList("Console", "Punto di Accesso", "Postazione di Controllo"));
        items.add(terminale);

        Item foglietto = new Item();
        foglietto.setName("Foglietto");
        foglietto.setDescription("Il foglio è leggermente sgualcito, come se fosse stato afferrato in fretta. L’inchiostro nero risalta sulla carta, tracciato con una calligrafia semplice ma decisa. Al centro del foglio è scritto chiaramente: 14112.");
        foglietto.setReusable(false);
        foglietto.setPickable(true);
        foglietto.setAliases(Arrays.asList("Foglio", "Foglietto", "Biglietto"));
        items.add(foglietto);

        Item badge = new Item();
        badge.setName("Badge");
        badge.setDescription("Il badge è di metallo, con un design distintivo e il logo della base inciso sopra. Sotto il logo, il badge riporta un numero di identificazione e un nome, che potrebbe essere utile per le identificazioni future.");
        badge.setReusable(false);
        badge.setPickable(true);
        badge.setAliases(Arrays.asList("Badge", "Tesserino", "Pass"));
        items.add(badge);

        Item scaffale = new Item();
        scaffale.setName("Scaffale");
        scaffale.setDescription("Su uno dei ripiani inferiori, quasi nascosta tra una vecchia lampada a raggi infrarossi e un dispositivo di misurazione rotto, si trova una piccola radio… È di un modello retrò, con una cassa di legno graffiata e pulsanti argentati consumati dal tempo. La radio emette un leggero ronzio, come se fosse ancora collegata a una fonte di alimentazione. È un oggetto insolito in mezzo a tutta quella tecnologia avanzata, come se la sua presenza potesse essere più di quanto sembri a prima vista…");
        scaffale.setReusable(false);
        scaffale.setPickable(false);
        scaffale.setAliases(Arrays.asList("Ripiano", "Mensola", "Palchetto"));
        items.add(scaffale);

        Item radio = new Item();
        radio.setName("Radio");
        radio.setDescription("Un dispositivo retrò in legno scuro con manopole consumate e piccoli led lampeggianti. Collegata al sistema del laboratorio, potrebbe trasmettere segnali segreti o attivare funzioni nascoste.");
        radio.setReusable(false);
        radio.setPickable(true);
        radio.setAliases(Arrays.asList("Radio", "Ricetrasmittente", "DispositivoRadio"));
        items.add(radio);

        Item pannello = new Item();
        pannello.setName("Pannello");
        pannello.setDescription("Snake si avvicina con cautela al pannello. Sul display è presente la scritta:\"Inserire la password numerica per procedere\". I tasti sotto il display brillano leggermente, pronti a ricevere l’input corretto.");
        pannello.setReusable(false);
        pannello.setPickable(false);
        pannello.setAliases(Arrays.asList("Pannello", "Tastiera", "Pannello di Controllo"));
        items.add(pannello);

        Item chiavetta = new Item();
        chiavetta.setName("Chiavetta");
        chiavetta.setDescription("La chiavetta contiene il protocollo \"Eclipse Omega\", un piano segreto e alternativo al progetto principale del laboratorio. Questo misterioso programma è stato ideato per attivarsi solo in situazioni critiche, capace di sovvertire le direttive attuali e prendere il controllo degli sviluppi in corso.");
        chiavetta.setReusable(false);
        chiavetta.setPickable(true);
        chiavetta.setAliases(Arrays.asList("Chiavetta", "USB", "ChiavettaUSB"));
        items.add(chiavetta);

// Personaggi
        Character dottoressa = new Character();
        dottoressa.setName("Dottoressa");
        dottoressa.setDescription("La dottoressa ha un aspetto ordinato e professionale. Indossa un camice bianco sopra una camicia chiara e pantaloni pratici. I capelli biondi sono raccolti in uno chignon stretto, lasciando scoperto un volto dai tratti delicati ma decisi. Gli occhi chiari osservano con attenzione da dietro occhiali sottili, e ogni suo movimento è preciso, riflesso di un controllo totale su sé stessa e sull'ambiente che la circonda.");
        dottoressa.setReusable(false);
        dottoressa.setPickable(false);
        dottoressa.setAliases(Arrays.asList("Weissman", "Ragazza", "Ricercatrice"));
        dottoressa.setTalkable(true);
        items.add(dottoressa);


        Character ia1 = new Character();
        ia1.setName("Bot");
        ia1.setDescription("Nel cuore del corridoio immerso nella penombra, l'intelligenza artificiale si manifesta su uno schermo incastonato nella parete. Una sequenza di volti sfocati e in continuo mutamento scorre rapidamente, mescolando tratti umani e digitali in un flusso inquietante. Ogni volto si dissolve nell'altro in una danza ipnotica, come se l'IA stesse cercando una forma stabile che non riesce mai a raggiungere.Una sottile scarica elettrica attraversa l'aria, suggerendo che l'entità sta osservando... e aspettando una risposta.");
        ia1.setReusable(false);
        ia1.setPickable(false);
        ia1.setAliases(Arrays.asList("AI", "Schermo", "Intelligenza Artificiale"));
        ia1.setTalkable(true);
        items.add(ia1);

        Character ia2 = new Character();
        ia2.setName("IA");
        ia2.setDescription("L'IA torna a manifestarsi su un nuovo schermo, più grande, incassato in una struttura metallica verticale. I volti sfocati scorrono ancora, ma ora sono più definiti, quasi familiari, come se l'entità stesse imparando. Attorno al monitor, luci gialle lampeggiano lentamente, segnalando uno stato di allerta moderato. Una serie di simboli criptici compare e scompare ai margini dello schermo, mentre il silenzio carico di attesa suggerisce che un nuovo enigma sta per essere proposto.");
        ia2.setReusable(false);
        ia2.setPickable(false);
        ia2.setAliases(Arrays.asList("AI", "Schermo", "Intelligenza Artificiale"));
        ia2.setTalkable(true);
        items.add(ia2);


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

        List<Agent> temp = items.stream()
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
                .filter(i -> i.getName().equals("Chiavetta"))
                .toList()
                .toArray(new Item[0]));

        stanza9.addItems(items.stream()
                .filter(i -> i.getName().equals("Pannello"))
                .toList()
                .toArray(new Item[0]));

        stanza5.addAgents(new Character[]{ia1});
        stanza10.addAgents(new Character[]{dottoressa});
        stanza12.addAgents(new Character[]{ia2});


        // Popolamento Agents
// Configurazione dei corridoi con setter
        Corridor c1 = new Corridor();
        c1.setStartingRoom(stanza1);
        c1.setDirection(CommandType.NORD);
        c1.setLocked(true); // Andata bloccata
        c1.setArrivingRoom(stanza2);

        Corridor c1b = new Corridor();
        c1b.setStartingRoom(stanza2);
        c1b.setDirection(CommandType.SUD);
        c1b.setLocked(false); // Ritorno sempre sbloccato
        c1b.setArrivingRoom(stanza1);

        Corridor c2 = new Corridor();
        c2.setStartingRoom(stanza2);
        c2.setDirection(CommandType.OVEST);
        c2.setLocked(true);
        c2.setArrivingRoom(stanza3);

        Corridor c2b = new Corridor();
        c2b.setStartingRoom(stanza3);
        c2b.setDirection(CommandType.EST);
        c2b.setLocked(false);
        c2b.setArrivingRoom(stanza2);

        Corridor c3 = new Corridor();
        c3.setStartingRoom(stanza3);
        c3.setDirection(CommandType.NORD);
        c3.setLocked(false);
        c3.setArrivingRoom(stanza4);

        Corridor c3b = new Corridor();
        c3b.setStartingRoom(stanza4);
        c3b.setDirection(CommandType.SUD);
        c3b.setLocked(false);
        c3b.setArrivingRoom(stanza3);

        Corridor c4 = new Corridor();
        c4.setStartingRoom(stanza4);
        c4.setDirection(CommandType.NORD);
        c4.setLocked(false);
        c4.setArrivingRoom(stanza5);

        Corridor c4b = new Corridor();
        c4b.setStartingRoom(stanza5);
        c4b.setDirection(CommandType.SUD);
        c4b.setLocked(false);
        c4b.setArrivingRoom(stanza4);

        Corridor c5 = new Corridor();
        c5.setStartingRoom(stanza5);
        c5.setDirection(CommandType.NORD);
        c5.setLocked(true);
        c5.setArrivingRoom(stanza6);

        Corridor c5b = new Corridor();
        c5b.setStartingRoom(stanza6);
        c5b.setDirection(CommandType.SUD);
        c5b.setLocked(false);
        c5b.setArrivingRoom(stanza5);

        Corridor c6 = new Corridor();
        c6.setStartingRoom(stanza6);
        c6.setDirection(CommandType.EST);
        c6.setLocked(true);
        c6.setArrivingRoom(stanza7);

        Corridor c6b = new Corridor();
        c6b.setStartingRoom(stanza7);
        c6b.setDirection(CommandType.OVEST);
        c6b.setLocked(false);
        c6b.setArrivingRoom(stanza6);

        Corridor c7 = new Corridor();
        c7.setStartingRoom(stanza6);
        c7.setDirection(CommandType.OVEST);
        c7.setLocked(true);
        c7.setArrivingRoom(stanza8);

        Corridor c7b = new Corridor();
        c7b.setStartingRoom(stanza8);
        c7b.setDirection(CommandType.EST);
        c7b.setLocked(false);
        c7b.setArrivingRoom(stanza6);

        Corridor c8 = new Corridor();
        c8.setStartingRoom(stanza8);
        c8.setDirection(CommandType.NORD);
        c8.setLocked(true);
        c8.setArrivingRoom(stanza9);

        Corridor c8b = new Corridor();
        c8b.setStartingRoom(stanza9);
        c8b.setDirection(CommandType.SUD);
        c8b.setLocked(false);
        c8b.setArrivingRoom(stanza8);

        Corridor c9 = new Corridor();
        c9.setStartingRoom(stanza9);
        c9.setDirection(CommandType.EST);
        c9.setLocked(true);
        c9.setArrivingRoom(stanza10);

        Corridor c9b = new Corridor();
        c9b.setStartingRoom(stanza10);
        c9b.setDirection(CommandType.OVEST);
        c9b.setLocked(false);
        c9b.setArrivingRoom(stanza9);

        Corridor c10 = new Corridor();
        c10.setStartingRoom(stanza10);
        c10.setDirection(CommandType.EST);
        c10.setLocked(true);
        c10.setArrivingRoom(stanza11);

        Corridor c10b = new Corridor();
        c10b.setStartingRoom(stanza11);
        c10b.setDirection(CommandType.OVEST);
        c10b.setLocked(false);
        c10b.setArrivingRoom(stanza10);

        Corridor c11 = new Corridor();
        c11.setStartingRoom(stanza11);
        c11.setDirection(CommandType.EST);
        c11.setLocked(true);
        c11.setArrivingRoom(stanza12);

        Corridor c11b = new Corridor();
        c11b.setStartingRoom(stanza12);
        c11b.setDirection(CommandType.OVEST);
        c11b.setLocked(false);
        c11b.setArrivingRoom(stanza11);


        // Raccolta e assegnazione al gioco
        List<Corridor> corridoi = Arrays.asList(
                c1, c1b, c2, c2b, c3, c3b, c4, c4b, c5, c5b, c6, c6b, c7, c7b, c8, c8b, c9, c9b, c10, c10b, c11, c11b
        );

        // Assegna la lista al gioco
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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Agent.class, new AgentDeserializer ())
                .create();

        String gameJson = gson.toJson(game);
        writeJsonToFile("src/main/resources/static/Game.json", gameJson);

        String agentsJson = gson.toJson(items);
        writeJsonToFile("src/main/resources/static/Agents.json", agentsJson);
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
        } catch (IOException e) {
            System.err.println("Errore nella scrittura del file " + filePath + ": " + e.getMessage());
        }
    }
}
