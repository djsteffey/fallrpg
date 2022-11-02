package halfbyte.game.fallrpg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UiTabs extends Group {
    public interface IListener{
        void onTabsTabClicked(UiTabs tabs, String tabName);
    }

    // variables
    private boolean m_horizontal;
    private Table m_table;
    private ClickListener m_clickListener;
    private IListener m_listener;

    // methods
    public UiTabs(boolean horizontal, IListener listener){
        // parameters
        this.m_horizontal = horizontal;
        this.m_listener = listener;

        // table
        this.m_table = new TableWithBackground(Color.WHITE);
        this.m_table.setFillParent(true);
        this.addActor(this.m_table);

        // click listener
        this.m_clickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                UiTabs.this.onTabClicked((TextButton) event.getListenerActor());
            }
        };
    }

    public void addTextTab(String text){
        if (this.m_horizontal == false){
            this.m_table.row();
        }

        TextButton button = new TextButton(text, GameAssetManager.getInstance().getTextButtonStyle(GameAssetManager.ETextSize.STANDARD));
        button.addListener(this.m_clickListener);
        this.m_table.add(button).expandX().height(48).fill();
    }

    public void addImageTab(String image){
        if (this.m_horizontal == false){
            this.m_table.row();
        }

        Button button = new Button(GameAssetManager.getInstance().getSkin());
        button.add(new Image(GameAssetManager.getInstance().getTexture(image)));
        button.addListener(this.m_clickListener);
        this.m_table.add(button).expandX().height(48).fill();
    }

    private void onTabClicked(TextButton textButton){
        this.m_listener.onTabsTabClicked(this, textButton.getText().toString());
    }
}
