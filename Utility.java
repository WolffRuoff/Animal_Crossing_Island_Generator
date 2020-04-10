/**
 * @author Leonard Maxim
 * 
 * Procedural Content Generatio @ DIS Copenhagen
 *
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
class Utility
{	
	 /**
	  * Writes image in folder with specified name. 
	  * Images will be saved as PNG
	  *
	  *   @param img - image to be created
	  *   @param url - path to the folder in which the image will be saved including name and file extension

	  * **/
	 static void SaveImage(BufferedImage img, String url)
	 {
		 File f = new File(url);
	     try {
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * Returns an a buffered image from path
	  * 
	  *   @param url - path to the folder where the image is located
	  *   (don't forget to add the file extension)
	  * **/
	 static BufferedImage LoadImage(String url)
	 {
		 BufferedImage img = null;
			
			try {
			   img = ImageIO.read(new File(url));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return img;
	 }
	 
	 /**
	  * Maps a number from one range to another
	  * @param value - value that has to be remapped
	  * @param istart - lower bound of first range
	  * @param istop - upper bound of second range
	  * @param ostart - lower bound of first range
	  * @param ostop - upper bound of second range
	  * **/
	 // conversion of scale
	 static public final double map(double value, double istart, double istop, double ostart, double ostop) 
	 {
		 return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	 }
	 
}