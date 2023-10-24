import java.io.File;
import java.io.IOException;
import java.lang.String;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class GameTimer 
{


	// static BufferedImage loadImage(String filename)
	// {
	// 	BufferedImage im = null;
	// 	try
	// 	{
	// 		im = ImageIO.read(new File(filename));
	// 	} catch (Exception e)
	// 	{
	// 		e.printStackTrace(System.err);
	// 		System.exit(1);
	// 	}
	// 	//System.out.println("Successfully loaded " + filename + " image."); Only here for error checking
	// 	return im;
	// }

	public static void main(String[] args)
	{
		Graphics g;
		BufferedImage[] images;
		int currentImage;
		int time = 30;
		// images = new BufferedImage[30];
		// images[30] = loadImage(time + ".tif");
		// int h = images[0].getHeight();
		// int w = images[0].getWidth();

		while(time >= 0)
		{
			// String filename = time + ".tif";
			// try{
			// 	if (images[time] == null)
			// 		images[time] = loadImage(filename);
			// 		//ImageIO.read(new File(filename));
			// }catch(Exception e) 
			// {
			// 	e.printStackTrace(System.err);
			// 	System.exit(1);
			// }
			// //Display countdown image for the current time
			System.out.println(time + " seconds left.");
			// System.out.println("\n" + filename);

			//Do the delay 
			try 
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{}

			//Decrement the time remaining in the timer.
			time--;
		}
	}
}






