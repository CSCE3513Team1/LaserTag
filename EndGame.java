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
	public static void main(String[] args) {
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
                EntryScreen entryScreen = new EntryScreen();
		        ArrayList<ArrayList<Player>> teams = entryScreen.waitForPlayerEntry();
                PlayActionTable playActionTable = new PlayActionTable(teams.get(0), teams.get(1));
		        playActionTable.display();

            }
        });

        panel.add(button);
        frame.add(panel);

        frame.setBounds(675, 150, 200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

//if timer is stopped, then create the button 
//Have the button wait until the timer is done, and create  a while loop in main that waits for the button to be pressed. 
//Most likely a copy of the original main code into this while loop
