import java.io.File;
import java.awt.image.*;
import java.util.Random;

public class WaterDrawer {
	private TileMap mappy;
	
	private BufferedImage ground = Utility.LoadImage("."+ File.separator + File.separator + "Resources" + File.separator + File.separator + "GroundGrass.png");
	private BufferedImage grass = Utility.LoadImage("."+ File.separator + File.separator + "Resources" + File.separator + File.separator + "HillGrass1.png");
	private BufferedImage hillGrass = Utility.LoadImage("."+ File.separator + File.separator + "Resources" + File.separator + File.separator + "HillGrass2.png");
	private BufferedImage water = Utility.LoadImage("."+ File.separator + File.separator + "Resources" + File.separator + File.separator + "water.png");
	private BufferedImage sand = Utility.LoadImage("."+ File.separator + File.separator + "Resources" + File.separator + File.separator + "Sand.png");
	private BufferedImage rock = Utility.LoadImage("."+ File.separator + File.separator + "Resources" + File.separator + File.separator + "Rocks.png");
	
	private Random ran = new Random();
	
	
	public WaterDrawer(TileMap m) {
		mappy = m;
	}
	
	public TileMap DrawMouthS() {
		int mouthMiddle = ran.nextInt(mappy.getWidth()/3) + mappy.getWidth()/3;
		// |||X||||
		int startY = mappy.getHeight()-1;
		while(!Utility.compareBufferedImages(mappy.getTile(mouthMiddle, startY), sand)) {
			startY--;
			//System.out.println(startY);
		}
		int y = startY - 4;
		int x1 = mouthMiddle;
		int x2 = mouthMiddle+1;
		int depth = 0;
		
		while(!Utility.compareBufferedImages(mappy.getTile(x1, y), water) || !Utility.compareBufferedImages(mappy.getTile(x2, y), water)) {
			while(y < mappy.getHeight()) {
				mappy.setTile(x1, y, water);
				mappy.setTile(x2, y, water);
				y++;
			}
			//System.out.println("x1 = " + x1);
			//System.out.println("x2 = " + x2);
			//System.out.println("y = " + y);

			if(x2-x1 >= 4) {
				x1--;
				x2++;
				depth++;
			}
			else {
				for(int i = startY - 4; i > startY - 12; i--) {
					mappy.setTile(x1, i, water);
					mappy.setTile(x2, i, water);
				}
				x1--;
				x2++;
			}
			y = startY - 4 + depth;
		}
		
		return mappy;
		
		
	}
	
	public TileMap DrawMouthE() {
		int mouthMiddle = ran.nextInt(mappy.getHeight()/3) + mappy.getHeight()/3;
		// |||X||||
		int startX = 0;
		while(!Utility.compareBufferedImages(mappy.getTile(startX, mouthMiddle), sand)) {
			startX++;
			System.out.println(startX);
		}
		int x = startX + 3;
		int y1 = mouthMiddle;
		int y2 = mouthMiddle+1;
		int depth = 0;
		
		while(!Utility.compareBufferedImages(mappy.getTile(x, y1), water) || !Utility.compareBufferedImages(mappy.getTile(x, y2), water)) {
			while(x > 0) {
				mappy.setTile(x, y1, water);
				mappy.setTile(x, y2, water);
				x--;
			}
			System.out.println("y1 = " + y1);
			System.out.println("y2 = " + y2);
			System.out.println("x = " + x);

			if(y2-y1 >= 4) { 
				y1--;
				y2++;
				depth++;
			}
			else {
				for(int i = startX + 3; i < startX + 11; i++) {
					mappy.setTile(i, y1, water);
					mappy.setTile(i, y2, water);
				}
				y1--;
				y2++;
			}
			x = startX + 3 - depth;
		}
		
		return mappy;
		
		
	}
}
