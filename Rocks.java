import java.awt.image.*;
import java.util.Random;

public class Rocks {
	
	Random rand = new Random();
	int random;
	
	public void rock(TileMap mappy, BufferedImage tile) {
		random = rand.nextInt(10);
		
		
		borderRock(15, mappy, tile);
		borderRock(95, mappy, tile);
		topRock(mappy, tile);
	}
	
	public void topRock(TileMap mappy, BufferedImage tile) {
		int rockSize, chance;
		for (int x = 9; x < mappy.getWidth() - 15; x++) {
			chance = rand.nextInt(5);
			if (chance != 0) {
				rockSize = rand.nextInt(10) + 6;
				for (int width = x; width < x + rockSize; width++) {
					for (int height = 11; height > rockSize; height--) {
						mappy.setTile(width, height, tile);
					}
				}
			}
		}
	}		
	
	public void borderRock(int start, TileMap mappy, BufferedImage tile) {
		int rockSize, placement, chance;
		for (int y = 15; y < mappy.getHeight() - 20; y++) {
			chance = rand.nextInt(16);
			if (chance == 0) { 
				rockSize = rand.nextInt(4) + 3;
				placement = rand.nextInt(7) - 5;
				for (int height = y; height < y + rockSize; height++) {
					for (int width = start + placement; width < start + placement + rockSize; width++) {
						mappy.setTile(width, height, tile);
					}
				}
			}
		}
	}
}