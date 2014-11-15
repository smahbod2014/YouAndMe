package youandme.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {

	protected GSM gsm;
	
	public State(GSM gsm) {
		this.gsm = gsm;
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
}
