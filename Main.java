import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;

public class Main 
{
	public static void main(String[] args) throws IOException
	{
        //Connect to the server
		//udpBaseServer_2.baseServer();

		//Create a SplashScreen Object
		SplashScreen splashScreen = new SplashScreen();

		try {
            // Make photon image appear for 3 seconds before disappearing
            Thread.sleep(3000);
            splashScreen.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

		//Creates an EntryScreen Object
		EntryScreen entryScreen = new EntryScreen();
		ArrayList<ArrayList<Player>> teams = entryScreen.waitForPlayerEntry();
		PlayActionTable playActionTable = new PlayActionTable(teams.get(0), teams.get(1));
		playActionTable.display();
		
	}


}
