import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class ImageTimer extends JWindow {
    private BufferedImage[] countdownImages;
    private int currentImageIndex;

    public ImageTimer() {
        try {
            countdownImages = new BufferedImage[31]; 

            for (int i = 30; i >= 0; i--) {
                URL url = new URL("https://github.com/CSCE3513Team1/LaserTag/blob/main/Images/" + i + ".tif?raw=true"); //iterates through 30 down to 0
                countdownImages[30 - i] = ImageIO.read(url);
            }

            setSize(countdownImages[0].getWidth(), countdownImages[0].getHeight());
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - getSize().width) / 2;
            int y = (screenSize.height - getSize().height - 300);
            setLocation(x, y);

            currentImageIndex = 0;
            setVisible(true);

            setAlwaysOnTop(true);

            // Create a Timer to update the countdown
            Timer timer = new Timer(1000, e -> {
                if (currentImageIndex < countdownImages.length - 1) {
                    currentImageIndex++;
                    repaint(); // Redraw the window with the updated image
                } else {
                    ((Timer) e.getSource()).stop(); // Stop the timer
                    dispose();
                }
            });

            timer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(countdownImages[currentImageIndex], 0, 0, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageTimer timer = new ImageTimer();
        });
    }
}
