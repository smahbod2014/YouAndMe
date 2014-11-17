package youandme.transitions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Transition {

	public int phase;
	
	public Transition() {}
	
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
}
