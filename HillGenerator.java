import java.util.Random;
import java.awt.image.*;
import java.util.*;
public class HillGenerator {
	private TileMap mappy;
	private int hillW;
	private int hillH;
	private int[][] hillNumbers;
	
	private BufferedImage ground;
	private BufferedImage grass;
	private BufferedImage hillGrass;
	
	private int layer;
	private Random ran = new Random();
	private String seed;
	private Boolean useRandomSeed;
	private int fillPercent;
	private int iterations = 4;
	
	public HillGenerator(TileMap m, int lay, int fp, String s){
		mappy = m;
		hillW = mappy.getWidth();
		hillH = mappy.getHeight()/2;
		hillNumbers = new int[hillW][hillH];
		seed = s;
		useRandomSeed = false;
		fillPercent = fp;
		layer = lay;
		if(layer==1) {
			grass = Utility.LoadImage(".\\Resources\\GroundGrass.png");
			hillGrass = Utility.LoadImage(".\\Resources\\HillGrass1.png");
		}
		else {
			grass = Utility.LoadImage(".\\Resources\\HillGrass1.png");
			hillGrass = Utility.LoadImage(".\\Resources\\HillGrass2.png");
		}
	}
	public HillGenerator(TileMap m, int lay, int fp){
		mappy = m;
		hillW = mappy.getWidth();
		hillH = mappy.getHeight()/2;
		hillNumbers = new int[hillW][hillH];
		useRandomSeed = true;
		fillPercent = fp;
		layer = lay;
		if(layer==1) {
			ground = Utility.LoadImage(".\\Resources\\GroundGrass.png");
			grass = Utility.LoadImage(".\\Resources\\GroundGrass.png");
			hillGrass = Utility.LoadImage(".\\Resources\\HillGrass1.png");
		}
		else {
			ground = Utility.LoadImage(".\\Resources\\GroundGrass.png");
			grass = Utility.LoadImage(".\\Resources\\HillGrass1.png");
			hillGrass = Utility.LoadImage(".\\Resources\\HillGrass2.png");
		}
	}
	
	public TileMap CreateHills() {
		RandomFill();
		DrawToMap();
		for(int i = 0; i < iterations; i++) {
			SmoothMap();
		}
		
		Cleanup();
		
		DrawToMap();
		return mappy;
	}
	
	private void Cleanup() {
		List<List<Coord>> grassRegions = GetRegions(1);
		int grassThreshholdSize = 30;
		
		for (List<Coord> grassRegion : grassRegions) {
			if(grassRegion.size() < grassThreshholdSize) {
				for(Coord tile : grassRegion) {
					hillNumbers[tile.getX()][tile.getY()] = 0;
				}
			}
		}
		
		List<List<Coord>> hillRegions = GetRegions(1);
		int hillThreshholdSize = 30;
		
		for (List<Coord> hillRegion : hillRegions) {
			if(hillRegion.size() < hillThreshholdSize) {
				for(Coord tile : hillRegion) {
					hillNumbers[tile.getX()][tile.getY()] = 1;
				}
			}
		}
	}
	
	public List<List<Coord>> GetRegions(int tileType){
		List<List<Coord>> regions = new ArrayList<List<Coord>>();
		int[][] flags = new int[hillW][hillH];
		
		for(int i = 0; i < hillW; i++) {
			for(int j = 0; j < hillH; j++) {
				if(flags[i][j] == 0 && hillNumbers[i][j] == tileType) {
					List<Coord> region = GetRegionTiles(i,j);
					regions.add(region);
					
					for(Coord tile : region) {
						flags[tile.getX()][tile.getY()] = 1;
					}
				}
			}
		}
		return regions;
	}
	
	private List<Coord> GetRegionTiles(int xStart, int yStart){
		List<Coord> tiles = new ArrayList<Coord> ();
		int[][] flags = new int[hillW][hillH];
		//Determine initial type
		int tileType = hillNumbers[xStart][yStart];
		
		Queue<Coord> q = new LinkedList<Coord>();
		flags [xStart][yStart] = 1;
		
		while(q.peek() != null) {
			Coord tile = q.remove();
			tiles.add(tile);
			
			for(int i = tile.getX() - 1; i <= tile.getX() + 1; i++) {
				for(int j = tile.getY() - 1; j <= tile.getY() + 1; j++) {
					if(i >= 0 && i < hillW && i >= 0 && i < hillH) {
						if(i == tile.getX() || j == tile.getY()) {
							if(flags[i][j] == 0 && hillNumbers[i][j] == tileType) {
								flags[i][j] = 1;
								q.add(new Coord(i,j));
							}
						}
					}
				}
			}
		}
		
		return tiles;
	}
	
	private void RandomFill() {
		if(!useRandomSeed) {
			ran.setSeed(Long.parseLong(seed));
		}
		/*
		 * 0 = hill
		 * 1 = grass
		 * 2 = nothing
		 */
		for(int i = 0; i < hillW; i++) {
			for(int j = 0; j < hillH; j++) {
				if(Utility.compareBufferedImages(mappy.getTile(i, j), grass)) { //Make sure the tiles lower
				//if(mappy.getTile(i, j).equals(grass)) { //Make sure the tiles lower
					if(!Utility.compareBufferedImages(mappy.getTile(i-1, j), grass) && !Utility.compareBufferedImages(mappy.getTile(i-1, j), hillGrass)) {
						hillNumbers[i][j] = 1;
					}
					else if(!Utility.compareBufferedImages(mappy.getTile(i+1, j), grass) && !Utility.compareBufferedImages(mappy.getTile(i+1, j), hillGrass)) {
						hillNumbers[i][j] = 1;
					}
					else if(!Utility.compareBufferedImages(mappy.getTile(i, j-1), grass) && !Utility.compareBufferedImages(mappy.getTile(i, j-1), hillGrass)) {
						hillNumbers[i][j] = 1;
					}
					else if(!Utility.compareBufferedImages(mappy.getTile(i, j-2), grass) && !Utility.compareBufferedImages(mappy.getTile(i, j-2), hillGrass) && !Utility.compareBufferedImages(mappy.getTile(i, j-2), ground)) {
						hillNumbers[i][j] = 0;
					}
					else if(!Utility.compareBufferedImages(mappy.getTile(i, j-3), grass) && !Utility.compareBufferedImages(mappy.getTile(i, j-3), hillGrass) && !Utility.compareBufferedImages(mappy.getTile(i, j-3), ground)) {
						hillNumbers[i][j] = 0;
					}
					else if(layer != 1 && !Utility.compareBufferedImages(mappy.getTile(i, j-4), grass) && !Utility.compareBufferedImages(mappy.getTile(i, j-4), hillGrass) && !Utility.compareBufferedImages(mappy.getTile(i, j-4), ground)) {
						hillNumbers[i][j] = 0;
					}
					else if(j >= hillH-(ran.nextInt(5)+3)) {
						hillNumbers[i][j] = 1;
					}
					else {
						if(ran.nextInt(100) < fillPercent) {
							hillNumbers[i][j] = 1;
						}
						else {
							hillNumbers[i][j] = 0;
							//System.out.println("Success");
						}
					}
				}
				else {
					hillNumbers[i][j] = 2; //Ignore these
					//System.out.println("failure");
				}
			}
		}
	}
	
	private void SmoothMap() {
		for(int i = 0; i < hillW; i++) {
			for(int j = 0; j < hillH; j++) {
				if(Utility.compareBufferedImages(mappy.getTile(i, j), grass) || Utility.compareBufferedImages(mappy.getTile(i, j), hillGrass)) { //Make sure the tiles lower
					//If the tile to the left isn't green make the current tile grass
					if(!Utility.compareBufferedImages(mappy.getTile(i-1, j), grass) && !Utility.compareBufferedImages(mappy.getTile(i-1, j), hillGrass)) {
						hillNumbers[i][j] = 1;
					}
					//If the tile to the right isn't green make the current tile grass
					else if(!Utility.compareBufferedImages(mappy.getTile(i+1, j), grass) && !Utility.compareBufferedImages(mappy.getTile(i+1, j), hillGrass)) {
						hillNumbers[i][j] = 1;
					}
					//If the tile above isn't green make the current tile grass
					else if(!Utility.compareBufferedImages(mappy.getTile(i, j-1), grass) && !Utility.compareBufferedImages(mappy.getTile(i, j-1), hillGrass)) {
						hillNumbers[i][j] = 1;
					}
					//For hill on hill prevent cliffs
					else if(!Utility.compareBufferedImages(mappy.getTile(i, j+1), grass) && !Utility.compareBufferedImages(mappy.getTile(i, j+1), hillGrass)) {
						hillNumbers[i][j] = 1;
					}
					else if(!Utility.compareBufferedImages(mappy.getTile(i, j+2), grass) && !Utility.compareBufferedImages(mappy.getTile(i, j+2), hillGrass)) {
						hillNumbers[i][j] = 1;
					}
					//If the tile 2 above isn't green perform smoothing leaning towards a hill
					else if(!Utility.compareBufferedImages(mappy.getTile(i, j-2), grass) && !Utility.compareBufferedImages(mappy.getTile(i, j-2), hillGrass) && !Utility.compareBufferedImages(mappy.getTile(i, j-2), ground)) {
						int wallTiles = GetSurroundingCount(i,j);
						
						if(wallTiles > 3)
							hillNumbers[i][j] = 0;
						else if(wallTiles < 3)
							hillNumbers[i][j] = 1;
					}
					//If j is within four of the bottom make it grass
					//else if(j >= hillH-4) {
					//	hillNumbers[i][j] = 1;
					//}
					//Otherwise perform smoothing
					else {
						int wallTiles = GetSurroundingCount(i,j);
				
						if(wallTiles >= 4)
							hillNumbers[i][j] = 0;
						else if(wallTiles < 4)
							hillNumbers[i][j] = 1;
					}
				}
			}
		}
	}
	
	private int GetSurroundingCount(int gridX, int gridY) {
		int count = 0;
		for(int nx = gridX - 1; nx <= gridX + 1; nx++) {
			for(int ny = gridY - 1; ny <= gridY + 1; ny++) {
				if(nx >= 0 && nx < hillW && ny >= 0 && ny < hillH) {
					if(nx != gridX || ny != gridY) {
						//System.out.println(nx);
						//System.out.println(ny);
						if(hillNumbers[nx][ny] == 0)
							count++;
					}
				}
				else { //Encourage more grass on edges
					count++;
				}
			}
		}
		return count;
	}
	
	private void DrawToMap() {
		for(int i = 0; i < hillW; i++) {
			for(int j = 0; j < hillH; j++) {
				if(hillNumbers[i][j] == 0) {
					mappy.setTile(i,j,hillGrass);
				}
				else if(hillNumbers[i][j] == 1) {
					mappy.setTile(i, j, grass);
				}
			}
		}
	}
}
