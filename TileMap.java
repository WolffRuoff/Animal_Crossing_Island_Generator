import java.awt.Image;
import java.awt.image.*;
import java.awt.Color;
//The 2d array making up our island
public class TileMap {
	private int width;
	private int height;
	private int imgW = 20;
	private int imgH = 20;
	private Image[][] map;
	
	public TileMap(int w, int h) {
		height = h;
		width = w;
		map = new Image[width][height];
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public void setTile(int x, int y, Image img) {
		map[x][y] = img;
	}
	public Image getTile(int x, int y) {
		return map[x][y];
	}
	
	public void saveMap() {
		int mapW = width * imgW;
		int mapH = height * imgH;
		
		BufferedImage island = new BufferedImage(mapW,mapH, BufferedImage.TYPE_INT_RGB);
		for(int i = 0; i < mapW; i = i + imgW) {
			for(int j = 0; j < mapH; j = j + imgH) {
				
			}
		}
		
		
		
	}
}
