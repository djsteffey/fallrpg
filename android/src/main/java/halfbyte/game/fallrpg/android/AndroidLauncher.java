package halfbyte.game.fallrpg.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import halfbyte.game.fallrpg.FallGame;
import halfbyte.game.fallrpg.IPlatformServices;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication implements IPlatformServices {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
		initialize(new FallGame(this), configuration);
	}
}