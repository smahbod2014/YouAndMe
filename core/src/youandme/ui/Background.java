package youandme.ui;

import youandme.YouAndMe;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {

	private final float speed = 25;
	private TextureRegion tr;
	private float x;
	private float y;
	
	public Background(TextureRegion tr, float x, float y) {
		this.tr = tr;
		this.x = x;
		this.y = y;
	}
	
	public void update(float dt) {
		x += dt * speed;
		if (x > 0) {
			x = -YouAndMe.WIDTH;
		}
	}
	
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(tr, x, y, YouAndMe.WIDTH, YouAndMe.HEIGHT);
		if (x < 0) {
			sb.draw(tr, x + YouAndMe.WIDTH, y, YouAndMe.WIDTH, YouAndMe.HEIGHT);
		}
		sb.end();
	}
}
