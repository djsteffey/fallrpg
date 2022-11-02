package halfbyte.game.fallrpg.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import halfbyte.game.fallrpg.AbstractScreen;
import halfbyte.game.fallrpg.GameAssetManager;
import halfbyte.game.fallrpg.IGameServices;
import halfbyte.game.fallrpg.credits.CreditsScreen;
import halfbyte.game.fallrpg.playing.PlayingScreen;
import halfbyte.game.fallrpg.stats.StatsScreen;
import halfbyte.game.fallrpg.upgrades.UpgradesScreen;

public class MainMenuScreen extends AbstractScreen {
    // variables


    // methods
    public MainMenuScreen(IGameServices game_services) {
        super(game_services);

        // table
        Table table = new Table();
        table.pad(8);
        table.defaults().space(8);
        table.setFillParent(true);
        this.m_stage.addActor(table);

        // title label
        Label label = new Label("Fall RPG", GameAssetManager.getInstance().getLabelStyle(GameAssetManager.ETextSize.LARGEST));
        label.setAlignment(Align.center);
        table.add(label).expandX().expandY().fill();

        // play
        table.row();
        Button button = new TextButton("Play", GameAssetManager.getInstance().getTextButtonStyle(GameAssetManager.ETextSize.LARGER));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MainMenuScreen.this.onButtonPlay();
            }
        });
        table.add(button).expandX().height(128).fill();

        // upgrades
        table.row();
        button = new TextButton("Upgrades", GameAssetManager.getInstance().getTextButtonStyle(GameAssetManager.ETextSize.LARGER));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MainMenuScreen.this.onButtonUpgrades();
            }
        });
        table.add(button).expandX().height(128).fill();

        // stats
        table.row();
        button = new TextButton("Stats", GameAssetManager.getInstance().getTextButtonStyle(GameAssetManager.ETextSize.LARGER));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MainMenuScreen.this.onButtonStats();
            }
        });
        table.add(button).expandX().height(128).fill();

        // credits
        table.row();
        button = new TextButton("Credits", GameAssetManager.getInstance().getTextButtonStyle(GameAssetManager.ETextSize.LARGER));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MainMenuScreen.this.onButtonCredits();
            }
        });
        table.add(button).expandX().height(128).fill();

        // quit
        table.row();
        button = new TextButton("Quit", GameAssetManager.getInstance().getTextButtonStyle(GameAssetManager.ETextSize.LARGER));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                MainMenuScreen.this.onButtonQuit();
            }
        });
        table.add(button).expandX().height(128).fill();

        // bottom space
        table.row();
        table.add(new Actor()).expandX().expandY().fill();
    }

    private void onButtonPlay(){
        this.m_gameServices.setNextScreen(new PlayingScreen(this.m_gameServices));
    }

    private void onButtonUpgrades(){
        this.m_gameServices.setNextScreen(new UpgradesScreen(this.m_gameServices));
    }

    private void onButtonStats(){
        this.m_gameServices.setNextScreen(new StatsScreen(this.m_gameServices));
    }

    private void onButtonCredits(){
        this.m_gameServices.setNextScreen(new CreditsScreen(this.m_gameServices));
    }

    private void onButtonQuit(){
        Gdx.app.exit();
    }
}
