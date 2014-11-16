package youandme.states;

import youandme.YouAndMe;
import youandme.ui.TextButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {

	//private Sprite[] title;
	private Sprite title;
	private TextButton playButton;
	
	public MenuState(GSM gsm) {
		super(gsm);
		//title = YouAndMe.ff.getFontArray(YouAndMe.TITLE);
		Texture tex = new Texture(Gdx.files.internal("youandme_title_192x32.png"));
		title = new Sprite(tex);
		title.setScale(YouAndMe.SCALE * 1.5f);
		title.setX(YouAndMe.WIDTH / 2 - title.getRegionWidth() / 2);
		title.setY(YouAndMe.HEIGHT * 3 / 4 - title.getHeight() / 2);
		playButton = new TextButton(YouAndMe.WIDTH/2, YouAndMe.HEIGHT/2, 
									YouAndMe.ff.getFontArray("Play"));
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		if(Gdx.input.isTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			camera.unproject(mouse);
			
			if(playButton.contains(mouse.x, mouse.y)) {
				gsm.set(new PlayState(gsm));
			}
		}
	}

	@Override
	public void update(float dt) {
		YouAndMe.bg.update(dt);
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		YouAndMe.bg.render(sb);
		sb.begin();
		title.draw(sb);
		sb.end();
		YouAndMe.ff.render(sb, playButton.text, playButton.x, playButton.y, 1);
	}
}
