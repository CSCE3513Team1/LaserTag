import java.io.IOException;
import java.util.ArrayList;

public class GameAction {

	PlayActionTable playActionTable;
	GameAction(){}
	
	static void RunGame() throws IOException
	{
		//Creates a EntryScreen Object
		EntryScreen entryScreen = new EntryScreen();
		//Creates an ArrayList of Players for each team
		ArrayList<ArrayList<Player>> teams = entryScreen.waitForPlayerEntry();
		//Creats playActionTable Object and Puts entryScreen Team inside the playActionTable
		PlayActionTable playActionTable = new PlayActionTable(teams.get(0), teams.get(1));
		//Displays playActionTable
		playActionTable.display();
		//send 202 to base
		UDPSender udpSender = new UDPSender();
		//System.out.println("udpSender created");
		UDPListener udpListener = new UDPListener();
		//UdpReceiver udpListener = new UdpReceiver(7500);
		//System.out.println("udpListener created");
		//dispatch udpListener
		Thread udpListenerThread = new Thread(udpListener);
		udpListenerThread.start();
		//System.out.println("udpListener running");
		playActionTable.updateDisplay();
		int displayInfoCounter = 0;
		udpSender.SendMessage("202");
		//System.out.println("202 sent");
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
				if (displayInfoCounter > 1000) {
					////System.out.println("No event");
					displayInfoCounter = 0;
				} else {
					displayInfoCounter++;
				}
				continue;
			}
			//System.out.println("Event");
			String event = udpListener.getNextEvent();
			//parse event
			String[] eventParts = event.trim().split(":");
			int attacker = Integer.parseInt(eventParts[0]);
			int defender = Integer.parseInt(eventParts[1]);
			if (defender == 0)
			{
				playActionTable.playerHitBase(attacker);
			}
			else
			{
				playActionTable.playerHitPlayer(attacker, defender);
			}
		}
		//wait for 3 seconds
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		udpSender.SendMessage("221");
		//close playActionTable
		//playActionTable.close();
		udpListenerThread.interrupt();
		return;
		/*
		//test playerHitPlayer and PlayerHitbase
		playActionTable.playerHitPlayer(1, 2);
		playActionTable.playerHitBase(2);
		playActionTable.updateDisplay();
		//wait, then have a random player hit a random player, seven times
		for(int i = 0; i < 7; i++)  {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //random number between 1-2 so 1,1, 1,2 , 2,1, 2,2
            int attacker = (int)(Math.random() * 2);
            int defender = (int)(Math.random() * 2);
            playActionTable.playerHitPlayer(attacker + 1, defender + 1);
            //System.out.println("This is attackers: " + attacker);
            //System.out.println("This is defenders: " + defender);
            playActionTable.updateDisplay();
        } 
        */
	}
}
