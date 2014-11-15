package youandme;

import youandme.states.GSM;
import youandme.states.MenuState;
import youandme.ui.FontFactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class YouAndMe extends ApplicationAdapter {
	
	public static float WIDTH = 480;
	public static float HEIGHT = 480;
	public static final String TITLE = "You & Me";
	
	public static FontFactory ff;
	
	private SpriteBatch sb;
	
	public GSM gsm;
	
	@Override
	public void create () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		ff = new FontFactory(new TextureRegion(new Texture(Gdx.files.internal("fonts/testfont.png"))));
		
		sb = new SpriteBatch();
		
		gsm = new GSM();
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.peek().handleInput();
		gsm.peek().update(Gdx.graphics.getDeltaTime());
		gsm.peek().render(sb);
	}
}
