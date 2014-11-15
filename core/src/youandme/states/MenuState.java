package youandme.states;

import youandme.YouAndMe;
import youandme.ui.TextButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {

	private Sprite[] title;
	private TextButton playButton;
	
	public MenuState(GSM gsm) {
		super(gsm);
		title = YouAndMe.ff.getFontArray(YouAndMe.TITLE);
		playButton = new TextButton(YouAndMe.WIDTH/2, YouAndMe.HEIGHT/2, 
									YouAndMe.ff.getFontArray("Play"));
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(Gdx.input.isTouched()) {
			
		}
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch sb) {
		YouAndMe.ff.render(sb, title, YouAndMe.WIDTH / 2, YouAndMe.HEIGHT *3/4, 1);
		YouAndMe.ff.render(sb, playButton.text, playButton.x, playButton.y, 1);
	}
}
