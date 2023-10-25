import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer {
    private static int secondsRemaining = 30; // Initial countdown value
    private static Timer timer;
    private static JLabel countdownLabel;


	public void startCountdown()
	{
		JFrame frame = new JFrame("Text Countdown Example");
        countdownLabel = new JLabel("Countdown: " + secondsRemaining + " seconds");

        // Create a Timer that fires an event every 1000 milliseconds (1 second)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsRemaining--;
                if (secondsRemaining >= 0) {
                    countdownLabel.setText("Countdown: " + secondsRemaining + " seconds");
                } else {
                    // Countdown has reached zero
                    countdownLabel.setText("Time's up!");
                    timer.stop(); // Stop the timer when countdown reaches zero
                }
            }
        });

        // Start the timer
        timer.start();

		frame.setLocation(50, 50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(countdownLabel);
        frame.pack();
        frame.setVisible(true);
	}
