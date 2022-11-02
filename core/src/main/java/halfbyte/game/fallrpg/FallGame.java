package halfbyte.game.fallrpg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import halfbyte.game.fallrpg.mainmenu.MainMenuScreen;

public class FallGame extends Game implements IGameServices {
	// variables
	private final IPlatformServices m_platformServices;
	private AbstractScreen m_currentScreen;
	private AbstractScreen m_nextScreen;
	private Actor m_screenTransitionActor;
	private GameState m_gameState;

	// methods
	public FallGame(IPlatformServices platform_services){
		// init variables
		this.m_platformServices = platform_services;
		this.m_currentScreen = null;
		this.m_nextScreen = null;
		this.m_screenTransitionActor = null;
		this.m_gameState = null;
	}

	@Override
	public void create() {
		// load assets
		GameAssetManager.getInstance().loadAssets();

		// screen transitions
		this.m_screenTransitionActor = new Actor();

		// state
		this.m_gameState = new GameState();

		// set first screen
		this.setNextScreen(new MainMenuScreen(
				this
		));
	}

	@Override
	public void dispose() {
		// dispose current screen
		if (this.m_currentScreen != null){
			this.m_currentScreen.dispose();
		}

		// dispose next screen
		if (this.m_nextScreen != null){
			this.m_nextScreen.dispose();
		}

		// dispose assets
		GameAssetManager.getInstance().dispose();

		// super
		super.dispose();
	}

	@Override
	public void render() {
		// handle screen transitions
		this.m_screenTransitionActor.act(Gdx.graphics.getDeltaTime());

		// super duper
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	// IGameServices
	@Override
	public IPlatformServices getPlatformServices(){
		return this.m_platformServices;
	}

	@Override
	public void setNextScreen(AbstractScreen screen) {
		// fade out current; starts immediately
		if (this.m_currentScreen != null){
			this.m_currentScreen.fadeOut();
		}

		// set next screen and fade in; fade in only starts after next becomes current due to the act method
		this.m_nextScreen = screen;
		if (this.m_nextScreen != null){
			this.m_nextScreen.fadeIn();
		}

		// transition
		this.m_screenTransitionActor.addAction(Actions.sequence(
				// delay while current fades out
				Actions.delay(Config.SCREEN_TRANSITION_FADE_DURATION),
				// make next current
				new Action() {
					@Override
					public boolean act(float delta) {
						if (FallGame.this.m_currentScreen != null) {
							FallGame.this.m_currentScreen.fadeCleanup();
						}
						FallGame.this.m_currentScreen = FallGame.this.m_nextScreen;
						FallGame.this.setScreen(FallGame.this.m_currentScreen);
						FallGame.this.m_nextScreen = null;
						return true;
					}
				},
				// delay while fade in
				Actions.delay(Config.SCREEN_TRANSITION_FADE_DURATION),
				// cleanup
				new Action() {
					@Override
					public boolean act(float delta) {
						if (FallGame.this.m_currentScreen != null){
							FallGame.this.m_currentScreen.fadeCleanup();
						}
						return true;
					}
				}
		));
	}

	@Override
	public GameState getGameState() {
		return this.m_gameState;
	}
}