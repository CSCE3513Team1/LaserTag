import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;


public class GameTimer extends JPanel{
    private static int secondsRemaining = 30; // Initial countdown value
    private static Timer timer;
    private static JLabel countdownLabel;
    
    public GameTimer()
    {
    	
    	//constructor
        JLabel label = new JLabel("Countdown: " + secondsRemaining + " seconds");
     // Create a Timer that fires an event every 1000 milliseconds (1 second)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsRemaining--;
                if (secondsRemaining >= 0) {
                    label.setText("Countdown: " + secondsRemaining + " seconds");
                } else {
                    // Countdown has reached zero
                    label.setText("Time's up!");
                    timer.stop(); // Stop the timer when countdown reaches zero
                }
            }
        });

        // Start the timer
        timer.start();
        add(label);
    }

	public void startCountdown()
	{
		
        countdownLabel = new JLabel("Countdown: " + secondsRemaining + " seconds");
        
	}
