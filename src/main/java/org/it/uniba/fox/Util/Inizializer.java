package org.it.uniba.fox.Util;

import com.google.gson.Gson;
import org.it.uniba.fox.Entity.Item;
import org.it.uniba.fox.Entity.Character;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inizializer {

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();

        items.add(new Item(
                "Pistola",
                "Una pistola semiautomatica in dotazione standard dell’esercito degli Stati Uniti, modello Beretta 92FS. E’ dotata di silenziatore e munizioni stordenti.",
                true,
                true,
                Arrays.asList("Beretta", "Arma", "PistolaSilenziata")
        ));

        items.add(new Item(
                "Sigarette",
                "Avvertenza del ministero della salute: fumare può essere pericoloso per la salute. Ha un uso limitato nel rivelare la posizione dei sensori a infrarossi.",
                false,
                true,
                Arrays.asList("Pacchetto", "Fumo", "SigaretteAmericane")
        ));

        items.add(new Item(
                "Passkey",
                "Una chiave elettronica indispensabile per disabilitare le serrature elettroniche della base.",
                true,
                true,
                Arrays.asList("Chiave", "Passkey", "ChiaveElettronica")
        ));

        items.add(new Item(
                "Uniforme",
                "Un travestimento completo che consente a Snake di muoversi più liberamente senza essere immediatamente riconosciuto.",
                true,
                true,
                Arrays.asList("Divisa", "Uniforme", "Travestimento")
        ));

        items.add(new Item(
                "Scatola",
                "Perfetta per passare inosservato tra le guardie. Un classico che non delude mai.",
                true,
                true,
                Arrays.asList("Scatola", "Cartone", "ScatolaCartone")
        ));

        items.add(new Item(
                "Foglietto",
                "Sul foglio è scritto \"45_2_8: Un byte si divide in due. Somma il primo numero dispari al secondo numero pari. Il totale rivelerà le cifre finali.\"",
                true,
                false,
                Arrays.asList("Foglio", "Foglietto", "Biglietto")
        ));

        items.add(new Item(
                "Badge",
                "Il badge è di metallo, con un design distintivo e il logo della base inciso sopra. Sotto il logo, il badge riporta un numero di identificazione e un nome, che potrebbe essere utile per le identificazioni future.",
                true,
                true,
                Arrays.asList("Badge", "Tesserino", "Pass")
        ));

        items.add(new Item(
                "Radio",
                "Un dispositivo retrò in legno scuro con manopole consumate e piccoli led lampeggianti. Collegata al sistema del laboratorio, potrebbe trasmettere segnali segreti o attivare funzioni nascoste.",
                true,
                true,
                Arrays.asList("Radio", "Ricetrasmittente", "DispositivoRadio")
        ));

        items.add(new Item(
                "Chiavetta",
                "La chiavetta contiene il protocollo \"Eclipse Omega\", un piano segreto e alternativo al progetto principale del laboratorio. Questo misterioso programma è stato ideato per attivarsi solo in situazioni critiche, capace di sovvertire le direttive attuali e prendere il controllo degli sviluppi in corso.",
                true,
                true,
                Arrays.asList("Chiavetta", "USB", "ChiavettaUSB")
        ));

        items.add(new Character(
                "Dottoressa",
                "La dottoressa ha un aspetto ordinato e professionale. Indossa un camice bianco sopra una camicia chiara e pantaloni pratici. I capelli biondi sono raccolti in uno chignon stretto, lasciando scoperto un volto dai tratti delicati ma decisi. Gli occhi chiari osservano con attenzione da dietro occhiali sottili, e ogni suo movimento è preciso, riflesso di un controllo totale su sé stessa e sull’ambiente che la circonda.",
                false,
                false,
                Arrays.asList("Weissman", "Ragazza", "Ricercatrice"),
                "Stanza10"
        ));


        items.add(new Character(
                "IA",
                "Nel cuore del corridoio immerso nella penombra, l’intelligenza artificiale si manifesta su uno schermo incastonato nella parete. Una sequenza di volti sfocati e in continuo mutamento scorre rapidamente, mescolando tratti umani e digitali in un flusso inquietante. Ogni volto si dissolve nell’altro in una danza ipnotica, come se l’IA stesse cercando una forma stabile che non riesce mai a raggiungere.Una sottile scarica elettrica attraversa l’aria, suggerendo che l'entità sta osservando... e aspettando una risposta.",
                false,
                false,
                Arrays.asList("AI", "Schermo", "Intelligenza Artificiale"),
                "Stanza5"
        ));


        items.add(new Character(
                "IA",
                "L’IA torna a manifestarsi su un nuovo schermo, più grande, incassato in una struttura metallica verticale. I volti sfocati scorrono ancora, ma ora sono più definiti, quasi familiari, come se l'entità stesse imparando. Attorno al monitor, luci gialle lampeggiano lentamente, segnalando uno stato di allerta moderato. Una serie di simboli criptici compare e scompare ai margini dello schermo, mentre il silenzio carico di attesa suggerisce che un nuovo enigma sta per essere proposto.",
                false,
                false,
                Arrays.asList("AI", "Schermo", "Intelligenza Artificiale"),
                "Stanza12"
        ));





        Gson gson = new Gson();
        String json = gson.toJson(items);

        System.out.println(json);
    }
}
