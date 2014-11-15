package youandme.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameTile {

	public TextureRegion tr;
	public float x;
	public float y;
	
	public GameTile(TextureRegion tr, float x, float y) {
		this.tr = tr;
		this.x = x;
		this.y = y;
	}
}
