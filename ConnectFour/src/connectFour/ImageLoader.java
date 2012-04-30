/***
 * Connect Four
 * Class - Sprite 
 * by Justin Dufresne
 */
package connectFour;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public abstract class ImageLoader
{
	// This class is only used for this static method, which streamlines the process of loading images.
	public static BufferedImage loadImage(String img)
	{
		URL imageURL = Game.class.getResource(img);
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(imageURL);
		} catch(IOException e) 
		{
			e.printStackTrace();
			System.out.println("Image not found at: " + img);
			image = null;
		}
		return image;
	}
}