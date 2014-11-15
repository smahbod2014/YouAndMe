package youandme.states;

import youandme.YouAndMe;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {

	private Sprite[] title;
	
	public MenuState(GSM gsm) {
		super(gsm);
		title = YouAndMe.ff.getFontArray(YouAndMe.TITLE);
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch sb) {
		YouAndMe.ff.render(sb, title, YouAndMe.WIDTH / 2, YouAndMe.HEIGHT / 2, 1);
	}
}
