import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Main 
{
	public static void main(String[] args) throws IOException
	{
		//Make sure to run updBaseServer_2.java first before main program
		// to see udp working along side main program.
		
		//Create a SplashScreen Object
		SplashScreen splashScreen = new SplashScreen();

		try {
            // Make photon image appear for 3 seconds before disappearing
            Thread.sleep(3000);
            splashScreen.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		//Creates a GameAction Object
		GameAction gameAction = new GameAction();

		//Creates an EndGame Object that creates the button to send you back to the Player Entry Screen
		EndGame endGame = new EngGame();
	}
}
