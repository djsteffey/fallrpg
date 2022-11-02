package halfbyte.game.fallrpg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class Header extends Group {
    // variables
    private GameState.EntityState m_playerState;
    private Label m_currencyLabel;

    // methods
    public Header(GameState.EntityState playerState){
        // parameters
        this.m_playerState = playerState;

        // size
        this.setSize(Config.SCREEN_WIDTH, 64);

        // table
        Table table = new TableWithBackground(Color.WHITE);
        table.pad(4);
        table.defaults().space(8);
        table.setFillParent(true);
        this.addActor(table);

        // currency
        this.m_currencyLabel = new Label("C: 0", GameAssetManager.getInstance().getLabelStyle(GameAssetManager.ETextSize.LARGER));
        this.m_currencyLabel.setAlignment(Align.center);
        table.add(this.m_currencyLabel).expandX().expandY().fill();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        this.m_currencyLabel.setText("C: " + this.m_playerState.getCurrency());
        super.draw(batch, parentAlpha);
    }
}
