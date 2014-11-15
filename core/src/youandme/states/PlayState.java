package youandme.states;

import youandme.YouAndMe;
import youandme.entities.Player;
import youandme.handlers.LevelReader;
import youandme.ui.GameTile;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State {

	private Player player;
	private Player lover;
	
	public PlayState(GSM gsm) {
		super(gsm);
		LevelReader.load(1);
		player = LevelReader.getPlayer();
		lover = LevelReader.getLover();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		for (GameTile gt : LevelReader.getBase()) {
			sb.draw(gt.tr, gt.x, gt.y, YouAndMe.TILE_SIZE, YouAndMe.TILE_SIZE);
		}
		sb.end();
		
		player.render(sb);
		lover.render(sb);
		
		sb.begin();
		for (GameTile gt : LevelReader.getTop()) {
			sb.draw(gt.tr, gt.x, gt.y, YouAndMe.TILE_SIZE, YouAndMe.TILE_SIZE);
		}
		sb.end();
	}
}
