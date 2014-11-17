package youandme.ui;

import youandme.YouAndMe;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class HealthBar {

	private final int MAX_HITS = 5;
	private final float maxBarWidth;
	private TextureRegion tr;
	private ShapeRenderer sr;
	private float scale;
	private float x;
	private float y;
	private float barX;
	private float barY;
	private float barWidth;
	private float barHeight;
	public int hits;
	
	public HealthBar(TextureRegion tr, float x, float y) {
		this.tr = tr;
		this.x = x;
		this.y = y;
		sr = new ShapeRenderer();
		sr.setColor(189f/255, 0, 0, 1);
		hits = MAX_HITS;
		scale = YouAndMe.SCALE * 2.5f;
		barX = x + 12f * scale;
		barY = y + 14.5f * scale;
		maxBarWidth = barWidth = (tr.getRegionWidth() * 17/20) * scale;
		barHeight = (tr.getRegionHeight() * 1/9) * scale;
	}
	
	public void reset() {
		modify(MAX_HITS - hits);
		barX = x + 12f * scale;
	}
	
	public void modify(int change) {
		hits += change;
		if (hits < 0) {
			hits = 0;
		}
		
		barWidth = maxBarWidth * hits / MAX_HITS;
	}
	
	public void correctBarLocation() {
		this.barX -= 9f * scale;
	}
	
	public void adjust(int dir) {
		this.barX += dir * maxBarWidth * 1 / MAX_HITS;
	}
	
	public void render(SpriteBatch sb) {
		sr.begin(ShapeType.Filled);
		sr.rect(barX, barY, barWidth, barHeight);
		sr.end();
		
		sb.begin();
		sb.draw(tr, x, y, tr.getRegionWidth() * scale, tr.getRegionHeight() * scale);
		sb.end();
	}
}
