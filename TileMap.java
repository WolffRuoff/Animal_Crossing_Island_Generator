import java.awt.image.*;
import java.awt.Color;
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
				System.out.println(j);
				y++;
			}
			y = 0;
			x++;
		}
		Utility.SaveImage(island, ".\\Images\\Island.png");
	}
		
}
