package youandme.states;

import youandme.YouAndMe;
import youandme.handlers.C;
import youandme.transitions.HeartTransition;
import youandme.ui.TextButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class MenuState extends State {

	private Sprite title;
	private Array<TextButton> buttons;
	
	public MenuState(GSM gsm) {
		super(gsm);
		title = new Sprite(YouAndMe.res.getTexture("title"));
		title.setScale(YouAndMe.SCALE * 1.5f);
		title.setX(YouAndMe.WIDTH / 2 - title.getRegionWidth() / 2);
		title.setY(YouAndMe.HEIGHT * 3 / 4 - title.getHeight() / 2);
		buttons = new Array<TextButton>();
		buttons.add(new TextButton(YouAndMe.WIDTH/2, YouAndMe.HEIGHT/2, 2, "Play", C.BUTTON_PLAY));
		buttons.add(new TextButton(YouAndMe.WIDTH/2, YouAndMe.HEIGHT/2 - 50, 2, "Quit", C.BUTTON_QUIT));
	}

	@Override
	public void handleInput() {
		mouse.x = Gdx.input.getX();
		mouse.y = Gdx.input.getY();
		camera.unproject(mouse);
		
		for (TextButton tb : buttons) {
			if(tb.contains(mouse.x, mouse.y)) {
				tb.setColor(1, 0, 0, 1);
			} else {
				tb.setColor(1, 1, 1, 1);
			}
		}
		
		if(Gdx.input.justTouched()) {
			for (TextButton tb : buttons) {
				if(tb.contains(mouse.x, mouse.y)) {
					if (tb.id == C.BUTTON_PLAY) {
						gsm.set(new TransitionState(gsm, this, new PlayState(gsm, 6), new HeartTransition(YouAndMe.res.getTexture("hearttransition"))));
					} else if (tb.id == C.BUTTON_QUIT) {
						Gdx.app.exit();
					}
				}
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
		
		for (TextButton tb : buttons) {
			YouAndMe.ff.render(sb, tb.text, tb.x, tb.y, tb.scale, tb.alpha);
		}
	}
}
