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

	public float distanceNeeded = YouAndMe.ADJUSTED_TILE_SIZE;
	private Array<Wall> walls;
	private boolean[] motion;
	private int normalRow;
	private float distanceAccum;
	private float oldX;
	private float oldY;
	private float bobDelay = .5f;
	private float hurtAnimationDelay = .125f;
	private float hurtTimer;
	private float hurtMaxTimer = hurtAnimationDelay * 5;
	public boolean inMotion;
	
	public Player(float x, float y, int normalRow) {
		this.x = x;
		this.y = y;
		this.normalRow = normalRow;
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("youandme_hearts_32.png")));
		this.animation = new Animation(tr, bobDelay);
		animation.setMaxFrames(2);
		animation.setRow(normalRow);
		animation.start();
		this.speed = 200;
		motion = new boolean[4];
		oldX = x;
		oldY = y;
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
	
	public void setHurt(int row) {
		animation.setRow(row);
		animation.setDelay(.125f);
		hurtTimer = hurtMaxTimer;
		animation.setMaxFrames(5);
		animation.start();
	}
	
	public void setMotion(int dir, boolean b) {
		oldX = x;
		oldY = y;
		motion[dir] = b;
		if (b && !checkCollisions(dir)) {
			animation.resetFrame();
			animation.stop();
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
	
	public boolean checkCollisions(int dir) {
		int normalX = (int) MathUtils.round(x / ADJUSTED_TILE_SIZE);
		int normalY = (int) MathUtils.round(y / ADJUSTED_TILE_SIZE);
		
		if (dir == C.DIRECTION_LEFT) {	
			for (Wall wall : walls) {
				int normalWallX = (int) MathUtils.round(wall.x / ADJUSTED_TILE_SIZE);
				int normalWallY = (int) MathUtils.round(wall.y / ADJUSTED_TILE_SIZE);
				if (normalX - 1 == normalWallX && normalY == normalWallY) {
					clearMotion(false);
					return true;
				}
			}
		} else if (dir == C.DIRECTION_DOWN) {
			for (Wall wall : walls) {
				int normalWallX = (int) MathUtils.round(wall.x / ADJUSTED_TILE_SIZE);
				int normalWallY = (int) MathUtils.round(wall.y / ADJUSTED_TILE_SIZE);
				if (normalX == normalWallX && normalY - 1 == normalWallY) {
					clearMotion(false);
					return true;
				}
			}
		} else if (dir == C.DIRECTION_RIGHT) {
			for (Wall wall : walls) {
				int normalWallX = (int) MathUtils.round(wall.x / ADJUSTED_TILE_SIZE);
				int normalWallY = (int) MathUtils.round(wall.y / ADJUSTED_TILE_SIZE);
				if (normalX + 1 == normalWallX && normalY == normalWallY) {
					clearMotion(false);
					return true;
				}
			}
		} else if (dir == C.DIRECTION_UP) {
			for (Wall wall : walls) {
				int normalWallX = (int) MathUtils.round(wall.x / ADJUSTED_TILE_SIZE);
				int normalWallY = (int) MathUtils.round(wall.y / ADJUSTED_TILE_SIZE);
				if (normalX == normalWallX && normalY + 1 == normalWallY) {
					clearMotion(false);
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void clearMotion(boolean normalize) {
		//normalize position, AKA make sure we are set to an actual grid position
		if (normalize) {
			if (motion[C.DIRECTION_LEFT]) {
				x = oldX - distanceNeeded;
			}
			
			if (motion[C.DIRECTION_DOWN]) {
				y = oldY - distanceNeeded;
			}
			
			if (motion[C.DIRECTION_RIGHT]) {
				x = oldX + distanceNeeded;
			}
			
			if (motion[C.DIRECTION_UP]) {
				y = oldY + distanceNeeded;
			}
		}
		
		for (int i = 0; i < motion.length; i++) {
			motion[i] = false;
		}
		
		distanceAccum = 0;
		inMotion = false;
		printPosition();
		if (!animation.isRunning()) {
			animation.setMaxFrames(2);
			animation.start();
		}
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
		animation.update(dt);
		movePlayer(dt);
		if (hurtTimer > 0) {
			hurtTimer -= dt;
			if (hurtTimer <= 0) {
				hurtTimer = 0;
				animation.setRow(normalRow);
				animation.setDelay(bobDelay);
				animation.setMaxFrames(2);
			}
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		animation.render(sb, x + PlayState.fixPosX, y + PlayState.fixPosY);
	}
}
