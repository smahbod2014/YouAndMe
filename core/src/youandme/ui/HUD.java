package youandme.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class HUD {

	private Array<HealthBar> healthBars;
	
	public HUD() {
		healthBars = new Array<HealthBar>();
	}
	
	public void setHealthBars(Array<HealthBar> healthBars) {
		this.healthBars = healthBars;
	}
	
	public void addHealthBar(HealthBar hb) {
		healthBars.add(hb);
	}
	
	public void render(SpriteBatch sb) {
		for (HealthBar hb : healthBars) {
			hb.render(sb);
		}
	}
}
