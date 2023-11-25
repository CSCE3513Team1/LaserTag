import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;


//NOTE: Because of the way this is set up, we can't have multiple timers running at once
//I'm not going to fix it since we won't need it but keep it in mind

public class GameTimer extends JPanel{
    private static int secondsRemaining; // Initial countdown value
    private static Timer timer;
    private static String prefix;
    private static String endDisplay;
    
    
    public GameTimer(int timerSeconds, String Prefix, String EndDisplay)
    {
        endDisplay = EndDisplay;
        prefix = Prefix;
        secondsRemaining = timerSeconds;
    	
    	//constructor
    	//adds in Jlabel that will be added to Jpanel
        JLabel label = new JLabel(CountdownLabel());
     // Create a Timer that fires an event every 1000 milliseconds (1 second)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSecondsRemaining(getSecondsRemaining() - 1);
                if (getSecondsRemaining() > 0) {
                    label.setText(CountdownLabel());
                } else {
                    // Countdown has reached zero
                    label.setText(endDisplay);
                    setSecondsRemaining(0);
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

    private String CountdownLabel() {
        //if over a minute, display minutes and seconds
        if (getSecondsRemaining() > 60) {
            return prefix + getSecondsRemaining() / 60 + " minutes " + getSecondsRemaining() % 60 + " seconds";
        }
        return prefix + getSecondsRemaining() + " seconds";
    }

    //method to wait for timer to finish
    public void waitForTimer() {
        while (secondsRemaining > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isFinished() {
        return secondsRemaining == 0;
    }
    
}
