import java.awt.Image;
//The 2d array making up our island
public class TileMap {
	private int width;
	private int height;
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
	public void setWidth(int w) {
		width = w;
	}
	public void setHeight(int h) {
		height = h;
	}
	public void setTile(int x, int y, Image img) {
		map[x][y] = img;
	}
	public Image getTile(int x, int y) {
		return map[x][y];
	}
}
