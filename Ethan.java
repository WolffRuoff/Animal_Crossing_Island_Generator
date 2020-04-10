
public class Ethan {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TileMap mappy = new TileMap(112,112);
		
		for(int i = 0; i < mappy.getWidth(); i++) {
			for(int j = 0; j < mappy.getHeight(); j++) {
				mappy.setTile(i, j, water);
			}
		}
		
		mappy.saveMap();
	}

}
