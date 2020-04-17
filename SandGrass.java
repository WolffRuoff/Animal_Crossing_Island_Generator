import java.awt.image.*;
import java.util.Random;

public class SandGrass {
	
	static PerlinNoise PerlinNoise = new PerlinNoise(0);
	private double perlin, random;
	private int size = 10;
	Random rand = new Random();
	
	public void south(int x1, int x2, int y1, int y2, TileMap mappy, BufferedImage tile) {
		for (int x = x1; x < (mappy.getWidth() - x2); x++) {
			random = rand.nextDouble();
			perlin = PerlinNoise.noise(x, random);
			
			if (perlin < -0.2) {
				size = size + 1;
			}
			
			else if (perlin > 0.2) {
				size = size - 1;
			}
			
			if (size > 13) {
				size = size - 1;
			}
			
			for (int y = y1; y < size + y2; y++) {
				mappy.setTile(x, y, tile);
			}
		}
		
		size = 10;		
	}
	
	public void east(int y1, int y2, int x1, int x2, TileMap mappy, BufferedImage tile) {
		for (int y = y1; y < (mappy.getHeight() - y2); y++) {
			random = rand.nextDouble();
			perlin = PerlinNoise.noise(y, random);
			
			if (perlin < -0.2) {
				size = size + 1;
			}
			
			else if (perlin > 0.2) {
				size = size - 1;
			}
			
			if (size > 13) {
				size = size - 1;
			}
			
			for (int x = x1; x < size + x2; x++) {
				mappy.setTile(x, y, tile);
			}
		}		
	}
	
	public void west(int y1, int y2, int x1, int x2, TileMap mappy, BufferedImage tile) {
		for (int y = y1; y < (mappy.getHeight() - y2); y++) {
			random = rand.nextDouble();
			perlin = PerlinNoise.noise(y, random);
			
			if (perlin < -0.2) {
				size = size + 1;
			}
						
			if (perlin > 0.2) {
				size = size - 1;
			}
			
			if (size > 13) {
				size = size - 1;
			}
			
			for (int x = size + x1; x < x2; x++) {
				mappy.setTile(x, y, tile);
			}
		}		
	}
	
	public void fill(int x1, int x2, int y1, int y2, TileMap mappy, BufferedImage tile) {
		for (int x = x2; x < x2; x++) {
			for (int y = y1; y < (mappy.getHeight() - y2); y++) {
				mappy.setTile(x, y, tile);
			}
		}
	}
}