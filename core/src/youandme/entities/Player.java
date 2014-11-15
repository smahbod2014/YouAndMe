package youandme.entities;

import youandme.handlers.Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Entity {

	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		//TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("INSERT ANIMATION SHEET HERE.png")));
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("rx face.jpg")));
		this.animation = new Animation(tr, .25f);
	}
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch sb) {
		animation.render(sb, x, y);
	}
}
