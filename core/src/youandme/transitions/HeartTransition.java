package youandme.transitions;

import youandme.YouAndMe;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HeartTransition extends Transition {

	private float distanceNeededX = YouAndMe.WIDTH / 2 * 2f;
	private float distanceNeededY = YouAndMe.HEIGHT / 2 * 2f;
	private TextureRegion tr;
	private float x;
	private float y;
	private float timer;
	private float timerMax = 1;
	
	public HeartTransition(TextureRegion tr) {
		this.tr = tr;
		x = YouAndMe.WIDTH / 2;
		y = YouAndMe.HEIGHT / 2;
		timer = timerMax;
	}
	@Override
	public void update(float dt) {
		if (timer > 0) {
			timer -= dt;
			if (timer <= 0) {
				if (phase < 2) {
					timer = timerMax;
					phase++;
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		if (phase == 0) {
			float width = (timerMax - timer) / timerMax * distanceNeededX;
			float height = (timerMax - timer) / timerMax * distanceNeededY;
			sb.draw(tr, x - width / 2, y - height / 2, width, height);
		} else {
			float width = timer / timerMax * distanceNeededX;
			float height = timer / timerMax * distanceNeededY;
			sb.draw(tr, x - width / 2, y - height / 2, width, height);
		}
		
		sb.end();
	}
}
