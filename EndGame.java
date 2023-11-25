import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.IOException;
import java.lang.String;

public class EndGame
{
    
    EndGame()
    {
        JFrame frame = new JFrame("Swing Button Example");
        JPanel panel = new JPanel();

        JButton button = new JButton();
        button.setText("End Game");
        button.setBounds(500, 500, 200, 200);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Clicked!");
                frame.dispose();
                try{
                    GameAction gameAction1 = new GameAction();
                }catch(IOException n) {
                    n.printStackTrace();
                }
                

            }
        });

        panel.add(button);
        frame.add(panel);

        frame.setBounds(675, 150, 200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
