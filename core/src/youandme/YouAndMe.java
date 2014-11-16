package youandme;

import youandme.states.GSM;
import youandme.states.MenuState;
import youandme.ui.Background;
import youandme.ui.FontFactory;
import youandme.ui.HUD;
import youandme.ui.HealthBar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class YouAndMe extends ApplicationAdapter {
	
	public static float WIDTH = 800;
	public static float HEIGHT = 600;
	public static final float SCALE = WIDTH / HEIGHT;
	public static final float TILE_SIZE = 32;
	public static final float ADJUSTED_TILE_SIZE = SCALE * TILE_SIZE;
	public static final String TITLE = "You & Me";
	
	public static FontFactory ff;
	public static Background bg;
	public static HUD hud;
	
	private SpriteBatch sb;
	
	public GSM gsm;
	
	@Override
	public void create () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		ff = new FontFactory(new TextureRegion(new Texture(Gdx.files.internal("fonts/testfont.png"))));
		bg = new Background(new TextureRegion(new Texture(Gdx.files.internal("youandme_clouds_200x150.png"))), 0, 0);
		hud = new HUD();
		TextureRegion healthBars = new TextureRegion(new Texture(Gdx.files.internal("youandme_healthbar_64x32.png")));
		TextureRegion[][] sheet = healthBars.split(64, 32);
		hud.addHealthBar(new HealthBar(sheet[0][0], 75, 50));
		hud.addHealthBar(new HealthBar(sheet[1][0], 550, 450));
		
		
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
