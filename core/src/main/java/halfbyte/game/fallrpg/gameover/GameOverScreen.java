package halfbyte.game.fallrpg.gameover;

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
import halfbyte.game.fallrpg.mainmenu.MainMenuScreen;
import halfbyte.game.fallrpg.playing.PlayingScreen;
import halfbyte.game.fallrpg.stats.StatsScreen;
import halfbyte.game.fallrpg.upgrades.UpgradesScreen;

public class GameOverScreen extends AbstractScreen {
    // variables


    // methods
    public GameOverScreen(IGameServices game_services, long currencyEarned) {
        super(game_services);

        // table
        Table table = new Table();
        table.pad(8);
        table.defaults().space(8);
        table.setFillParent(true);
        this.m_stage.addActor(table);

        // game over label
        Label label = new Label("Game Over", GameAssetManager.getInstance().getLabelStyle(GameAssetManager.ETextSize.LARGEST));
        label.setAlignment(Align.center);
        table.add(label).expandX().expandY().fill();

        // currency earned
        table.row();
        label = new Label("Currency Earned: " + currencyEarned, GameAssetManager.getInstance().getLabelStyle(GameAssetManager.ETextSize.LARGER));
        label.setAlignment(Align.center);
        table.add(label).expandX().expandY().fill();

        // restart
        table.row();
        Button button = new TextButton("Restart", GameAssetManager.getInstance().getTextButtonStyle(GameAssetManager.ETextSize.LARGER));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameOverScreen.this.onButtonRestart();
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
                GameOverScreen.this.onButtonUpgrades();
            }
        });
        table.add(button).expandX().height(128).fill();

        // stats menu
        table.row();
        button = new TextButton("Stats", GameAssetManager.getInstance().getTextButtonStyle(GameAssetManager.ETextSize.LARGER));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameOverScreen.this.onButtonStats();
            }
        });
        table.add(button).expandX().height(128).fill();

        // main menu
        table.row();
        button = new TextButton("Quit", GameAssetManager.getInstance().getTextButtonStyle(GameAssetManager.ETextSize.LARGER));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameOverScreen.this.onButtonQuit();
            }
        });
        table.add(button).expandX().height(128).fill();

        // bottom space
        table.row();
        table.add(new Actor()).expandX().expandY().fill();

        // update the currency
        this.m_gameServices.getGameState().getPlayerState().modifyCurrency(currencyEarned);
    }

    private void onButtonRestart(){
        this.m_gameServices.setNextScreen(new PlayingScreen(this.m_gameServices));
    }

    private void onButtonUpgrades(){
        this.m_gameServices.setNextScreen(new UpgradesScreen(this.m_gameServices));
    }

    private void onButtonStats(){
        this.m_gameServices.setNextScreen(new StatsScreen(this.m_gameServices));
    }

    private void onButtonQuit(){
        this.m_gameServices.setNextScreen(new MainMenuScreen(this.m_gameServices));
    }
}
