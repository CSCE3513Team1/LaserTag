import java.util.ArrayList;

//This class is responsible for running the game and should only be accessed statically

public class GameAction {

	GameAction(){}
	
	static void RunGame()
	{
		//Gather the player information from the entry screen
		EntryScreen entryScreen = new EntryScreen();
		ArrayList<ArrayList<Player>> teams = entryScreen.waitForPlayerEntry();
		entryScreen.close();

		//Initialize playActionTable using the teams
		//The play action table will also handle the music and the timer
		PlayActionTable playActionTable = new PlayActionTable(teams.get(0), teams.get(1));
		playActionTable.display();


		//Configure UDP
		UDPSender udpSender = new UDPSender();
		UDPListener udpListener = new UDPListener();
		Thread udpListenerThread = new Thread(udpListener);

		//It's important to start the udplistener before dispatching the game start message
		udpListenerThread.start();
		playActionTable.updateDisplay();
		udpSender.SendMessage("202");

		//Main game loop
		while (!playActionTable.isGameOver())
		{
			//wait
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//Gets next event from queue
			if (udpListener.noEvent())
			{
				continue;
			}
			//System.out.println("Event");
			String event = udpListener.getNextEvent();
			//parse event
			String[] eventParts = event.trim().split(":");
			int attacker = Integer.parseInt(eventParts[0]);
			int defender = Integer.parseInt(eventParts[1]);
			if (defender == 53 || defender == 43)
			{
				playActionTable.playerHitBase(attacker);
			}
			else
			{
				playActionTable.playerHitPlayer(attacker, defender);
			}
		}
		//wait for 3 seconds. This is to prevent the screen from closing too fast
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		udpSender.SendMessage("221");
		//close everything and return
		playActionTable.close();
		udpListenerThread.interrupt();
		return;
	}
}
