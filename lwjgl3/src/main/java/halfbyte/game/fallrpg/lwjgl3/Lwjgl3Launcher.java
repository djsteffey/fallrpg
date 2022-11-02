package halfbyte.game.fallrpg.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import halfbyte.game.fallrpg.FallGame;
import halfbyte.game.fallrpg.IPlatformServices;

public class Lwjgl3Launcher implements IPlatformServices {
	public static void main(String[] args) {
		// config
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("Fall RPG");
		configuration.setWindowedMode(720 / 2, 1280 / 2);
		configuration.setResizable(false);
		configuration.setWindowPosition(800, 200);
		configuration.useVsync(true);
		configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

		// create game
		FallGame game = new FallGame(new Lwjgl3Launcher());

		// start
		new Lwjgl3Application(game, configuration);
	}
}