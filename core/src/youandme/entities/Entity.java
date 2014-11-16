package youandme.entities;

import static youandme.YouAndMe.ADJUSTED_TILE_SIZE;
import youandme.handlers.Animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public abstract class Entity {

	public static final float pi = MathUtils.PI;
	
	public float x;
	public float y;
	protected Animation animation;
	
	protected float speed;
	
	public int getNormalX() {
		return (int) MathUtils.round(x / ADJUSTED_TILE_SIZE);
	}
	
	public int getNormalY() {
		return (int) MathUtils.round(y / ADJUSTED_TILE_SIZE);
	}
	
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
}
