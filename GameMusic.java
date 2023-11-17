import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;

public class GameMusic {
    public static void main(String[] args) {
        final Player[] player = {null}; // Declare as final array

        // random number between 1-8
        int randomNumber = new Random().nextInt(8) + 1;

        // get the mp3 from github with a randomized track number
        String mp3Source = "https://github.com/CSCE3513Team1/LaserTag/raw/main/Images/Track0" + randomNumber + ".mp3";

        try {
            URLConnection urlConnection = new URL(mp3Source).openConnection();
            urlConnection.connect();
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            player[0] = new Player(bis);

            // Start a new thread for playback
            Thread playerThread = new Thread(() -> {
                try {
                    player[0].play();
                } catch (JavaLayerException e) {
                    System.out.println(e.getMessage());
                }
            });
            playerThread.start();

            // Sleep for 5 seconds
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Close resources and exit
            closeResources(player[0], bis);
            System.exit(0);

        } catch (IOException | JavaLayerException e) {
            System.out.println(e.getMessage());
        }
    }
    //close buffered input stream and Player
    private static void closeResources(Player player, BufferedInputStream bis) {
        try {
            if (player != null) {
                player.close();
            }
            if (bis != null) {
                bis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
