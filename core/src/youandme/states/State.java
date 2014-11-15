package youandme.states;

import youandme.YouAndMe;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {

	protected GSM gsm;
	protected Vector3 mouse;
	protected OrthographicCamera camera;
	
	public State(GSM gsm) {
		this.gsm = gsm;
		this.mouse = new Vector3();
		this.camera = new OrthographicCamera();
		camera.setToOrtho(false, YouAndMe.WIDTH, YouAndMe.HEIGHT);
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
}
