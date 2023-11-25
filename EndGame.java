import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndGame
{
    private boolean buttonClicked = false;
    JFrame frame;
    JPanel panel;
    
    EndGame()
    {
        frame = new JFrame("Swing Button Example");
        panel = new JPanel();

        JButton button = new JButton();
        button.setText("Play Again");
        button.setBounds(500, 500, 200, 200);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClicked = true;
            }
        });

        panel.add(button);
        frame.add(panel);

        frame.setBounds(675, 150, 200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void waitForButtonPress()
    {
        while (!buttonClicked)
        {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        frame.dispose();
    }
}
