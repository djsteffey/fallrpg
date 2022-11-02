package halfbyte.game.fallrpg.playing;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import halfbyte.game.fallrpg.Config;

public class EntityStage extends Stage {
    // variables


    // methods
    public EntityStage(){
        super(new FitViewport(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
    }
}
