import java.io.File;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.List;
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
	
	private List<Coord> mouth1 = new ArrayList<Coord> ();
	private List<Coord> mouth2 = new ArrayList<Coord> ();
	
	public WaterDrawer(TileMap m) {
		mappy = m;
	}
	
	public TileMap DrawMouths() {
		int mouthMiddle1 = ran.nextInt(mappy.getWidth()/3) + mappy.getWidth()/3;
		DrawMouthS(mouthMiddle1, false);
		
		int mouth2 = ran.nextInt(3);
		if(mouth2==0)
			DrawMouthE();
		else if(mouth2==1)
			DrawMouthW();
		else {
			int mouthMiddle2 = ran.nextInt(mappy.getWidth()/3) + mappy.getWidth()/3;
			
			while(Math.abs(mouthMiddle1 - mouthMiddle2) < mappy.getWidth()/4) {
				mouthMiddle2 = ran.nextInt(mappy.getWidth()/3) + mappy.getWidth()/3;
			}
			DrawMouthS(mouthMiddle2, true);
		}
		DrawPond();
		return mappy;
	}
	private void DrawMouthS(int mouthMiddle, boolean secondMouth) {
		//mouthMiddle = ran.nextInt(mappy.getWidth()/3) + mappy.getWidth()/3;
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
		
		if(!secondMouth) {
			for(int i = x1-1; i <= x2 + 1; i++)
				mouth1.add(new Coord(i,y));
		}
		else {
			for(int i = x1-1; i <= x2 + 1; i++)
				mouth2.add(new Coord(i,y));		
		}
		
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
				
	}
	
	private void DrawMouthE() {
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
		
		for(int i = y1-1; i <= y2 + 1; i++)
			mouth1.add(new Coord(x,i));
		
		while(!Utility.compareBufferedImages(mappy.getTile(x, y1), water) || !Utility.compareBufferedImages(mappy.getTile(x, y2), water)) {
			while(x > 0) {
				mappy.setTile(x, y1, water);
				mappy.setTile(x, y2, water);
				x--;
			}
			//System.out.println("y1 = " + y1);
			//System.out.println("y2 = " + y2);
			//System.out.println("x = " + x);

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
	}
	
	private void DrawMouthW() {
		int mouthMiddle = ran.nextInt(mappy.getHeight()/3) + mappy.getHeight()/3;
		// |||X||||
		int startX = mappy.getWidth()-1;
		while(!Utility.compareBufferedImages(mappy.getTile(startX, mouthMiddle), sand)) {
			startX--;
			System.out.println(startX);
		}
		int x = startX - 3;
		int y1 = mouthMiddle;
		int y2 = mouthMiddle+1;
		int depth = 0;
		
		for(int i = y1-1; i <= y2 + 1; i++)
			mouth1.add(new Coord(x,i));
		
		while(!Utility.compareBufferedImages(mappy.getTile(x, y1), water) || !Utility.compareBufferedImages(mappy.getTile(x, y2), water)) {
			while(x < mappy.getWidth()) {
				mappy.setTile(x, y1, water);
				mappy.setTile(x, y2, water);
				x++;
			}
			//System.out.println("y1 = " + y1);
			//System.out.println("y2 = " + y2);
			//System.out.println("x = " + x);

			if(y2-y1 >= 4) { 
				y1--;
				y2++;
				depth++;
			}
			else {
				for(int i = startX - 3; i > startX - 11; i--) {
					mappy.setTile(i, y1, water);
					mappy.setTile(i, y2, water);
				}
				y1--;
				y2++;
			}
			x = startX - 3 + depth;
		}
	}
	
	private void DrawPond() {
		List<List<Coord>> hill2Regions = mappy.GetRegions(hillGrass, mappy.getHeight());
		int ranHill = ran.nextInt(hill2Regions.size());
		//System.out.println("Size" + hill2Regions.size());
		List<Coord> hill = hill2Regions.get(ranHill);
		
		int count=0;
		while(hill.size() < 55) {
			hill = hill2Regions.get(count);
			count++;
		}
		
		List<Coord> edgeTiles = new ArrayList<Coord>();
		for(Coord tile : hill) {
			for(int i = tile.getX()-6; i <= tile.getX()+7; i++) {
				for(int j = tile.getY()-7; j <= tile.getY()+7; j++) {
					if(i != tile.getX() && j != tile.getY()) {
						if(!Utility.compareBufferedImages(mappy.getTile(i, j), hillGrass)){
							edgeTiles.add(tile);
						}
					}
				}
			}
		}
		//System.out.println(edgeTiles.size());
		for(Coord tile : hill) {
			if(!edgeTiles.contains(tile)) {
				//System.out.println("Drawings");
				mappy.setTile(tile.getX(), tile.getY(), water);
			}
				
		}

	}
}
