package youandme.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameTile {

	public TextureRegion tr;
	public float x;
	public float y;
	public int property;
	public int debugIndex;
	
	public GameTile(TextureRegion tr, float x, float y, int property, int debugIndex) {
		this.tr = tr;
		this.x = x;
		this.y = y;
		this.property = property;
		this.debugIndex = debugIndex;
	}
}
