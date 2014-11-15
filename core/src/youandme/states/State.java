package youandme.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

	protected GSM gsm;
	protected Vector3 mouse;
	
	public State(GSM gsm) {
		this.gsm = gsm;
		this.mouse = new Vector3();
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
}
