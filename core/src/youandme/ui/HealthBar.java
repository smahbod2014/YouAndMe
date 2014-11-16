package youandme.ui;

import youandme.YouAndMe;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar {

	private final int MAX_HITS = 15;
	private TextureRegion tr;
	private float scale;
	private float x;
	private float y;
	private int hits;
	
	public HealthBar(TextureRegion tr, float x, float y) {
		this.tr = tr;
		this.x = x;
		this.y = y;
		hits = 0;
		scale = YouAndMe.SCALE * 2.5f;
	}
	
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(tr, x, y, tr.getRegionWidth() * scale, tr.getRegionHeight() * scale);
		sb.end();
	}
}
