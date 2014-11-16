package youandme.states;

import static youandme.YouAndMe.ADJUSTED_TILE_SIZE;
import static youandme.YouAndMe.HEIGHT;
import static youandme.YouAndMe.WIDTH;
import youandme.YouAndMe;
import youandme.entities.Player;
import youandme.entities.VictoryHeart;
import youandme.entities.Wall;
import youandme.handlers.C;
import youandme.handlers.LevelReader;
import youandme.ui.GameTile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class PlayState extends State {

	public static final int COUNTER_CLOCKWISE = 1;
	public static final int CLOCKWISE = -1;
	public static float fixPosX;
	public static float fixPosY;
	public static float originX;
	public static float originY;
	public static boolean rotating;
	public static boolean victory;
	public static float victoryDistance;
	private ShapeRenderer sr = new ShapeRenderer();
	private Array<Wall> walls;
	private Player player;
	private Player lover;
	private VictoryHeart vh;
	private float victoryMaxTimer = 2;
	
	public PlayState(GSM gsm) {
		super(gsm);
		LevelReader.load(2);
		player = LevelReader.getPlayer();
		lover = LevelReader.getLover();
		walls = new Array<Wall>();
		for (GameTile gt : LevelReader.getWalls()) {
			walls.add(new Wall(gt));
		}
		for (GameTile gt : LevelReader.getBorder()) {
			walls.add(new Wall(gt));
		}
		player.setWalls(walls);
		lover.setWalls(walls);
		fixPosX = WIDTH / 2 - LevelReader.getSize() * ADJUSTED_TILE_SIZE / 2;
		fixPosY = HEIGHT / 2 - LevelReader.getSize() * ADJUSTED_TILE_SIZE / 2;
	}
	
	private void checkCollisions() {
		player.checkCollisions();
		lover.checkCollisions();
	}
	
	private void setRotating(boolean b, int d) {
		rotating = b;
		for (Wall wall : walls) {
			wall.rotating = b;
			wall.direction = d;
		}
	}
	
	private void checkWinCondition(int dir) {
		if (dir == C.DIRECTION_LEFT) {
			if (player.getNormalX() - 1 == lover.getNormalX() + 1 && player.getNormalY() == lover.getNormalY()) {
				victory = true;
			} else if (player.getNormalX() - 1 == lover.getNormalX() && player.getNormalY() == lover.getNormalY()) {
				victory = true;
				player.distanceNeeded = ADJUSTED_TILE_SIZE / 2;
				lover.distanceNeeded = ADJUSTED_TILE_SIZE / 2;
			}
		} else if (dir == C.DIRECTION_DOWN) {
			if (player.getNormalX() == lover.getNormalX() && player.getNormalY() - 1 == lover.getNormalY() + 1) {
				victory = true;
			} else if (player.getNormalX() == lover.getNormalX() && player.getNormalY() - 1 == lover.getNormalY()) {
				victory = true;
				player.distanceNeeded = ADJUSTED_TILE_SIZE / 2;
				lover.distanceNeeded = ADJUSTED_TILE_SIZE / 2;
			}
		} else if (dir == C.DIRECTION_RIGHT) {
			if (player.getNormalX() + 1 == lover.getNormalX() - 1 && player.getNormalY() == lover.getNormalY()) {
				victory = true;
			} else if (player.getNormalX() + 1 == lover.getNormalX() && player.getNormalY() == lover.getNormalY()) {
				victory = true;
				player.distanceNeeded = ADJUSTED_TILE_SIZE / 2;
				lover.distanceNeeded = ADJUSTED_TILE_SIZE / 2;
			}
		} else if (dir == C.DIRECTION_UP) {
			if (player.getNormalX() == lover.getNormalX() && player.getNormalY() + 1 == lover.getNormalY() - 1) {
				victory = true;
			} else if (player.getNormalX() == lover.getNormalX() && player.getNormalY() + 1 == lover.getNormalY()) {
				victory = true;
				player.distanceNeeded = ADJUSTED_TILE_SIZE / 2;
				lover.distanceNeeded = ADJUSTED_TILE_SIZE / 2;
			}
		}
	}

	@Override
	public void handleInput() {
		if (!victory && !rotating && !player.inMotion && !lover.inMotion) {
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				player.setMotion(C.DIRECTION_LEFT, true);
				lover.setMotion(C.DIRECTION_RIGHT, true);
				checkWinCondition(C.DIRECTION_LEFT);
				checkCollisions();
			}
			
			else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				player.setMotion(C.DIRECTION_DOWN, true);
				lover.setMotion(C.DIRECTION_UP, true);
				checkWinCondition(C.DIRECTION_DOWN);
				checkCollisions();
			}
			
			else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				player.setMotion(C.DIRECTION_RIGHT, true);
				lover.setMotion(C.DIRECTION_LEFT, true);
				checkWinCondition(C.DIRECTION_RIGHT);
				checkCollisions();
			}
			
			else if (Gdx.input.isKeyPressed(Keys.UP)) {
				player.setMotion(C.DIRECTION_UP, true);
				lover.setMotion(C.DIRECTION_DOWN, true);
				checkWinCondition(C.DIRECTION_UP);
				checkCollisions();
			}
		}
		
		if (!rotating && Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			rotating = true;
			originX = MathUtils.round(LevelReader.getSize() / 2f * ADJUSTED_TILE_SIZE);
			originY = MathUtils.round(LevelReader.getSize() / 2f * ADJUSTED_TILE_SIZE);
			setRotating(true, COUNTER_CLOCKWISE);
		}
	}

	@Override
	public void update(float dt) {
		
		YouAndMe.bg.update(dt);
		player.update(dt);
		lover.update(dt);
		
		if (vh != null) {
			vh.update(dt);
		}
		
		for (Wall wall : walls) {
			if (wall.type == C.WALL_REGULAR) {
				wall.update(dt);
			}
		}
		
		if (!rotating) {
			int normalPlayerX = (int) MathUtils.round(player.x / ADJUSTED_TILE_SIZE);
			int normalPlayerY = (int) MathUtils.round(player.y / ADJUSTED_TILE_SIZE);
			int normalLoverX = (int) MathUtils.round(lover.x / ADJUSTED_TILE_SIZE);
			int normalLoverY = (int) MathUtils.round(lover.y / ADJUSTED_TILE_SIZE);
			boolean rotate = false;
			for (Wall wall : walls) {
				if (wall.type == C.WALL_REGULAR) {
					int normalWallX = (int) MathUtils.round(wall.x / ADJUSTED_TILE_SIZE);
					int normalWallY = (int) MathUtils.round(wall.y / ADJUSTED_TILE_SIZE);
					if (normalPlayerX == normalWallX && normalPlayerY == normalWallY) {
						player.setHurt(1);
						rotate = true;
					}
					
					if (normalLoverX == normalWallX && normalLoverY == normalWallY) {
						lover.setHurt(4);
						rotate = true;
					}
				}
			}
			
			setRotating(rotate, CLOCKWISE);
		}
		
		if (victory && !player.inMotion && !lover.inMotion) {
			vh = new VictoryHeart(player.x, player.y, victoryMaxTimer);
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		YouAndMe.bg.render(sb);
		
		
		sb.begin();
		for (GameTile gt : LevelReader.getBase()) {
			sb.draw(gt.tr, gt.x + fixPosX, gt.y + fixPosY, ADJUSTED_TILE_SIZE, ADJUSTED_TILE_SIZE);
		}
		sb.end();
		
		sr.begin(ShapeType.Line);
		sr.setColor(0, 0, 0, 1);
		float x = ADJUSTED_TILE_SIZE;
		float y = LevelReader.getSize() * ADJUSTED_TILE_SIZE;
		for (int i = 0; i < LevelReader.getSize() - 1; i++) {
			sr.line(x + fixPosX, y + fixPosY, x + fixPosX, y - LevelReader.getSize() * ADJUSTED_TILE_SIZE + fixPosY);
			x += ADJUSTED_TILE_SIZE;
		}
		x = 0;
		y = (LevelReader.getSize() - 1) * ADJUSTED_TILE_SIZE;
		for (int i = 0; i < LevelReader.getSize() - 1; i++) {
			sr.line(x + fixPosX, y + fixPosY, x + LevelReader.getSize() * ADJUSTED_TILE_SIZE + fixPosX, y + fixPosY);
			y -= ADJUSTED_TILE_SIZE;
		}
		sr.end();
		
		if (!victory) {
			player.render(sb);
			lover.render(sb);
		}
		
		if (vh != null) {
			vh.render(sb);
		}
		
		for (Wall wall : walls) {
			if (wall.type == C.WALL_BORDER) {
				wall.render(sb);
			}
		}
		for (Wall wall : walls) {
			if (wall.type == C.WALL_REGULAR) {
				wall.render(sb);
			}
		}
		
		YouAndMe.hud.render(sb);
	}
}
