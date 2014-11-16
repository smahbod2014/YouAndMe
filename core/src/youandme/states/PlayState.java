package youandme.states;

import static youandme.YouAndMe.ADJUSTED_TILE_SIZE;
import static youandme.YouAndMe.HEIGHT;
import static youandme.YouAndMe.WIDTH;
import youandme.entities.Player;
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
	private ShapeRenderer sr = new ShapeRenderer();
	private Array<Wall> walls;
	private Player player;
	private Player lover;
	public static boolean rotating;
	
	public PlayState(GSM gsm) {
		super(gsm);
		LevelReader.load(1);
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

	@Override
	public void handleInput() {
		if (!rotating && !player.inMotion && !lover.inMotion) {
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				player.setMotion(C.DIRECTION_LEFT, true);
				lover.setMotion(C.DIRECTION_RIGHT, true);
				checkCollisions();
			}
			
			else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				player.setMotion(C.DIRECTION_DOWN, true);
				lover.setMotion(C.DIRECTION_UP, true);
				checkCollisions();
			}
			
			else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				player.setMotion(C.DIRECTION_RIGHT, true);
				lover.setMotion(C.DIRECTION_LEFT, true);
				checkCollisions();
			}
			
			else if (Gdx.input.isKeyPressed(Keys.UP)) {
				player.setMotion(C.DIRECTION_UP, true);
				lover.setMotion(C.DIRECTION_DOWN, true);
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
		player.update(dt);
		lover.update(dt);
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
						player.setAnimation(0/*insert animation row here*/);
						player.playAnimation();	
						rotate = true;
					}
					
					if (normalLoverX == normalWallX && normalLoverY == normalWallY) {
						lover.setAnimation(0/*insert animation row here*/);
						lover.playAnimation();
						rotate = true;
					}
				}
			}
			
			setRotating(rotate, CLOCKWISE);
		}
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		for (GameTile gt : LevelReader.getBase()) {
			sb.draw(gt.tr, gt.x + fixPosX, gt.y + fixPosY, ADJUSTED_TILE_SIZE, ADJUSTED_TILE_SIZE);
		}
		sb.end();
		
		player.render(sb);
		lover.render(sb);
		for (Wall wall : walls) {
			wall.render(sb);
		}
		
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
	}
}
