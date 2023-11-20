import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class GameMusic {
    private Player player;

    public void playRandomTrack() {
        // random number between 1-8
        int randomNumber = (int) (Math.random() * 8) + 1;

        String mp3Source = "https://github.com/CSCE3513Team1/LaserTag/raw/main/Images/Track0" + randomNumber + ".mp3";

        try {
            URLConnection urlConnection = new URL(mp3Source).openConnection();
            urlConnection.connect();
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            player = new Player(bis);

            // Start a new thread for music
            Thread playerThread = new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    System.out.println(e.getMessage());
                }
            });
            playerThread.start();

            // Sleep for 5 seconds if needed)
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // close player
            closeResources();

        } catch (IOException | JavaLayerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void closeResources() {
        //says the try catch is unreachable and freezes for some reason
        //try {
            //if (player != null) 
            //{
                player.close();
           // }
        //} catch (IOException e) {
           // e.printStackTrace();
        //}
    }
}
