import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

public class SplashScreen extends JWindow {
    private BufferedImage newPhotonImage;

    public SplashScreen() {
        try {
            
            //Pulling images from class Github previous method
            //URL url = new URL("https://github.com/jstrother123/photon-main/blob/main/logo.jpg?raw=true");
            
            // Load the original image from our Github
            URL url = new URL("https://github.com/CSCE3513Team1/LaserTag/blob/main/Images/logo.jpg?raw=true");
            BufferedImage originalPhotonImage = ImageIO.read(url);

            // Desired width and height for the resized image
            int photonImageWidth = 1500;
            int photonImageHeight = 750;

            // Create a new BufferedImage
            newPhotonImage = new BufferedImage(photonImageWidth, photonImageHeight, BufferedImage.TYPE_INT_ARGB);

            // Draw the original image onto the new BufferedImage
            Graphics2D g = newPhotonImage.createGraphics();
            g.drawImage(originalPhotonImage, 0, 0, photonImageWidth, photonImageHeight, null);
            g.dispose();

            // Set JWindow size with new image width and height
            setSize(photonImageWidth, photonImageHeight);

            // Get current screen size
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            // Center the JWindow on the screen
            int x = (screenSize.width - getSize().width) / 2;
            int y = (screenSize.height - getSize().height) / 2;
            setLocation(x, y);

            // Make the JWindow visible
            setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Paint resized image onto JWindow
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(newPhotonImage, 0, 0, this);
    }

    public static void main(String[] args) {
        SplashScreen splash = new SplashScreen();
        try {
            // Make photon image appear for 3 seconds before disappearing
            Thread.sleep(3000);
            splash.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
