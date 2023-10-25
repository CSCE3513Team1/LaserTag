import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;


public class GameTimer extends JPanel{
    private static int secondsRemaining = 3; // Initial countdown value
    private static Timer timer;
    
    
    public GameTimer()
    {
    	
    	//constructor
    	//adds in Jlabel that will be added to Jpanel
        JLabel label = new JLabel("Countdown: " + getSecondsRemaining() + " seconds");
     // Create a Timer that fires an event every 1000 milliseconds (1 second)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSecondsRemaining(getSecondsRemaining() - 1);
                if (getSecondsRemaining() >= 0) {
                    label.setText("Countdown: " + getSecondsRemaining() + " seconds");
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

    //getter for secondsRemaining
	public static int getSecondsRemaining() {
		return secondsRemaining;
	}
	
	//setter for secondsRemaining
	public static void setSecondsRemaining(int secondsRemaining) {
		GameTimer.secondsRemaining = secondsRemaining;
	}
    
}
