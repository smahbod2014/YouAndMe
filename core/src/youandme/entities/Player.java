package youandme.entities;

import static youandme.YouAndMe.*;
import youandme.YouAndMe;
import youandme.handlers.Animation;
import youandme.handlers.C;
import youandme.states.PlayState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Player extends Entity {

	private final float distanceNeeded = YouAndMe.ADJUSTED_TILE_SIZE;
	private Array<Wall> walls;
	private boolean[] motion;
	private float distanceAccum;
	private float oldX;
	private float oldY;
	public boolean inMotion;
	
	public Player(float x, float y) {
		this.x = x;
		this.y = y;
		//TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("INSERT ANIMATION SHEET HERE.png")));
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("testplayer.png")));
		this.animation = new Animation(tr, .25f);
		this.speed = 200;
		motion = new boolean[4];
		oldX = x;
		oldY = y;
	}
	
	public void setAnimation(int row) {
		animation.setRow(row);
	}
	
	public void playAnimation() {
		animation.start();
	}
	
	public void stopAnimation() {
		animation.stop();
	}
	
	public void setWalls(Array<Wall> walls) {
		this.walls = walls;
	}
	
	public void setMotion(int dir, boolean b) {
		oldX = x;
		oldY = y;
		motion[dir] = b;
		if (b) {
			inMotion = true;
		}
	}
	
	private void movePlayer(float dt) {
		float amountToMove = dt * SCALE * speed;
		if (motion[C.DIRECTION_LEFT]) {
			x -= amountToMove;
			distanceAccum += amountToMove;
		}
		
		if (motion[C.DIRECTION_DOWN]) {
			y -= amountToMove;
			distanceAccum += amountToMove;
		}
		
		if (motion[C.DIRECTION_RIGHT]) {
			x += amountToMove;
			distanceAccum += amountToMove;
		}
		
		if (motion[C.DIRECTION_UP]) {
			y += amountToMove;
			distanceAccum += amountToMove;
		}
		
		if (distanceAccum >= distanceNeeded) {
			clearMotion(true);
		}
	}
	
	public void checkCollisions() {
		int normalX = (int) MathUtils.round(x / ADJUSTED_TILE_SIZE);
		int normalY = (int) MathUtils.round(y / ADJUSTED_TILE_SIZE);
		
		if (motion[C.DIRECTION_LEFT]) {
			
			for (Wall wall : walls) {
				int normalWallX = (int) MathUtils.round(wall.x / ADJUSTED_TILE_SIZE);
				int normalWallY = (int) MathUtils.round(wall.y / ADJUSTED_TILE_SIZE);
				if (normalX - 1 == normalWallX && normalY == normalWallY) {
					clearMotion(false);
					return;
				}
			}
		}
		
		if (motion[C.DIRECTION_DOWN]) {
			for (Wall wall : walls) {
				int normalWallX = (int) MathUtils.round(wall.x / ADJUSTED_TILE_SIZE);
				int normalWallY = (int) MathUtils.round(wall.y / ADJUSTED_TILE_SIZE);
				if (normalX == normalWallX && normalY - 1 == normalWallY) {
					clearMotion(false);
					return;
				}
			}
		}
		
		if (motion[C.DIRECTION_RIGHT]) {
			for (Wall wall : walls) {
				int normalWallX = (int) MathUtils.round(wall.x / ADJUSTED_TILE_SIZE);
				int normalWallY = (int) MathUtils.round(wall.y / ADJUSTED_TILE_SIZE);
				if (normalX + 1 == normalWallX && normalY == normalWallY) {
					clearMotion(false);
					return;
				}
			}
		}
		
		if (motion[C.DIRECTION_UP]) {
			for (Wall wall : walls) {
				int normalWallX = (int) MathUtils.round(wall.x / ADJUSTED_TILE_SIZE);
				int normalWallY = (int) MathUtils.round(wall.y / ADJUSTED_TILE_SIZE);
				if (normalX == normalWallX && normalY + 1 == normalWallY) {
					clearMotion(false);
					return;
				}
			}
		}
	}
	
	private void clearMotion(boolean normalize) {
		//normalize position, AKA make sure we are set to an actual grid position
		if (normalize) {
			if (motion[C.DIRECTION_LEFT]) {
				x = oldX - ADJUSTED_TILE_SIZE;
			}
			
			if (motion[C.DIRECTION_DOWN]) {
				y = oldY - ADJUSTED_TILE_SIZE;
			}
			
			if (motion[C.DIRECTION_RIGHT]) {
				x = oldX + ADJUSTED_TILE_SIZE;
			}
			
			if (motion[C.DIRECTION_UP]) {
				y = oldY + ADJUSTED_TILE_SIZE;
			}
		}
		
		for (int i = 0; i < motion.length; i++) {
			motion[i] = false;
		}
		
		distanceAccum = 0;
		inMotion = false;
	}
	
	public void printWalls() {
		for (Wall wall : walls) {
			int normalX = (int) MathUtils.round(wall.x / ADJUSTED_TILE_SIZE);
			int normalY = (int) MathUtils.round(wall.y / ADJUSTED_TILE_SIZE);
			System.out.println("(" + normalX + ", " + normalY + ")");
		}
	}
	
	public void printPosition() {
		int normalX = (int) MathUtils.round(x / ADJUSTED_TILE_SIZE);
		int normalY = (int) MathUtils.round(y / ADJUSTED_TILE_SIZE);
		System.out.println("(" + normalX + ", " + normalY + ")");
	}
	
	@Override
	public void update(float dt) {
		movePlayer(dt);
	}

	@Override
	public void render(SpriteBatch sb) {
		animation.render(sb, x + PlayState.fixPosX, y + PlayState.fixPosY);
	}
}
