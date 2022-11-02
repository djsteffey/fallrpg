package halfbyte.game.fallrpg.credits;

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

public class CreditsScreen extends AbstractScreen {
    // variables


    // methods
    public CreditsScreen(IGameServices game_services) {
        super(game_services);

        // table
        Table table = new Table();
        table.pad(8);
        table.defaults().space(8);
        table.setFillParent(true);
        this.m_stage.addActor(table);

        // title label
        Label label = new Label("Credits", GameAssetManager.getInstance().getLabelStyle(GameAssetManager.ETextSize.LARGEST));
        label.setAlignment(Align.center);
        table.add(label).expandX().expandY().fill();

        // back
        table.row();
        Button button = new TextButton("Back", GameAssetManager.getInstance().getTextButtonStyle(GameAssetManager.ETextSize.LARGER));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                CreditsScreen.this.onButtonBack();
            }
        });
        table.add(button).expandX().height(128).fill();

        // bottom space
        table.row();
        table.add(new Actor()).expandX().expandY().fill();
    }

    private void onButtonBack(){
        this.m_gameServices.setNextScreen(new MainMenuScreen(this.m_gameServices));
    }
}
