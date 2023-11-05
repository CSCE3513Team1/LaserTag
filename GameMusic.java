import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.util.Random;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class GameMusic {
    public static void main(String[] args) {
        Player player = null;

        //random number between 1-8
        Random random = new Random();
        int randomNumber = random.nextInt(8);
        randomNumber = randomNumber + 1;

        //get the mp3 from github with randomized track number
        String mp3Source = "https://github.com/CSCE3513Team1/LaserTag/raw/main/Images/Track0" + randomNumber + ".mp3";

        try {
            URLConnection urlConnection = new URL(mp3Source).openConnection();
            urlConnection.connect();
            BufferedInputStream bis = new BufferedInputStream(urlConnection.getInputStream());
            player = new Player(bis);
        } catch (IOException | JavaLayerException e) {
            System.out.println(e.getMessage());
        }

        if (player != null) {
            try {
                player.play();
            } catch (JavaLayerException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
