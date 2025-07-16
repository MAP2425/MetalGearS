### In questa sezione verrà spiegato come sono stati applicati gli argomenti trattati durante il corso di "Metodi Avanzati di Programmazione" all'interno del progetto.

#### NOTA BENE: Gli snippet di codice presenti in questa sezione sono semplificati e non rappresentano l'intero codice del progetto, ma solo una parte significativa.


<ul>
    <li>
        <h2>1) Utilizzo dei file</h2>
        <details open>
            <summary>Visualizza dettagli</summary>
            <h3><b>Cosa sono i file?</b></h3>
Un file non è altro che un flusso di I/O che può essere utilizzato sia come sorgente che come destinazione di dati.

In Java, i file sono gestiti attraverso la classe File,che fornisce metodi per creare, eliminare, leggere, scrivere e gestire tutti i flussi di I/O nel Sistema Operativo.

- Un flusso può rappresentare molti tipi diversi: file su disco, dispositivi, altri programmi e array in memoria.
- Gli stream supportano molti tipi diversi di dati, inclusi byte semplici, tipi di dati primitivi, caratteri e oggetti.
- Alcuni flussi semplicemente trasmettono dati; altri manipolano e trasformano i dati.
- Indipendentemente dal modo in cui funzionano internamente, tutti i flussi presentano lo stesso modello: una sequenza di dati.

<h3><b>Come abbiamo utilizzato i file nel nostro progetto?</b></h3>
All'interno del nostro progetto i file sono stati utilizzati sia per l'inizializzazione del gioco, delle stanze che per il salvataggio.

La gestione dei file ci ha permesso di memorizzare e recuperare i dati del gioco in modo persistente, garantendo la continuità dell'esperienza di gioco per gli utenti.

Come appreso durante il corso, inoltre, abbiamo utilizzato i file in formato JSON per memorizzare i dati in modo strutturato e leggibile, facilitandone la gestione e la manipolazione.

- **Inizializzazione del gioco e caricamento di quest'ultimo**:
Per l'inizializzazione del gioco o il caricamento di quest'ultimo, abbiamo reso il nostro codice molto modulare, creando un unico metodo all'interno della classe <b>Converter</b> che si occupa di effettuare la conversione dei dati da JSON a Oggetti Java sia se si tratta di una nuova partita che di un caricamento di una partita salvata.


Per far ciò abbiamo creato due metodi ausialiari all'interno della classe <b>Converter</b>, il cui compito è solo quello di passare i path del file JSON da leggere e restituirli al metodo principale <b>convertJsonToJavaClass</b> che si occupa di effettuare la conversione dei dati da JSON a Java, come mostrato di seguito:
```java

  public Map<String, Item> convertJsonToJavaClass() {
        ensureResourceDirectoriesExist();
        return processJsonFiles("src/main/resources/static/Game.json", "src/main/resources/static/Agents.json");
    }


  public Map<String, Item> loadGame() {
        ensureResourceDirectoriesExist();
        return processJsonFiles("src/main/resources/LoadedGame.json", "src/main/resources/LoadedAgents.json");
    }

```

In questo modo abbiamo evitato di creare due metodi distinti per la conversione dei dati da JSON a Java, rendendo il nostro codice più modulare e manutenibile.
Il metodo <b>processJsonFiles</b> ha come compito quello di andare a leggere i file JSON passati come parametri e restituirli in formato Java, andando a creare un oggetto di tipo <b>Map</b> contenente tutti gli agenti presenti nel gioco e inizializzando il gioco.

- **Salvataggio del gioco**:


Tra gli utilizzi dei file, uno dei più importanti è sicuramente quello del salvataggio del gioco, anch'esso effettuato tramite file JSON.

La classe che si occupa di fare ciò è <b>Converter</b>, in particolare il suo metodo <b>ConvertGameToJson</b> e <b>ConvertItemsToJson</b>, come mostrato di seguito:

```java
    public void ConvertGameToJson() {
  Gson gson = new Gson();
  Game game = Game.getInstance();
  String json = gson.toJson(game);

  try {
    Files.write(Paths.get("src", "main", "resources", "LoadedGame.json"), json.getBytes());
  } catch (IOException e) {
    throw new RuntimeException(e);
  }
}

public void ConvertItemsToJson() {
  Gson gson = new Gson();
  Game game = Game.getInstance();
  GameManager gameManager = new GameManager();
  Set<Item> allItems = gameManager.getAllItems();

  Set<Room> rooms = game.getCorridorsMap().stream()
          .map(Corridor::getStartingRoom)
          .collect(Collectors.toSet());

  Set<Item> itemsToSave = allItems.stream()
          .filter(item -> !game.getInventory().contains(item))
          .filter(item -> rooms.stream()
                  .noneMatch(room -> room.getItems().contains(item)))
          .collect(Collectors.toSet());

  String json = gson.toJson(itemsToSave);
  try {
    Files.write(Paths.get("src", "main", "resources", "LoadedAgents.json"), json.getBytes());
  } catch (IOException e) {
    throw new RuntimeException(e);
  }
}
```

Il metodo <b>ConvertGameToJson</b> si occupa di convertire l'oggetto Game in formato JSON, mentre il metodo <b>ConvertAgentsToJson</b> si occupa di convertire gli oggetti di tipo Item in formato JSON, salvando solo gli oggetti che non sono presenti nell'inventario o in una stanza.


- **Inizializzione delle StopWords da txt**:

Per l'inizializzazione delle StopWords abbiamo utilizzato il metodo <b>setUpUselessWords</b> all'interno della classe <b>Parser</b>, che si occupa di leggere il file <b>StopWords.txt</b> e di inizializzarne la lista, come mostrato di seguito:
```java
private void setupUselessWords() throws IOException {
        File file = new File("src/main/resources/utilities/stopWords.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim().toLowerCase());
            }
        }
    }
```

L'utilizzo dei file ci ha permesso di memorizzare e recuperare i dati del gioco in modo persistente, garantendo la continuità dell'esperienza di gioco per gli utenti e facilitando la gestione e la manipolazione dei dati all'interno del gioco.<br>
Inoltre, a nostro avviso, utilizzare al i file JSON al posto di implementare Serialazible ci ha permesso di avere un codice più pulito e leggibile, in quanto i file JSON sono più comodi da manipolare rispetto ai file binari.
</details>
</li>
<li>
<h2>2) Utilizzo del Database</h2>
<details open>
<summary>Visualizza dettagli</summary>
<h3><b>Cosa è un Database?</b></h3>
Uno dei motivi di successo di Java è dovuto alla possibilità di sviluppare applicazioni client/server indipendenti dalla piattaforma:

- L’indipendenza dalla piattaforma deve essere garantita
  anche per applicazioni che lavorano su basi di dati: per questo è nato lo standard Java Data Base Connectivity (JDBC)

<h4><b>Come funziona JDBC?</b></h4>
JDBC è progettato per essere platform-independent.

Per permettere ciò JDBC fornisce un driver manager che gestisce dinamicamente tutti gli oggetti driver di cui hanno bisogno le interrogazioni a database, quindi:
- Pertanto se si hanno tre diversi DBMS allora necessiteranno tre diversi tipi di oggetti driver.
- Gli oggetti driver si registrano presso il driver manager al momento del caricamento.
- Come tutte le API Java anche JDBC è stato progettato in modo da semplificare tutte le normali operazioni di interfacciamento con un database: connessione, creazione di tabelle, interrogazione e visualizzazione dei risultati.

<h3><b>Come abbiamo utilizzato il Database nel nostro progetto?</b></h3>
Come richiesto dal professore è stato utilizzato H2, un database SQL open-source scritto in Java. È molto veloce e leggero, e supporta la modalità server e la modalità embedded.

H2 è molto popolare in ambito di sviluppo di applicazioni Java, in quanto è facile da usare e da configurare, dunque perfetto per il nostro progetto.
L'utilizzo del database ci ha permesso di memorizzare i dati relativi a tutti i dialoghi del gioco, i punteggi dei giocatori e le informazioni sui tempi di gioco, garantendo la persistenza dei dati e la possibilità di recuperarli in qualsiasi momento.
Un ulteriore vantaggio di H2 è il fatto di avere una console web che permette di visualizzare i dati del database in modo semplice e intuitivo, facilitando il debug e il controllo dei dati memorizzati, come mostrato di seguito:

![img_H2](../resources/img/database1.jpg)

A seguito dell'inserimento dei dati nel database, è possibile visualizzare i dati memorizzati all'interno del database, come mostrato di seguito:

![img_H2](../resources/img/database2.jpg)

Il database, come appreso durante il corso, ha bisogno di una connessione per poter essere utilizzato. Per questo motivo, abbiamo implementato la classe <b>DatabaseConnection</b> per gestire la connessione al database e le operazioni di lettura e scrittura dei dati, come mostrato di seguito:
```java
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/main/resources/database";
    static final String USER = "sa";
    static final String PASS = "";
```
La classe <b>DatabaseConnection</b> definisce le costanti per il driver JDBC, l'URL del database, l'utente e la password per la connessione al database.

All'interno della classe <b>DatabaseConnection</b> sono stati implementati i metodi per la connessione al database, la creazione delle tabelle, l'inserimento dei dati e la lettura dei dati, come mostrato di seguito:
```java
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
```

Il metodo <b>connect</b> si occupa di connettersi al database, creare la tabelle, controllare se essa sia vuota e popolarla con i dati di default, se necessario, restituendo la connessione al database.

Altri metodi fondamentali sono <b>close</b>, <b>printFromDB</b> e <b>getDescriptionFromDatabase</b> che permettono di chiudere la connessione al database, inserire i dati nel database, stampare i dati dal database, ottenere la classifica dei giocatori e ottenere le descrizioni delle stanze dal database, rispettivamente.
Andiamo a vederli nello specifico:
```java

public static void close(Connection conn) {
    if (conn != null) {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
```
Il metodo <b>close</b> si occupa di chiudere la connessione al database, se è aperta.

```java
  public static void printFromDB(String comando, String stanza, String stato, String personaggio, String oggetto) {
        String statoDB = stato.equalsIgnoreCase("true") ? "Libero" : "Sorvegliato";
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
```
Il metodo <b>printFromDB</b> di stampare la descrizione di un elemento (comando, stanza, stato, personaggio o oggetto), in base ai parametri passati, ottenuti dal database.

In conclusione l'utilizzo del database ci ha fornito un modo comodo ed efficiente per memorizzare e recuperare i dialoghi del gioco.

</details>
</li>
<li>
<h2>3) Utilizzo dei Thread</h2>
<details open>
<summary>Visualizza dettagli</summary>
<h3><b>Cosa sono i Thread?</b></h3>
Un <b>Thread</b> è un flusso di esecuzione di un programma, ossia un processo che può essere eseguito in modo indipendente dagli altri processi. In Java, i Thread sono utilizzati per eseguire operazioni in modo concorrente, permettendo di sfruttare al massimo le risorse del sistema e di migliorare le prestazioni delle applicazioni.

La classe Thread di Java implementa tutte le funzionalità di un singolo thread e può essere creata in due modi:
- Estendendo la classe Thread, che prevede l'implementazione del metodo run() che contiene il codice da eseguire nel thread.
- Implementando l'interfaccia Runnable, che prevende il metodo run() che contiene il codice da eseguire nel thread.

A prescindere dal metodo utilizzato, il thread deve essere avviato chiamando il metodo start(), che avvia l'esecuzione del thread e chiama il metodo run().

Il thread può essere interrotto chiamando il metodo interrupt(), che invia un segnale di interruzione al thread, che può essere catturato e gestito nel metodo run().

Esiste anche il metodo join(), che permette di attendere che il thread termini la sua esecuzione.

<h3><b>Come abbiamo utilizzato i Thread nel nostro progetto?</b></h3>
Nel nostro progetto abbiamo utilizzato i Thread per gestire operazioni quali la riproduzione della musica di sottofondo, la gestione dell'input da parte dell'utente e il timer di gioco.

- **Musica di sottofondo**:
<br>
La musica di sottofondo è stata implementata utilizzando un thread separato, in modo che  possa essere riprodotta indipendentemente dal resto del gioco e possa essere interrotta o riprodotta nuovamente in qualsiasi momento, sia dal menù principale che dalla schermata di gioco, con ben 9 traccie audio diverse.

La classe che gestisce la musica di sottofondo è la classe <b>Mixer</b>, che estende la classe Thread e si occupa di caricare e riprodurre la musica di sottofondo, come mostrato di seguito:
```java
public class Mixer extends Thread {
    private static Clip[] clips; // array of clips
    private static int currentClip; // current clip index 
    private static boolean running = false; // running status
    private static HashMap<String, Integer> roomToClipIndex; // map room to clip index
    private static Mixer instance; // instance of Mixer

    private Mixer() {
        clips = new Clip[9];
        // load clips..

        // map room to clip instantiation
        roomToClipIndex = new HashMap<>();

        // populate map
    }
}
```
Il costruttore della classe <b>Mixer</b> si occupa di inizializzare l'array di Clip, l'indice corrente del clip, lo stato di esecuzione e la mappatura delle stanze all'indice della clip corrispondente

```java  
  private void loadClip(int index, String filePath) {
    try {
      File file = new File(filePath);
      AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
      clips[index] = AudioSystem.getClip();
      clips[index].open(audioStream);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static Mixer getInstance() {
    if (instance == null) {
      instance = new Mixer();
    }
    return instance;
  }
  ```

Il metodo <b>loadClip</b> si occupa di caricare il file audio in un oggetto Clip, mentre il metodo <b>getInstance</b> restituisce l'istanza della classe Mixer, garantendo che esista solo un'istanza della classe.

```java

  @Override
  public void run() {
    running = true;
    try {
      if (clips[0] != null) {
        clips[0].start();
        clips[0].loop(Clip.LOOP_CONTINUOUSLY);
        currentClip = 0;
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


  public static void startClip() {
    if (clips[currentClip] != null) {
      running = true;
      reverseIcons();
      clips[currentClip].start();
    }
  }
  ```

Il metodo <b>run</b> viene sovrascritto per avviare il thread e riprodurre la musica di sottofondo in modo continuo, mentre il metodo <b>startClip</b> viene utilizzato per avviare la riproduzione della musica.

```java
  public static void stopClip() {
    if (clips[currentClip] != null) {
      running = false;
      reverseIcons();
      clips[currentClip].stop();
    }
  }
  
  private static void changeClip(int i) {
    getInstance();
    if (running) {
      if (clips[currentClip] != null) {
        clips[currentClip].stop();
      }
      if (clips[i] != null) {
        clips[i].start();
        clips[i].loop(Clip.LOOP_CONTINUOUSLY);
      }
    }
    currentClip = i;
  }
  ```
  
Il metodo <b>stopClip</b> viene utilizzato per fermare la riproduzione della musica, mentre il metodo <b>changeClip</b> viene utilizzato per cambiare il clip audio in base all'indice passato come parametro.

```java
  public static boolean isRunning() {
    return running;
  }
  
  public static void reverseIcons() {
    if (!running) {
      musicButtonSetTextGame("🔇");
      musicButtonSetTextMenu("🔇");
    } else {
      musicButtonSetTextGame("🔊");
      musicButtonSetTextMenu("🔊");
    }
  }
  
  public static void changRoomMusic(String room) {
        getInstance();
        if (roomToClipIndex != null) {
            changeClip(roomToClipIndex.getOrDefault(room, 3));
        } else {
            changeClip(3);
        }
    }

```
Il metodo <b>isRunning</b> restituisce lo stato di esecuzione della musica, il metodo <b>reverseIcons</b> cambia l'icona del bottone della musica in base allo stato di esecuzione e il metodo <b>changeRoomMusic</b> cambia il clip audio in base alla stanza passata come parametro.

- **Input da parte dell'utente**:

L'input da parte dell'utente è stato gestito utilizzando un thread separato, che è sempre in ascolto sul campo di testo in modo che i comandi inseriti dall'utente possano essere presi in modo indipendente e immediato.

La classe che gestisce l'input da parte dell'utente è la classe <b>UserInputManager</b> che si occupa di ricevere i comandi dall'utente e trasmetterli alla classe <b>UserInputFlow</b> per il corretto instradamento, come mostrato di seguito:
```java
public static void startInputListener() {
    new Thread(() -> {
        while (true) {
            if (!isCurrentInputEmpty()) {
                UserInputFlow.gameFlow(getCurrentInput());
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }).start();
}

```
Questo codice gestisce l'ascolto degli input dell'utente. La funzione <b>startInputListener</b> avvia un thread che monitora continuamente il campo di input dell'utente. 

<b>Altri Thread sono stati istanziati ed utilizzati per piccoli task da eseguire contemporaneamente ad altre operazioni, come ad esempio:</b>

- **Setup degli elementi del gioco per una nuova partita**.
- **Setup degli elementi del gioco per una partita caricata da un file di salvataggio**.
- **Run del RestServer in background**.
- **Gestione del timer di gioco, attraverso la classe Timer**.
- **Timer per il cambiamento dei panel all'interno del gioco**.


Implementando i Thread all'interno del gioco siamo riusciti a garantire un'esperienza di gioco fluida e reattiva, permettendo all'utente di interagire con il gioco in modo intuitivo e coinvolgente, non accorgendosi della presenza di operazioni in background.

  </details>
  </li>
  <li>
    <h2>4) Utilizzo dei Socket</h2>
    <details open>
        <summary>Visualizza dettagli</summary>
        <h3><b>Cosa sono i socket ?</b></h3>
        In Java si usa un socket per creare la connessione ad un’altra macchina attraverso la rete. In particolare, per stabilire una connessione fra due computer occorrerà disporre di un socket su ogni macchina.Quest'ultimo non è che una astrazione software usata per
        rappresentare i terminali di una connessione tra due macchine.

In particolare, Java utilizza, per la comunicazione in rete, il <b>modello a stream</b>.

Un socket può mantenere due tipi di stream: uno di input ed uno di output. Dal punto di vista software, ciò che avviene è che un processo invia dei dati ad un altro processo attraverso la rete, scrivendo sullo stream di output associato ad un socket. Un altro processo, accede ai dati scritti in precedenza leggendo dallo stream di input del socket stesso.

Ci sono due classi socket basate su stream:

- ServerSocket che il server usa per ascoltare una
  richiesta di connessione
- Socket usata dal client per inizializzare la connessione<br>

Una volta che un client richiede una connessione
socket, il ServerSocket restituisce  un Socket corrispondente
attraverso il quale la comunicazione può avvenire
dal lato server.

Generalmente quando si crea un Serversocket, si specifica solo la porta del server a cui ci si vuole connettere, mentre quando si crea un Clientsocket si specifica, oltre alla porta del server a cui ci si vuole connettere, anche l'indirizzo IP del server.

Nel nostro caso, essendo il server e il client sulla stessa macchina, non è stato necessario specificare l'indirizzo IP.
<h3><b>Come abbiamo utilizzato i Socket nel nostro progetto?</b></h3>
Nel menu  del nostro gioco è presente un bottone "🌐", che apre, al click, una pagina web contenente le informazioni principali del gioco e le istruzioni per giocare.

Questo bottone è stato implementato utilizzando i socket sulla porta 8080, in particolare il metodo <b>openWebpage</b> che apre una connessione con il browser predefinito del sistema e carica la pagina web desiderata.
<h4><b>Che informazioni contiene il sito web?</b></h4>
Ecco come appare il sito web:

![img_sito_web1](../resources/img/immagine_sito_web1.jpg)
![img_sito_web2](../resources/img/immagine_sito_web2.jpg)

Il sito web contiene le seguenti informazioni:
- **Titolo del gioco**: il nome del gioco, ossia "Avventura nella Piramide".
- **Indice linkato**: permette di accedere rapidamente alle varie sezioni del sito web.
- **Avvenimenti nel gioco**: un breve racconto della storia del gioco.

Ovviamente ciascuna sezione contiene, al suo termine, un link che permette di tornare all'indice del sito web.

<h4><b>Come è stato implementato il socket?</b></h4>
All'interno del nostro codice, in particolare nel package "Database", sono presenti le classi che hanno permesso l'implementazione del socket.

Andiamo ad osservare il codice nel dettaglio:
```java
// Codice per l'inizializzazione del RestServer

public void startServer() throws IOException {
  HttpServer server = HttpServer.createSimpleServer("/", 8080);
  ServerConfiguration config = server.getServerConfiguration();
  config.addHttpHandler(new DatabaseHandler(), "/api/data");
  // Thread per avviare il server..
}
``` 
Nel metodo <b>startServer()</b> viene creato un oggetto di tipo <b>HttpServer</b> che si mette in ascolto sulla porta 8080. Viene inoltre aggiunto un <b>HttpHandler</b> che si occupa di gestire le richieste in arrivo sulla stessa porta.

In conclusione, l'utilizzo dei socket ci ha permesso di creare un sito web per il nostro gioco, per permettere agli utenti di accedere alla storia completa del nostro gioco.

</details>
</li>
<li>
    <h2>5) Utilizzo di Java Swing</h2>
    <details open>
        <summary>Visualizza dettagli</summary>
          <h3><b>Cosa è Java Swing?</b></h3>
        Java Swing è stato introdotto per la prima volta con Java 1.2 e ha sostituito il vecchio toolkit AWT (Abstract Window Toolkit) ed è il framework che permette la realizzazione di interfacce grafiche (GUI).

Essendo una libreria grafica, Java Swing permette di creare interfacce grafiche per applicazioni desktop, offrendo una vasta gamma di componenti grafici tra cui:

- Finestre, Form, Dialog
- Menu, Pulsanti, Check-box, Combo-box
- Alberi, Tabelle
- Layout, Look&Feel

<h3><b>Come abbiamo utilizzato Java Swing nel nostro progetto?</b></h3>
Il nostro progetto è stato sviluppato utilizzando interamente Java Swing per la realizzazione dell'interfaccia grafica del gioco, dal momento che volevamo creare un'esperienza di gioco coinvolgente e interattiva per l'utente.

Esso vanta lo sviluppo di una GUI molto ricca e complessa utilizzando un unico JFrame, che contiene tutti i componenti grafici necessari per l'interazione con l'utente attraverso l'utilizzo di JPanel e CardLayout.

L'utilizzo del Cardlayout, contenente al suo interno i vari JPanel, ci ha permesso di creare una GUI dinamica e flessibile, in grado di passare da una schermata all'altra in modo fluido e intuitivo e sopratutto di permettere sviluppi futuri senza dover modificare il codice esistente.

All'interno del nostro progetto, la classe principale che gestisce la GUI è la classe <b>ManagerGUI</b>, che estende la classe JFrame e si occupa di inizializzare e gestire tutti i componenti grafici del gioco, come mostrato di seguito:
```java

public class ManagerGUI extends JFrame {
  static GameGUI game; // The game GUI
  
  public ManagerGUI() {
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Metal Gear");
        setPreferredSize(new Dimension(800, 600));
        setResizable(false);
        try {
            Image icon = ImageIO.read(new File("src/main/resources/img/solid_snake_icon.jpg"));
            setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    // Create the cards
    JPanel cards = new JPanel(new CardLayout());
    MenuGUI menu = new MenuGUI();
    CreditsGUI credits = new CreditsGUI();
    game = new GameGUI();
    
    // Add the panels to cards
    cards.add(menu, "MenuGUI");
    cards.add(credits, "CreditsGUI");
    cards.add(game, "GameGUI");
    // Start the frame and the music...
  }
  
  public static void closeGame() {
    game.goBack(); // Go back to the menu
  }
  
}

```
La classe <b>ManagerGUI</b> contiene solo un  metodo e  il costruttore, che si occupa di inizializzare e mostrare la finestra principale del gioco, impostando le proprietà del JFrame e creando i vari JPanel che costituiscono la GUI del gioco.
<br>
I panel verranno aggiunti al CardLayout, che permette di passare da una schermata all'altra in modo fluido e intuitivo.

Il metodo <b>closeGame()</b> permette di tornare al menu principale del gioco, chiamando il metodo <b>goBack()</b> del pannello del gioco.

Nel momento in cui il gioco viene avviato, viene mostrato il Menu principale, che contiene vari pulsanti per iniziare una nuova partita, caricare una partita salvata, visualizzare i crediti, aprire il sito web del gioco e attivare o disattivare la musica di sottofondo, come mostrato di seguito:
![img_MenuGUI](../src/main/resources/img/titleScreen.jpg)

La classe che gestisce il Menu principale è la classe <b>MenuGUI</b>, che estende la classe JPanel e contiene tutti i componenti grafici del Menu, come mostrato di seguito:
```java

public class MenuGUI extends JPanel {
    private JPanel backgroundPanel; // The background panel
    private JButton newGame; // The new game button
    private static JButton sound; // The sound button
    private JButton help; // The help button
    private JButton loadGame; // The load game button
    private JButton credits;  // The credits button
    private JButton site; // The site button


    public MenuGUI() {
        initComponents();
    }


    private void initComponents() {
        // Create the components
        // Sets the background image
        // Initialize the buttons and set the properties...
        // Create  an action listener for each button...
        // Add the buttons to the panel...
        // Set the layout of the panel...
    }
}
```
La classe **MenuGUI** rappresenta il pannello principale del gioco e contiene vari pulsanti per azioni diverse. I componenti principali includono:

- backgroundPanel: un pannello che disegna un'immagine di sfondo.
- pulsanti: newGame, sound, help, loadGame, credits.

Nel costruttore, viene chiamato initComponents() per configurare i componenti della GUI.

Il metodo initComponents imposta il Jpanel dello sfondo e configura i vari pulsanti con testo e dimensioni specificate, aggiungendo un ActionListener a ciascun tasto per gestire i click.

La classe che gestisce la schermata di gioco è la classe <b>GameGUI</b>, che estende la classe JPanel e contiene tutti i componenti grafici del gioco, come mostrato di seguito:
```java
  public class GameGUI extends JPanel {
    private JButton goBackButton; // The go back button
    private JButton saveGameButton; // The save game button
    private JButton helpButton; // The help button
    private static JButton musicButton; // The music button
    private static JLabel timerLabel; // The timer label
    private static JPanel imagePanel; // The image panel
    private static JTextPane displayTextPane; // The display text pane 
    private JScrollPane scrollPaneDisplayText; // The scroll pane display text
    private static JTextArea inventoryTextArea; // The inventory text area
    private JScrollPane scrollPaneInventoryText; // The scroll pane inventory text
    private JTextField userInputField; // The user input field 
    private JToolBar toolBar; // The tool bar
    private static CardLayout cardLayout; // The card layout

    public GameGUI() {
        UIManager.put("ScrollBar.width", 0); // Set the width of the scroll bar to 0
        SwingUtilities.updateComponentTreeUI(this); // Update the UI of the component
        initComponents();
        initImagePanel();
    }

    initImagePanel() {
        // Create the image panel...
        // Adds all the images in the game to a CardLayout...
        // Assigns the card layout to the image panel...
    }

    private void initComponents() {
        // Create the components...
        // Set the properties of the components...
        // Create an action listener for each button...
        // Add the components to the panel...
        // Set the layout of the panel...
    }
}
    
```    
Allo stesso modo di <b>MenuGUI</b>, la classe <b>GameGUI</b> rappresenta il pannello principale del gioco e contiene vari pulsanti per azioni diverse, ciascuno con i propri ActionListener per gestire i click.
Allo stesso modo di <b>MenuGUI</b>, la classe <b>GameGUI</b> rappresenta il pannello principale del gioco e contiene vari pulsanti per azioni diverse, ciascuno con i propri ActionListener per gestire i click.


Il caricamento di una partita salvata avviene  allo stesso modo di una nuova partita, con l'unica differenza che il gioco viene caricato da un file di salvataggio.


Dopo aver cliccato il pulsante "Riconoscimenti", viene mostrata una schermata con i nomi dei membri del team di sviluppo, come mostrato di seguito:

![img_Credits](../resources/img/Credits.jpg)


La classe che gestisce i Riconoscimenti è la classe <b>RiconoscimentiGUI</b>, che estende la classe JPanel e contiene tutti i componenti grafici dei Riconoscimenti, come mostrato di seguito:

```java

public class CreditsGUI extends JPanel {
  private JButton goBack; // The go back button
  private JLabel titleLabel; // The title label
  private JPanel backgroundPanel; // The background panel 
  private JPanel marcoIcon; // The marco icon panel
  private JPanel fabryIcon; // The pasco icon panel
  private JPanel ricciIcon; // The mik icon panel
  private JLabel contentLabel; // The content label

  public CreditsGUI() {
    initComponents();
  }

  initComponents() {
    // Create the components...
    // Set the properties of the components...
    // Create an action listener for the button...
    // Add the components to the panel...
    // Set the layout of the panel...
  }
}
```

Nel Menu principale del gioco è presente un bottone "Help", che apre, al click, una finestra di dialogo contenente le istruzioni per giocare, come mostrato di seguito:

![img_HelpGUI](../resources/img/HelpGUI.jpg)

La classe che mostra i comandi è la classe <b>HelpGUI</b>, che estende la classe JFrame e, con l'aggiunta di una JLabel permette la visualizzazione delle istruzioni del gioco.

All'interno del gioco sono presenti dei minigiochi, uno dei quali non è semplicemente testuale, ma possiede la propria controparte grafica: il Wordle, che è stato implementato attraverso la classe <b>BoxLetter</b> che è strutturata nel seguente modo:
```java
public class BoxLetter {
    
  private JTextField textField; // The text field

  
  public BoxLetter() {
        textField = new JTextField(1); // Single Agent input
        textField.setFont(new Font("Otacon", Font.BOLD, 30));
        textField.setHorizontalAlignment(JTextField.CENTER);
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new SingleCharDocumentFilter());
    }

  public JTextField getTextField() {
    return textField;
  }
  
  protected void setBoxColor(Color color) {
    textField.setBackground(color);
  }
  
  private static class SingleCharDocumentFilter extends DocumentFilter {
      @Override
  public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
    if ((fb.getDocument().getLength() + string.length()) <= 1 && string.matches("[A-Z]")) {
      super.insertString(fb, offset, string, attr);
              }
         }
    }
}
```

Questa classe rappresenta una casella di testo in cui l'utente può inserire una singola lettera, in modo da indovinare la parola nascosta.
Assieme alla GUI, abbiamo implementato anche la logica del gioco, che permette all'utente di inserire una lettera e di verificare se la lettera è presente nella parola nascosta, come mostrato di seguito:

![img_Wordle](../resources/img/Wordle.gif)

In conclusione, l'utilizzo di Java Swing ci ha permesso di creare un'interfaccia grafica coinvolgente e interattiva per il nostro gioco, rendendo l'esperienza di gioco più piacevole e stimolante per l'utente.
  </details>
</li>
<li>
<h2>6) Utilizzo delle Java REST</h2>
<details open>
    <summary>Visualizza dettagli</summary>
      <h3><b>Cosa è la Java REST?</b></h3>
La <b>Java Representational State Transfer (REST)</b> è un tipo di architettura software per i sistemi distribuiti ed è basata sulla trasmissione di dati tramite protocollo <b>HTTP</b>.

Proprio perché utilizza il protocollo HTTP, l'utilizzo principale della <b>Java REST</b> è quello di andare ad effettuare delle richieste ad un server e riceverà o invierà informazioni a seconda del verbo HTTP utilizzato.

I verbi HTTP principali sono:

- <b>GET</b>: per recuperare informazioni dal server.
- <b>POST</b>: per inviare informazioni al server.
- <b>PUT</b>: per aggiornare informazioni sul server.
- <b>DELETE</b>: per eliminare informazioni dal server.


<h3><b>Come abbiamo utilizzato la Java REST nel nostro progetto?</b></h3>


Nel nostro progetto abbiamo utilizzato le <b>Java REST</b> per rendere gli enigmi nelle stanze dinamici, in modo che l'utente possa avere un'esperienza di gioco più coinvolgente e varia, e andando a simulare in locale quello che è lo scambio e la ricezione dati di tipo client-server.


- **Enigma Finale per completare la missione**:
  nell'ultima stanza del gioco, ossia il corridio finale, l'IA che gestisce la base pone un enigma all'utente, ossia indovinare una parola segreta di 5 lettere per poter scappare e portare a termine la missione. Questo enigma è stato implementato utilizzando la Java REST, in modo che l'utente possa ricevere una parola diversa ogni partita, rendendo il gioco più interessante e stimolante.

L'API da noi utilizzata per generare le parole casuali è <a href="https://random-word-api.herokuapp.com/home">Random Word API</a>.
'interfaccia dell'API è la seguente:
![img_Random_Words_API](../resources/img/immagine_api1.png)
Come è possibile visualizzare dalla foto, questa API è molto ricca di endpoint, ognuno dei quali permette di ottenere informazioni diverse.

Tra le varie possibilità offerte da questa API, la funzionalità che ci interessava era quella di ottenere una parola casuale di una determinata lunghezza, dunque abbiamo scelto l'endpoint "/word" e abbiamo impostato il parametro "length" a 5, in modo da ottenere una parola di 5 lettere.<br>
Inoltre nell' endpoint "/languages" è possibile visualizzare la lingua in cui è possibile ottenere le parole casuali.<br>
A questo punto, modificando il link su cui effettuare la richiesta, possiamo ottenere la parola casuale di 5 lettere in italiano, come mostrato di seguito:
```http request
https://random-word-api.herokuapp.com/word?lang=it&length=5
```
Oltre quindi a fornirci immediatamente il link per effettuare la richiesta, possiamo notare anche la risposta in formato JSON e la sua semplice struttura:
```json
["venti"]
```
Il json restituito è un array con solo un elemento , contenente la parola casuale di 5 lettere in italiano, per questo motivo molto semplice da gestire e da interpretare.<br>
Per parsarlo e ottenere la parola casuale, non è stato nemmeno necessario usare la libreria Gson, bensì un banalissimo replace per eliminare le parentesi quadre e le virgolette.

Il codice per fare la GET e parsare la risposta è il seguente:
```java
public WordleGame() {
  String guessingWord1;
  StringBuilder result = new StringBuilder();

  try {
    URL url = new URL("https://random-word-api.herokuapp.com/word?lang=it&length=5");
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

    conn.setRequestMethod("GET");
    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(conn.getInputStream()))) {
      for (String line; (line = reader.readLine()) != null; ) {
        result.append(line);
      }
    }
    guessingWord1 = result.toString()
            .replace("[", "")
            .replace("]", "")
            .replace("\"", "")
            .toUpperCase();

  } catch (IOException e) {
    guessingWord1 = "SALVE";
  }
  GuessingWord = guessingWord1;
}
```

- **Enigma per poter entrare nel livello intermedio della base**:
  All'interno della stanza numero 5 della nostra mappa abbiamo deciso di rendere difficile l'ingresso al livello intermedio mettendo alla prova l'utente con una serie di domande di varie categorie, dalla cultura generale a domande sui Computer o videogiochi. Per poter superare questa prova l'utente dovrà rispondere correttamente a 3 domande consecutive e, in caso di risposta errata, dovrà ricominciare da capo.

Per implementare questa funzionalità abbiamo utilizzato la Java REST, in particolare andando a cercare un database che permettesse di ottenere domande di varie categorie e difficoltà.

A nostro avviso l'API più adatta per questo scopo è <a href="https://opentdb.com/">Open Trivia Database</a>, un database di domande di trivia open-source che offre una vasta gamma di domande di varie categorie e difficoltà.

L'interfaccia del database è la seguente:
![img_Open_Trivia_Database](../resources/img/immagine_trivia1.png)

A questo punto, cliccando il pulsante API, possiamo andare ad analizzare l'interfaccia di nostro maggiore interesse, ossia la richiesta per ottenere domande di varie categorie e difficoltà:

![img_Open_Trivia_Database](../resources/img/immagine_trivia2.png)
I parametri possibili per la richiesta sono i seguenti:
- <b>amount</b>: il numero di domande da ottenere, nel nostro caso 1.
- <b>category</b>: l'ID della categoria delle domande, nel nostro  caso la scelta è stata lasciata a "Any Category", indicando che le domande possono essere di qualsiasi categoria.
- <b>difficulty</b>: la difficoltà delle domande, nel nostro caso la scelta è stata "Easy", indicando che le domande saranno di facile difficoltà, vista già la complessità della prova.
- <b>type</b>: il tipo di domande (multiple o boolean), nel nostro caso la scelta è stata "boolean", indicando che la risposta sarà vera o falsa.
- <b>encode</b>: il tipo di encoding della risposta, nel nostro caso la scelta è stata lasciata a "Default", evitando di aggiungere campi inutili alla richiesta.

A questo punto, cliccando il pulsante "Generate API URL", possiamo ottenere il link per effettuare la richiesta e vedere il risultato, che con i campi modificati risulta essere il seguente:


```http request
https://opentdb.com/api.php?amount=1&difficulty=easy&type=boolean
```


Per quanto riguarda la risposta, la struttura del JSON restituito è la seguente:
```json
{
  "response_code": 0,
  "results": [
    {
      "category": "General Knowledge",
      "type": "boolean",
      "difficulty": "easy",
      "question": "The Great Wall of China is visible from the moon.",
      "correct_answer": "False",
      "incorrect_answers": [
        "True"
      ]
    }
  ]
}
```


Dopo aver compreso il funzionamento dell'API, abbiamo implementato la richiesta all'interno del nostro codice Java, attraverso una semplice <b>GET</b>, come mostrato di seguito:
```java
  public static void getQAndA() {
  String urlToRead = "https://opentdb.com/api.php?amount=1&difficulty=easy&type=boolean";
  int maxAttempts = 3;

  // Try to get the question and answer from the API
  // if it fails 3 times, the player will have to talk to the mummy again
  for (int attempt = 0; attempt < maxAttempts; attempt++) {
      try {
          URL url = new URL(urlToRead);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("GET");

          StringBuilder result = new StringBuilder();
          try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
              String line;
              while ((line = reader.readLine()) != null) {
                  result.append(line);
              }
          }

          JsonObject jsonObject = JsonParser.parseString(result.toString()).getAsJsonObject();

          question = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().get("question").getAsString().replace("&quot;", "\"")
                  .replace("&amp;", "&").replace("&apos;", "'").replace("&lt;", "<")
                  .replace("&gt;", ">").replace("&#039;", "'").replace("&eacute;", "é").replace("&egrave;", "è");
          correctAnswer = jsonObject.getAsJsonArray("results").get(0).getAsJsonObject().get("correct_answer").getAsString();

          displayQuestion(question);
          break;
        } catch (IOException e) {
      }
    }
}
```

Come è possibile notare dal codice, la richiesta viene effettuata attraverso un ciclo <b>for</b>, dovuto principalmente al fatto che in fase di testing l'API non rispondeva sempre correttamente, dunque abbiamo deciso di effettuare più tentativi per ottenere la singola domanda e salvarla nella variabile <b>question</b>.<br>

Entrambe verranno utilizzate per visualizzare la domanda e controllare la risposta data dall'utente.
