package youandme.entities;

import static youandme.YouAndMe.*;
import youandme.handlers.Animation;
import youandme.states.PlayState;
import youandme.ui.GameTile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Wall extends Entity {

	private final float rotationTime = .5f;
	private final float rotateDistanceNeeded = pi/2;
	public final int type;
	public int direction;
	public int debugIndex;
	public boolean rotating;
	private float rotationAccum;
	
	public Wall(GameTile gt) {
		this.x = gt.x;
		this.y = gt.y;
		this.animation = new Animation(gt.tr, 0);
		this.type = gt.property;
		this.debugIndex = gt.debugIndex;
	}
	
	private void rotate(float dt, float originX, float originY) {
		float adjX = x + ADJUSTED_TILE_SIZE / 2;
		float adjY = y + ADJUSTED_TILE_SIZE / 2;
		float radius = (float) Math.sqrt(Math.pow(originX - adjX, 2) + Math.pow(originY - adjY, 2));
		float angle = MathUtils.atan2(adjY - originY, adjX - originX);
		if (angle < 0)
			angle += 2*pi;
		float nextAngle = direction * rotateDistanceNeeded * dt / rotationTime;
		this.x = originX + radius * MathUtils.cos(angle + nextAngle) - ADJUSTED_TILE_SIZE / 2;
		this.y = originY + radius * MathUtils.sin(angle + nextAngle) - ADJUSTED_TILE_SIZE / 2;
		rotationAccum += (float) Math.abs(nextAngle);
		if (rotationAccum >= rotateDistanceNeeded) {
			adjX = x + ADJUSTED_TILE_SIZE / 2;
			adjY = y + ADJUSTED_TILE_SIZE / 2;
			x = (float) Math.floor(adjX / ADJUSTED_TILE_SIZE) * ADJUSTED_TILE_SIZE;
			y = (float) Math.floor(adjY / ADJUSTED_TILE_SIZE) * ADJUSTED_TILE_SIZE;
			rotationAccum = 0;
			rotating = false;
			PlayState.rotating = false;
		}
	}
	
	@Override
	public void update(float dt) {
		if (rotating) {
			rotate(dt, PlayState.originX, PlayState.originY);
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		animation.render(sb, x + PlayState.fixPosX, y + PlayState.fixPosY);
	}
}
