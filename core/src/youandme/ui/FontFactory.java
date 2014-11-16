package youandme.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FontFactory {

	private final int FONT_SIZE = 16;
	private TextureRegion[][] sheet;
	private int cols;
	
	public FontFactory(TextureRegion region) {
		sheet = region.split(FONT_SIZE, FONT_SIZE);
		cols = sheet[0].length;
	}
	
	public Sprite[] getFontArray(String s) {
		Sprite[] images = new Sprite[s.length()];
		s = s.toUpperCase();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			Sprite spr = null;
			
			//c between A and Z
			if (c >= 65 && c <= 90) {
				c -= 65;
				spr = new Sprite(sheet[c / cols][c % cols]);
			} else if (c >= 48 && c <= 57) {
				c -= 48;
				c += 27;
				spr = new Sprite(sheet[c / cols][c % cols]);
			} else if (c == ' ') {
				spr = new Sprite(sheet[26 / cols][26 % cols]);
			} else if (c == '.') {
				spr = new Sprite(sheet[37 / cols][37 % cols]);
			} else if (c == '!') {
				spr = new Sprite(sheet[38 / cols][38 % cols]);
			} else if (c == ':') {
				spr = new Sprite(sheet[39 / cols][39 % cols]);
			} else if (c == '-') {
				spr = new Sprite(sheet[40 / cols][40 % cols]);
			} else if (c == '?' ){
				spr = new Sprite(sheet[41 / cols][41 % cols]);
			} else if (c == '&') {
				spr = new Sprite(sheet[42 / cols][42 % cols]);
			} else {
				spr = new Sprite(sheet[41 / cols][41 % cols]);
			}
			
			if (spr != null) {
				images[i] = spr;
			}
		}
		
		return images;
	}
	
	public float width(int length, float scale) {
		return FONT_SIZE * length * scale;
	}
	
	public void render(SpriteBatch sb, Sprite[] sprites, float x, float y, float scale) {
		float currx = 0;
		float offx = width(sprites.length, scale) / 2;
		sb.begin();
		for (Sprite spr : sprites) {
			spr.setX(x - offx + width(1, scale) / 4 + currx);
			spr.setY(y - width(1, scale) / 2);
			spr.setScale(scale, scale);
			spr.draw(sb);
			currx += width(1, scale);
		}
		sb.end();
	}
}