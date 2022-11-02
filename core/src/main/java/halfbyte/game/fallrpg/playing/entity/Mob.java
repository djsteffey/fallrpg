package halfbyte.game.fallrpg.playing.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import halfbyte.game.fallrpg.GameAssetManager;
import halfbyte.game.fallrpg.GameState;
import halfbyte.game.fallrpg.UiProgressBar;

public class Mob extends Entity{
    // variables
    private UiProgressBar m_hpBar;

    // methods
    public Mob(float positionX, float positionY, GameState.EntityState entityState, IListener listener) {
        super(positionX, positionY, 48, entityState, listener);

        // modify hp
        this.m_entityState.setHpCurrent(6);

        // make hp bar
        this.m_hpBar = new UiProgressBar(this.m_entityState.getHpCurrent(), this.m_entityState.getHpMax());
        this.m_hpBar.setSize(64, 12);
        this.m_hpBar.setColors(Color.WHITE, Color.BLACK, Color.RED);
        this.m_hpBar.setPosition(0, -this.m_radius - 12, Align.center);
    }

    @Override
    public void damage(long amount) {
        super.damage(amount);
        if (this.getAlive()) {
            // update hp bar value
            this.m_hpBar.setValue(this.m_entityState.getHpCurrent());

            // ensure it is shown
            this.addActor(this.m_hpBar);

            // clear existing actions
            this.m_hpBar.clearActions();

            // ensure full color
            this.m_hpBar.setColor(Color.WHITE);

            // add delay and fade
            this.m_hpBar.addAction(Actions.sequence(
                    Actions.delay(2.5f),
                    Actions.fadeOut(2.5f),
                    Actions.removeActor()
            ));
        }
        else{
            // dead so make sure hp bar is gone
            this.m_hpBar.remove();
        }
    }

    @Override
    public void kill(){
        // super
        super.kill();

        // remove existing image
        for (Actor child : this.getChildren()){
            child.remove();
        }

        // add skull
        Image image = new Image(GameAssetManager.getInstance().getTexture("oga/ARoachIFoundOnMyPillow/bone_skull.png"));
        image.setSize(this.m_radius * 1.5f, this.m_radius * 1.5f);
        image.setOrigin(image.getWidth() * 0.50f, image.getHeight() * 0.50f);
        image.setPosition(-image.getWidth() * 0.50f, -image.getHeight() * 0.50f);
        image.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.moveBy(0, -500, 2.50f),
                        Actions.rotateBy(-360 * 2, 2.50f),
                        Actions.fadeOut(2.50f)
                ),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        Mob.this.remove();
                        return true;
                    }
                }
        ));
        this.addActor(image);
    }
}
