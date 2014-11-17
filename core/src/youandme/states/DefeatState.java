package youandme.states;

import youandme.YouAndMe;
import youandme.handlers.C;
import youandme.handlers.LevelReader;
import youandme.ui.TextButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class DefeatState extends State {

	private final float TIME_TO_LIGHT_UP = .35f;
	private final float TIME_SPENT_LIT_UP = .25f;
	private final float TIME_SPENT_OFF = 2f;
	private State previous;
	private Array<TextButton> buttons;
	private float timer;
	private float timerMax = 3f;
	private int[] phase;
	private float[] lightUpTimer;
	private float[] lightUpTimerMax;
	private float[] lightUpStallTimer;
	private float[] lightUpStallTimerMax;
	private Sprite[] textRef;
	
	public DefeatState(GSM gsm, State previous) {
		super(gsm);
		this.previous = previous;
		YouAndMe.hud.clearSelections();
		timer = 0;
		buttons = new Array<TextButton>();
		buttons.add(new TextButton(YouAndMe.WIDTH/2, YouAndMe.HEIGHT*3/4, 3, "Game over", -1, 0));
		buttons.add(new TextButton(YouAndMe.WIDTH/2, YouAndMe.HEIGHT/2, 2, "Retry", C.BUTTON_RETRY, 0));
		buttons.add(new TextButton(YouAndMe.WIDTH/2, YouAndMe.HEIGHT/2-50, 2, "Main menu", C.BUTTON_MAIN_MENU, 0));
		
		int size = 0;
		for (TextButton tb : buttons) {
			size += tb.text.length;
		}
		
		textRef = new Sprite[size];
		lightUpTimer = new float[size];
		lightUpTimerMax = new float[size];
		lightUpStallTimer = new float[size];
		lightUpStallTimerMax = new float[size];
		phase = new int[size];
		float delay = 0;
		for (int i = 0; i < size; i++) {
			lightUpTimer[i] = 0 - delay;
			lightUpTimerMax[i] = TIME_TO_LIGHT_UP;
			lightUpStallTimer[i] = 0;
			lightUpStallTimerMax[i] = 0; //will be overwritten later
			phase[i] = 0;
			delay += lightUpTimerMax[i] * .2f;
		}
		
		int i = 0;
		for (TextButton tb : buttons) {
			for (int j = 0; j < tb.text.length; j++) {
				textRef[i++] = tb.text[j];
			}
		}
	}

	@Override
	public void handleInput() {
		if (Gdx.input.justTouched()) {
			mouse.x = Gdx.input.getX();
			mouse.y = Gdx.input.getY();
			camera.unproject(mouse);
			for (TextButton tb : buttons) {
				if (tb.contains(mouse.x, mouse.y)) {
					if (tb.id == C.BUTTON_RETRY) {
						gsm.set(new PlayState(gsm, LevelReader.level));
					} else if (tb.id == C.BUTTON_MAIN_MENU) {
						gsm.set(new MenuState(gsm));
					}
				}
			}
		}
	}

	@Override
	public void update(float dt) {
		YouAndMe.bg.update(dt);
		previous.update(dt);
		if (timer < timerMax) {
			for (TextButton tb : buttons) {			
				tb.alpha = MathUtils.clamp(timer / timerMax, .01f, 1f);
			}
			timer += dt;
			if (timer >= timerMax) {
				timer = timerMax;
			}
		}
		
		if (timer == timerMax) {
			for (int i = 0; i < lightUpTimer.length; i++) {
				if (phase[i] == 0 && lightUpTimer[i] < lightUpTimerMax[i]) {
					lightUpTimer[i] += dt;
					if (lightUpTimer[i] >= lightUpTimerMax[i]) {
						lightUpTimer[i] = lightUpTimerMax[i];
						lightUpStallTimer[i] = 0;
						lightUpStallTimerMax[i] = TIME_SPENT_LIT_UP;
						phase[i] = 1; //next phase!
					}
					
					if (lightUpTimer[i] >= 0) {
						float amount = (lightUpTimerMax[i] - lightUpTimer[i]) / lightUpTimerMax[i];
						textRef[i].setColor(1, amount, amount, 1);
					}	
				}
				
				if (lightUpTimer[i] == lightUpTimerMax[i] && lightUpStallTimer[i] < lightUpStallTimerMax[i]) {
					lightUpStallTimer[i] += dt;
					if (lightUpStallTimer[i] >= lightUpStallTimerMax[i]) {
						lightUpStallTimer[i] = 0;
						lightUpTimer[i] = 0; //resets lightUpTimer so phase 1 can proceed
					}
				}
				
				if (phase[i] == 1 && lightUpTimer[i] < lightUpTimerMax[i]) {
					lightUpTimer[i] += dt;
					if (lightUpTimer[i] >= lightUpTimerMax[i]) {
						lightUpTimer[i] = lightUpTimerMax[i];
						lightUpStallTimer[i] = 0;
						lightUpStallTimerMax[i] = TIME_SPENT_OFF;
						phase[i] = 0; //long wait phase
					}
					
					float amount = lightUpTimer[i] / lightUpTimerMax[i];
					textRef[i].setColor(1, amount, amount, 1);
				}
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		YouAndMe.bg.render(sb);
		previous.render(sb);
		
		for (TextButton tb : buttons) {
			YouAndMe.ff.render(sb, tb.text, tb.x, tb.y, tb.scale, tb.alpha);
		}
	}
}
