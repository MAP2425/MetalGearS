package org.it.uniba.fox.Util;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.HashMap;

import static org.it.uniba.fox.GUI.GameGUI.musicButtonSetTextGame;
import static org.it.uniba.fox.GUI.MenuGUI.musicButtonSetTextMenu;

/**
 * The class that manages the music.
 */
public class Mixer extends Thread {
    /**
     * The array of songs.
     */
    private static Clip[] clips;
    /**
     * The current song.
     */
    private static int currentClip;
    /**
     * The running status of the music.
     */
    private static boolean running = false;
    /**
     * The map of rooms to music index.
     */
    private static HashMap<String, Integer> roomToClipIndex;
    /**
     * The instance of the Mixer.
     */
    private static Mixer instance;

    /**
     * The constructor of the Mixer.
     */
    private Mixer() {
        clips = new Clip[9];
        // menu music
        loadClip(0, "src/main/resources/audio/Metal Gear Solid 2- Main Theme 8-bit.wav");
        //rooms music
        loadClip(1, "src/main/resources/audio/MG2 - SS OST [27 - INFILTRATION (Alert Mode 1 part7)].wav");
        loadClip(2, "src/main/resources/audio/MG2 - SS OST [26 - FIGHT INTO ENEMY TERITORY (Sneaking into Enemy Territory BGM)].wav");
        loadClip(3, "src/main/resources/audio/MG2 - SS OST [28 - IN SECURITY (Holly is Captured)].wav");
        loadClip(4, "src/main/resources/audio/MG2 - SS OST [15 - MECANIC (Boss BGM 2)].wav");
        loadClip(5, "src/main/resources/audio/MG2 - SS OST [13 - BATTLE AGAINST TIME (Boss BGM 1)].wav");
        loadClip(6, "src/main/resources/audio/Metal Gear (NES) Music - Super Computer Area.wav");
        loadClip(7, "src/main/resources/audio/Metal Gear (NES) Music - Boss Battle.wav");
        loadClip(8, "src/main/resources/audio/Metal Gear (NES) Music - Password Theme.wav");

        // map room to clip instantiation
        roomToClipIndex = new HashMap<>();

        roomToClipIndex.put("Menu", 0);

        roomToClipIndex.put("WordleGUI", 5);

        roomToClipIndex.put("Stanza1", 1);
        roomToClipIndex.put("Stanza2", 2);
        roomToClipIndex.put("Stanza3", 2);
        roomToClipIndex.put("Stanza4", 3);
        roomToClipIndex.put("Stanza5", 7);
        roomToClipIndex.put("Stanza6", 1);
        roomToClipIndex.put("Stanza7", 4);
        roomToClipIndex.put("Stanza8", 4);
        roomToClipIndex.put("Stanza9", 8);
        roomToClipIndex.put("Stanza10", 3);
        roomToClipIndex.put("Stanza11", 5);
        roomToClipIndex.put("Stanza12", 7);
    }

    /**
     * Load the clip from the file path.
     *
     * @param index    the index of the clip
     * @param filePath the file path
     */
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

    /**
     * Gets the instance of the Mixer.
     *
     * @return the instance
     */
    public static Mixer getInstance() {
        if (instance == null) {
            instance = new Mixer();
        }
        return instance;
    }

    /**
     * Override of the run method.
     * Starts the menu music.
     */
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

    /**
     * Starts the music.
     */
    public static void startClip() {
        if (clips[currentClip] != null) {
            running = true;
            reverseIcons();
            clips[currentClip].start();
        }
    }

    /**
     * Stops the music.
     */
    public static void stopClip() {
        if (clips[currentClip] != null) {
            running = false;
            reverseIcons();
            clips[currentClip].stop();
        }
    }

    /**
     * Changes the music.
     *
     * @param i the index of the music
     */
    private static void changeClip(int i) {
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

    /**
     * Checks if the music is running.
     *
     * @return the running status
     */
    public static boolean isRunning() {
        return running;
    }

    /**
     * Reverses the music icons.
     */
     public static void reverseIcons() {
      if (!running) {
         musicButtonSetTextGame("🔇");
         musicButtonSetTextMenu("🔇");
      } else {
          musicButtonSetTextGame("🔊");
          musicButtonSetTextMenu("🔊");
      }
     }

    /*
     * Changes the music based on the room.
     */
    public static void changRoomMusic(String room) {
        changeClip(roomToClipIndex.getOrDefault(room, 3));
    }
}
