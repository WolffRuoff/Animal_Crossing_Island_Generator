import java.util.Random;
import java.awt.image.*;

public class HillGenerator {
	private TileMap mappy;
	private int hillW;
	private int hillH;
	private int[][] hillNumbers;
	
	private BufferedImage grass;
	private BufferedImage hillGrass;
	
	private String seed;
	private Boolean useRandomSeed;
	private int fillPercent;
	private int iterations = 5;
	
	public HillGenerator(TileMap m, int layer, int fp, String s){
		mappy = m;
		hillW = mappy.getWidth();
		hillH = mappy.getHeight()/2;
		hillNumbers = new int[hillW][hillH];
		seed = s;
		useRandomSeed = false;
		fillPercent = fp;
		if(layer==1) {
			grass = Utility.LoadImage(".\\Resources\\GroundGrass.png");
			hillGrass = Utility.LoadImage(".\\Resources\\HillGrass1.png");
		}
		else {
			grass = Utility.LoadImage(".\\Resources\\HillGrass1.png");
			hillGrass = Utility.LoadImage(".\\Resources\\HillGrass2.png");
		}
	}
	public HillGenerator(TileMap m, int layer, int fp){
		mappy = m;
		hillW = mappy.getWidth();
		hillH = mappy.getHeight()/2;
		hillNumbers = new int[hillW][hillH];
		useRandomSeed = true;
		fillPercent = fp;
		if(layer==1) {
			grass = Utility.LoadImage(".\\Resources\\GroundGrass.png");
			hillGrass = Utility.LoadImage(".\\Resources\\HillGrass1.png");
		}
		else {
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
		DrawToMap();
		return mappy;
	}
	
	private void RandomFill() {
		Random ran = new Random();
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
					else if(j+3 >= hillH) {
						hillNumbers[i][j] = 1;
					}
					else if(!Utility.compareBufferedImages(mappy.getTile(i, j+1), grass) && !Utility.compareBufferedImages(mappy.getTile(i, j+1), hillGrass)) {
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
					else if(!Utility.compareBufferedImages(mappy.getTile(i, j+1), grass) && !Utility.compareBufferedImages(mappy.getTile(i, j+1), hillGrass)) {
						hillNumbers[i][j] = 1;
					}
					else {
						int wallTiles = GetSurroundingCount(i,j);
				
						if(wallTiles > 4)
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
