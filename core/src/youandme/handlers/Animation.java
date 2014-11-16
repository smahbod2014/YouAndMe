package youandme.handlers;

import static youandme.YouAndMe.*;
import youandme.entities.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

	private TextureRegion[][] sheet;
	private float delayTimer;
	private float delay;
	private int row;
	private int current;
	private int maxFrames;
	private boolean running;
	
	public Animation(TextureRegion sheet, float delay) {
		this.sheet = sheet.split((int) TILE_SIZE, (int) TILE_SIZE);
		this.delay = delay;
		this.delayTimer = delay;
		this.maxFrames = this.sheet[0].length;
	}
	
	public void start() {
		running = true;
		delayTimer = delay;
	}
	
	public void stop() {
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void setDelay(float d) {
		this.delay = d;
	}
	
	public void setRow(int row) {
		this.row = row;
		resetFrame();
	}
	
	public int getCurrentFrame() {
		return current;
	}
	
	public void setFrame(int current) {
		this.current = current;
	}
	
	public void resetFrame() {
		setFrame(0);
	}
	
	public void setMaxFrames(int max) {
		this.maxFrames = max;
	}
	
	public void update(float dt) {
		if (running && delayTimer > 0) {
			delayTimer -= dt;
			if (delayTimer <= 0) {
				delayTimer = delay;
				current++;
				if (current == maxFrames) {
					current = 0;
				}
			}
		}
	}
	
	public void render(SpriteBatch sb, float x, float y) {
		sb.begin();
		sb.draw(sheet[row][current], x, y, ADJUSTED_TILE_SIZE, ADJUSTED_TILE_SIZE);
		sb.end();
	}
}
