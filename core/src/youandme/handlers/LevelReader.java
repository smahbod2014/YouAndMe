package youandme.handlers;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import static youandme.YouAndMe.*;
import youandme.entities.Player;
import youandme.ui.GameTile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

public class LevelReader {

	private static Array<GameTile> base;
	private static Array<GameTile> walls;
	private static Array<GameTile> border;
	private static Player player;
	private static Player lover;
	private static int size;
	private static int debugIndex = 1;
	public static int level;
	
	public static Array<GameTile> getBase() {
		return base;
	}
	
	public static Array<GameTile> getWalls() {
		return walls;
	}
	
	public static Array<GameTile> getBorder() {
		return border;
	}
	
	public static Player getPlayer() {
		return player;
	}
	
	public static Player getLover() {
		return lover;
	}
	
	public static int getSize() {
		return size;
	}
	
	public static void load(int level) {
		TiledMap map = null;
		try {
			map = new TmxMapLoader().load("levels/youandme_level" + level + ".tmx");
		} catch (Exception e) {
			e.printStackTrace();
			Gdx.app.exit();
		}
		
		debugIndex = 1;
		LevelReader.level = level;
		
		base = new Array<GameTile>();
		walls = new Array<GameTile>();
		border = new Array<GameTile>();
		
		//create the layers
		populateLayer(map, base, "base", -1);
		populateLayer(map, walls, "walls", C.WALL_REGULAR);
		populateLayer(map, border, "border", C.WALL_BORDER);
		
		//read in object info (Player, etc.)
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse("../core/assets/leveldata/youandme_level" + level + ".xml");
			document.normalize();
			
			Node root = document.getElementsByTagName("leveldata").item(0);
			Element rootElement = (Element) root;
			Node player = rootElement.getElementsByTagName("player").item(0);
			Element playerElement = (Element) player;
			createPlayer(playerElement);
			Node lover = rootElement.getElementsByTagName("lover").item(0);
			Element loverElement = (Element) lover;
			createLover(loverElement);
			Node size = rootElement.getElementsByTagName("map").item(0);
			Element sizeElement = (Element) size;
			LevelReader.size = Integer.parseInt(sizeElement.getAttribute("size"));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createPlayer(Element playerElement) {
		float x = Float.parseFloat(playerElement.getAttribute("x")) * ADJUSTED_TILE_SIZE;
		float y = Float.parseFloat(playerElement.getAttribute("y")) * ADJUSTED_TILE_SIZE;
		player = new Player(x, y, 0);
	}
	
	private static void createLover(Element playerElement) {
		float x = Float.parseFloat(playerElement.getAttribute("x")) * ADJUSTED_TILE_SIZE;
		float y = Float.parseFloat(playerElement.getAttribute("y")) * ADJUSTED_TILE_SIZE;
		lover = new Player(x, y, 3);
	}

	private static void populateLayer(TiledMap map, Array<GameTile> list, String name, int property) {
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(name);
		for (int row = 0; row < layer.getHeight(); row++) {
			for (int col = 0; col < layer.getWidth(); col++) {
				if (layer.getCell(col, row) != null) {
					float adjustedX = col * ADJUSTED_TILE_SIZE;
					float adjustedY = row * ADJUSTED_TILE_SIZE;
					list.add(new GameTile(layer.getCell(col, row).getTile().getTextureRegion(), adjustedX, adjustedY, property, debugIndex++));
				}
			}
		}
	}
}
