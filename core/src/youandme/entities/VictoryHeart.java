package youandme.entities;

import youandme.YouAndMe;
import youandme.handlers.Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class VictoryHeart extends Entity {

	public VictoryHeart(float x, float y, float duration) {
		this.x = x;
		this.y = y;
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("youandme_hearts_32.png")));
		animation = new Animation(tr, duration / ((int) (tr.getRegionWidth() / YouAndMe.TILE_SIZE)));
		animation.setRow(6);
		animation.start();
	}
	
	@Override
	public void update(float dt) {
		animation.update(dt);
		if (animation.getCurrentFrame() == 4) {
			animation.stop();
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		animation.render(sb, x, y);
	}
}
