package org.it.uniba.fox.InteractionManager;
import org.it.uniba.fox.DB_Web.DatabaseConnection;
import org.it.uniba.fox.Entity.Game;
import org.it.uniba.fox.GUI.GameGUI;
import org.it.uniba.fox.GUI.WordleGUI;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The class that manages the Wordle game.
 */
public class WordleGame {
    /**
     * The constant MAX_LETTERS that represents the maximum number of letters in the word.
     */
    private final int MAX_LETTERS = 5;
    /**
     * The constant MAX_ATTEMPTS that represents the maximum number of attempts.
     */
    private final int MAX_ATTEMPTS = 6;
    /**
     * The current attempt.
     */
    private int currentTry = 0;
    /**
     * The word to guess.
     */
    private final String GuessingWord;

    /**
     * Instantiates a new WordleGame and takes the word to guess using an API.
     */
    public WordleGame() {
        String guessingWord1;
        StringBuilder result = new StringBuilder();
        int timeout = 4000;

        /*
         * Sfortunatamente all'ultimo secondo abbiamo scoperto che l'API è in down e non sappiamo
         * se tornerà a funzionare. Abbiamo quindi deciso di utilizzare una seconda API per generare
         * la parola da indovinare. Questa seconda API è però in inglese e non in italiano.
         * Inoltre se anche questa API dovesse smettere di funzionare, abbiamo una parola di default.
         */
        try {
            URL url = new URL("https://random-word-api.herokuapp.com/word?lang=it&length=5");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);

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
            try {
                URL url = new URL("https://random-word.ryanrk.com//api/en/word/random/?minlength=5&maxlength=5");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setConnectTimeout(timeout);
                conn.setReadTimeout(timeout);

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

            } catch (IOException e1) {
                guessingWord1 = "SALVE";
            }
        }
        GuessingWord = guessingWord1;
    }

    /**
     * Manages the guess of the user.
     *
     * @param text the user input
     */
    public void manageGuess(String text) {
        // Check if the word has the correct length
        // Sets the text in the boxes
        // Checks the guess
        if(checkLength(text)){
            String[] newText = text.split("");
            WordleGUI game = GameGUI.getWordle();
            for (int j = 0; j < MAX_LETTERS; j++) {
                game.setBoxText(currentTry, j, newText[j]);
            }
            checkGuess(text);
            currentTry++;
        }

        // Checks if the word is correct
        // if it is the wordle game ends and the user wins
        if (text.equals(GuessingWord)) {
            DatabaseConnection.printFromDB("0", "Stanza12", "Corretto", "IA", "0");
            UserInputFlow.Event = 0;
            Game game = Game.getInstance();
            game.getCurrentRoom().setFree(true);
            game.unlockCorridor("Desert", "Stanza1");
            GameGUI.setImagePanel(game.getCurrentRoom().getName());
            return;
        }

        // Checks if the user has reached the maximum number of attempts
        // if it is the wordle game ends and the user loses
        if (currentTry == MAX_ATTEMPTS) {
            DatabaseConnection.printFromDB("0", "Stanza12", "Sbagliato", "IA", "0");
            OutputDisplayManager.displayText("> \"La parola corretta era: " + GuessingWord + "!\"");
            UserInputFlow.Event = 0;
            Game game = Game.getInstance();
            game.getCurrentRoom().setFree(false);
            GameGUI.setImagePanel(game.getCurrentRoom().getName());
        }
    }

    /**
     * Checks the length of the word.
     *
     * @param text the user input
     * @return true if the word has the correct length, false otherwise
     */
    private boolean checkLength(String text) {
        if (text.length() != MAX_LETTERS) {
            OutputDisplayManager.displayText("> \"Come puoi vedere, la parola non è di esattamente 5 lettere!\"");
            return false;
        }
        return true;
    }

    /**
     * Changes box color.
     *
     * @param row   the row of the box to change
     * @param col   the col of the box to change
     * @param color the color to set
     */
    private void changeBoxColor(int row, int col, Color color) {
        WordleGUI game = GameGUI.getWordle();

        game.setBoxColor(row, col, color);
    }

    /**
     * Checks the guess of the user.
     *
     * @param guess the guess of the user
     */
    private void checkGuess(String guess) {
        String[] charAlreadyGuessed = guess.split("");
        String[] charGuessingWord = GuessingWord.split("");

        // Check if a letter is in the exact position
        for(int i = 0; i < MAX_LETTERS; i++) {
            if (charGuessingWord[i].equals(charAlreadyGuessed[i])) {
                changeBoxColor(currentTry, i, new Color(23, 147, 10));
                charGuessingWord[i] = "";
                charAlreadyGuessed[i] = "";
            }
        }

        // Check if a letter is in the word but not in the exact position
        for (int i = 0; i < MAX_LETTERS; i++) {
            for (int j = 0; j < MAX_LETTERS; j++) {
                if (charGuessingWord[i].equals(charAlreadyGuessed[j]) && !charGuessingWord[i].isEmpty()) {
                    changeBoxColor(currentTry, j, new Color(178, 149, 22));
                    charGuessingWord[i] = "";
                    charAlreadyGuessed[j] = "";
                }
            }
        }

        // Check if a letter is not in the word
        for (int i = 0; i < MAX_LETTERS; i++) {
            if (!charAlreadyGuessed[i].isEmpty()) {
                changeBoxColor(currentTry, i, new Color(47, 55, 61));
            }
        }
    }
}
