import java.awt.image.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Fuck This!!!!!");
		
		TileMap mappy = new TileMap(112,112);
		
		// water layer
		BufferedImage water = Utility.LoadImage(".\\Resources\\water.png");
		for(int i = 0; i < mappy.getWidth(); i++) {
			for (int j = 0; j < mappy.getHeight(); j++) {
				mappy.setTile(i, j, water);
			}
		}
		mappy.saveMap();
		
		// sand layer
		double sandWidth, sandHeight;
		BufferedImage sand = Utility.LoadImage(".\\Resources\\Sand.png");
		for (int i = 3; i < (mappy.getWidth() - 3); i++) {
			for (int j = 3; j < (mappy.getHeight() - 12); j++) {
				mappy.setTile(i, j, sand);
			}
		}
		mappy.saveMap();
		
		// ground grass layer
	}
}
