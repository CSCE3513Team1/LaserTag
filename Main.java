import java.lang.String;

public class Main 
{
	public static void main(String[] args)
	{
		//Splash screen
		SplashScreen splashScreen = new SplashScreen();
		try {
            Thread.sleep(3000);
            splashScreen.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		

		//Run the game
		while(true){
			GameAction.RunGame();
		}
	}
}
