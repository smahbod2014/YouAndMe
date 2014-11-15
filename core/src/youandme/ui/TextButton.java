package youandme.ui;

import youandme.YouAndMe;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class TextButton {

	public float x;
	public float y;
	public Sprite[] text;
	
	public TextButton(float x, float y, Sprite[] text) {
		this.x = x;
		this.y = y;
		this.text = text;
	}
	
	public boolean contains(float x, float y){
		float width = YouAndMe.ff.width(text.length, 1);
		float height = YouAndMe.ff.width(1,1);
		
		return (x > this.x - width/2 && x < this.x + width/2 && 
				y > this.y - height/2 && y < this.y + height/2);
	}
}
