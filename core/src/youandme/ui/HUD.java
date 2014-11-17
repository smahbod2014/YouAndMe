package youandme.ui;

import youandme.YouAndMe;
import youandme.handlers.C;
import youandme.handlers.LevelReader;
import youandme.states.DefeatState;
import youandme.states.GSM;
import youandme.states.MenuState;
import youandme.states.PlayState;
import youandme.states.State;
import youandme.states.VictoryState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class HUD {

	private Array<HealthBar> healthBars;
	private Array<TextButton> instructions;
	private Array<TextButton> options;
	private TextButton levelIndicator;
	private Vector3 mouse;
	private GSM gsm;
	
	public HUD(GSM gsm) {
		this.gsm = gsm;
		healthBars = new Array<HealthBar>();
		instructions = new Array<TextButton>();
		options = new Array<TextButton>();
		levelIndicator = new TextButton(100, YouAndMe.HEIGHT - 16, 1.5f, "Level: ", C.UNDEFINED);
		mouse = new Vector3();
		float scale = 1f;
		this.instructions.add(new TextButton(470, 50, scale, "z:rotate left", C.UNDEFINED));
		this.instructions.add(new TextButton(470, 20, scale, "x:rotate right", C.UNDEFINED));
		for (TextButton tb : this.instructions) {
			tb.setColor(1, 1, 1, 1);
		}
		
		scale = 1.25f;
		options.add(new TextButton(700, 25, scale, "Main Menu", C.BUTTON_MAIN_MENU));
		options.add(new TextButton(700, 55, scale, "Retry", C.BUTTON_RETRY));
		options.add(new TextButton(700, 85, scale, "Undo", C.BUTTON_UNDO));
	}
	
	public void setHealthBars(Array<HealthBar> healthBars) {
		this.healthBars = healthBars;
	}
	
	public void addHealthBar(HealthBar hb) {
		healthBars.add(hb);
	}
	
	public void correctHealthBar() {
		healthBars.get(1).correctBarLocation();
	}
	
	public void modifyHealthBar(int i, int change) {
		healthBars.get(i).modify(change);
		if (i == 1) {
			healthBars.get(1).adjust(change > 0 ? -1 : 1);
		}
	}
	
	public boolean isPlayerDead() {
		if (healthBars.get(0).hits == 0) {
			return true;
		}
		return false;
	}
	
	public boolean isLoverDead() {
		if (healthBars.get(1).hits == 0) {
			return true;
		}
		return false;
	}
	
	public boolean isGameOver() {
		for (HealthBar hb : healthBars) {
			if (hb.hits == 0) {
				return true;
			}
		}
		
		return false;
	}
	
	public void reset() {
		for (HealthBar hb : healthBars) {
			hb.reset();
		}
		
		healthBars.get(1).correctBarLocation();
		levelIndicator.setText("Level:" + LevelReader.level);
		clearSelections();
	}
	
	public void clearSelections() {
		for (TextButton tb : options) {
			tb.setColor(1, 1, 1, 1);
		}
	}
	
	public void handleInput() {
		mouse.x = Gdx.input.getX();
		mouse.y = Gdx.input.getY();
		State.camera.unproject(mouse);
		
		if (!(gsm.peek() instanceof VictoryState) && !(gsm.peek() instanceof DefeatState)) {
			for (TextButton tb : options) {
				if (tb.contains(mouse.x, mouse.y)) {
					tb.setColor(1, 0, 0, 1);
				} else {
					tb.setColor(1, 1, 1, 1);
				}
			}
			
			if (Gdx.input.justTouched()) {
				for (int i = 0; i < options.size; i++) {
					if (options.get(i).contains(mouse.x, mouse.y)) {
						if (options.get(i).id == C.BUTTON_MAIN_MENU) {
							gsm.set(new MenuState(gsm));
						} else if (options.get(i).id == C.BUTTON_RETRY) {
							gsm.set(new PlayState(gsm, LevelReader.level));
						} else if (options.get(i).id == C.BUTTON_UNDO) {
							//TODO undo logic
						}
					}
				}
			}
		}
	}
	
	public void render(SpriteBatch sb) {
		for (HealthBar hb : healthBars) {
			hb.render(sb);
		}
		
		for (TextButton tb : instructions) {
			YouAndMe.ff.render(sb, tb.text, tb.x, tb.y, tb.scale, tb.alpha);
		}
		
		for (TextButton tb : options) {
			YouAndMe.ff.render(sb, tb.text, tb.x, tb.y, tb.scale, tb.alpha);
		}
		
		YouAndMe.ff.render(sb, levelIndicator.text, levelIndicator.x, levelIndicator.y, levelIndicator.scale, levelIndicator.alpha);
	}
}
