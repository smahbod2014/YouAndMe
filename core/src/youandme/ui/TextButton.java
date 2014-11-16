package youandme.ui;

import youandme.YouAndMe;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class TextButton {

	public float x;
	public float y;
	public float scale;
	public int id;
	public Sprite[] text;
	
	public TextButton(float x, float y, float scale, Sprite[] text, int id) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.scale = scale;
		this.id = id;
	}
	
	public boolean contains(float x, float y){
		float width = YouAndMe.ff.width(text.length, scale);
		float height = YouAndMe.ff.width(1,scale);
		
		return (x > this.x - width/2 && x < this.x + width/2 && 
				y > this.y - height/2 && y < this.y + height/2);
	}
}
