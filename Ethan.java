import java.awt.image.*;

public class Ethan {

	static SandGrass SandGrass = new SandGrass();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TileMap mappy = new TileMap(112,112);
		BufferedImage water = Utility.LoadImage(".\\Resources\\water.png");
		for(int i = 0; i < mappy.getWidth(); i++) {
			for(int j = 0; j < mappy.getHeight(); j++) {
				mappy.setTile(i, j, water);
			}
		}
		
		// sand layer
		BufferedImage sand = Utility.LoadImage(".\\Resources\\Sand.png");
		SandGrass.south(13, 13, 90, 90, mappy, sand);
		SandGrass.east(12, 13, 90, 90, mappy, sand);
		SandGrass.west(12, 13, 3, 20, mappy, sand);
		SandGrass.fill(20, 90, 12, 20, mappy, sand);

		// ground grass layer
		BufferedImage groundGrass = Utility.LoadImage(".\\Resources\\GroundGrass.png");
		SandGrass.south(20, 20, 80, 80, mappy, groundGrass);
		SandGrass.east(12, 18, 70, 80, mappy, groundGrass);
		SandGrass.west(12, 18, 10, 40, mappy, groundGrass);
		SandGrass.fill(40, 70, 12, 30, mappy, groundGrass);

		// Hills Layer 1
		HillGenerator hilly = new HillGenerator(mappy, 1, 45);
		mappy = hilly.CreateHills();
		
		// Hills Layer 2
		HillGenerator hilly2 = new HillGenerator(mappy, 2, 50);
		mappy = hilly2.CreateHills();
		mappy.saveMap();
	
	}

}
