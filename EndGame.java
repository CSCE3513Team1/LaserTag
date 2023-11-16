import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGame 
{
	public static void main(String[] args) {
        JFrame frame = new JFrame("Swing Button Example");
        JPanel panel = new JPanel();

        JButton button = new JButton();
        button.setText("Click Me");
        button.setBounds(500, 500, 200, 200);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Clicked!");
            }
        });

        panel.add(button);
        frame.add(panel);

        frame.setBounds(675, 150, 200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

