package youandme.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import youandme.YouAndMe;

public class YouAndMeDesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Math.ceil(YouAndMe.WIDTH);
		config.height = (int) Math.ceil(YouAndMe.HEIGHT);
		config.title = YouAndMe.TITLE;
		//config.y = 0;
		new LwjglApplication(new YouAndMe(), config);
	}
}
