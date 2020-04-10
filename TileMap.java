import java.awt.Image;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics;
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
		int x = 0;
		int y = 0;
		BufferedImage island = new BufferedImage(mapW,mapH, BufferedImage.TYPE_INT_RGB);
		Graphics g = island.getGraphics();
		for(int i = 0; i < mapW; i = i + imgW) {
			for(int j = 0; j < mapH; j = j + imgH) {
				g.drawImage(map[x][y], i, j, null);
				x++;
			}
			y++;
		}
		File f = new File(".\\Images\\Island.png");
	     try {
			ImageIO.write(island, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}
