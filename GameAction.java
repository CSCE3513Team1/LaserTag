import java.io.IOException;
import java.util.ArrayList;

public class GameAction {

	PlayActionTable playActionTable;
	
	GameAction() throws IOException
	{
		//Creates a EntryScreen Object
		EntryScreen entryScreen = new EntryScreen();
		//Creates an ArrayList of Players for each team
		ArrayList<ArrayList<Player>> teams = entryScreen.waitForPlayerEntry();
		//Creats playActionTable Object and Puts entryScreen Team inside the playActionTable
		PlayActionTable playActionTable = new PlayActionTable(teams.get(0), teams.get(1));
		//Displays playActionTable
		playActionTable.display();
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
            System.out.println("This is attackers: " + attacker);
            System.out.println("This is defenders: " + defender);
            playActionTable.updateDisplay();
        } 
        */
	}
}
