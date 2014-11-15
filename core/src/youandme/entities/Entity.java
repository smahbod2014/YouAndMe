package youandme.entities;

import youandme.handlers.Animation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {

	protected float x;
	protected float y;
	protected Animation animation;
	
	protected float speed;
	
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
}
