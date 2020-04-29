import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.awt.Graphics;
//The 2d array making up our island
public class TileMap {
	private int width;
	private int height;
	private int imgW = 20;
	private int imgH = 20;
	private BufferedImage[][] map;
	
	public TileMap(int w, int h) {
		height = h;
		width = w;
		map = new BufferedImage[width][height];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setTile(int x, int y, BufferedImage img) {
		map[x][y] = img;
	}
	
	public BufferedImage getTile(int x, int y) {
		return map[x][y];
	}
	
	public void saveMap() {
		int mapW = width * imgW;
		int mapH = height * imgH;
		int x = 0;
		int y = 0;
		BufferedImage island = new BufferedImage(mapW,mapH, BufferedImage.TYPE_INT_RGB);
		Graphics g = island.getGraphics();
		for(int i = 0; i < mapW; i = i + imgW) {
			for(int j = 0; j < mapH; j = j + imgH) {
				g.drawImage(map[x][y], i, j, null);
				//System.out.println(j);
				y++;
			}
			y = 0;
			x++;
		}
		Utility.SaveImage(island, "."+ File.separator + File.separator + "Images" + File.separator + File.separator + "Island.png");
	}
	public List<List<Coord>> GetRegions(BufferedImage tileType, int h){
		List<List<Coord>> regions = new ArrayList<List<Coord>>();
		int[][] flags = new int[width][h];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < h; j++) {
				if(flags[i][j] != 1 && Utility.compareBufferedImages(getTile(i,j), tileType)) {
					List<Coord> region = GetRegionTiles(i,j,h);
					regions.add(region);
					
					for(Coord tile : region) {
						flags[tile.getX()][tile.getY()] = 1;
					}
				}
			}
		}
		return regions;
	}
	
	public List<Coord> GetRegionTiles(int xStart, int yStart, int h){
		List<Coord> tiles = new ArrayList<Coord> ();
		int[][] flags = new int[width][h];
		//Determine initial type
		BufferedImage tileType = getTile(xStart, yStart);
		
		Queue<Coord> q = new LinkedList<Coord>();
		q.add(new Coord(xStart, yStart));
		flags [xStart][yStart] = 1;
		
		while(q.size() > 0) {
			Coord tile = q.remove();
			tiles.add(tile);
			
			for(int i = tile.getX() - 1; i <= tile.getX() + 1; i++) {
				for(int j = tile.getY() - 1; j <= tile.getY() + 1; j++) {
					if(i >= 0 && i < width && j >= 0 && j < h) {
						if(i == tile.getX() || j == tile.getY()) {
							if(flags[i][j] != 1 && Utility.compareBufferedImages(getTile(i,j), tileType)) {
								flags[i][j] = 1;
								q.add(new Coord(i,j));
							}
						}
					}
				}
			}
		}
		//System.out.print("Size: " +tiles.size());
		return tiles;
	}
}
	