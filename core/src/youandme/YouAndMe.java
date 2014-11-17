package youandme;

import youandme.handlers.Resources;
import youandme.states.GSM;
import youandme.states.MenuState;
import youandme.ui.Background;
import youandme.ui.FontFactory;
import youandme.ui.HUD;
import youandme.ui.HealthBar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
	public static Resources res;
	
	private SpriteBatch sb;
	
	public GSM gsm;
	
	@Override
	public void create () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		loadTextures();
		
		gsm = new GSM();
		
		ff = new FontFactory(res.getTexture("font"));
		bg = new Background(res.getTexture("bg"), 0, 0);
		hud = new HUD(gsm);
		hud.addHealthBar(new HealthBar(res.getTexture("healthbar1"), 16, -16));
		hud.addHealthBar(new HealthBar(res.getTexture("healthbar2"), WIDTH * .5625f + 12, HEIGHT - 2.25f *  ADJUSTED_TILE_SIZE));
		hud.correctHealthBar();
		
		sb = new SpriteBatch();
		
		
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.peek().handleInput();
		gsm.peek().update(Gdx.graphics.getDeltaTime());
		gsm.peek().render(sb);
	}
	
	private void loadTextures() {
		res = new Resources();
		res.loadTexture("font", "fonts/font1_16.png");
		res.loadTexture("bg", "youandme_clouds_200x150.png");
		res.loadTexture("hearts", "youandme_hearts_32.png");
		res.loadTexture("title", "youandme_title_192x32.png");
		res.loadPartitionedTexture("healthbar1", "youandme_healthbar_96x32.png", 96, 32, 0, 0);
		res.loadPartitionedTexture("healthbar2", "youandme_healthbar_96x32.png", 96, 32, 1, 0);
		res.loadPartitionedTexture("hearttransition", "youandme_hearts_32.png", 32, 32, 6, 4);
	}
}
