package halfbyte.game.fallrpg.playing.entity;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import halfbyte.game.fallrpg.GameAssetManager;
import halfbyte.game.fallrpg.GameState;

public class Entity extends Group {
    public interface IListener{
        void onEntityDeath(Entity entity);
    }

    // variables
    protected float m_radius;
    protected GameState.EntityState m_entityState;
    private IListener m_listener;

    // methods
    public Entity(float positionX, float positionY, float radius, GameState.EntityState entityState, IListener listener){
        // parameters
        this.m_radius = radius;
        this.m_entityState = entityState;
        this.m_listener = listener;

        // position
        this.setPosition(positionX, positionY);

        // image
        Image image = new Image(GameAssetManager.getInstance().getTileset("actors_24x24.png", 24).getRegion(22));
        image.setSize(this.m_radius * 2, this.m_radius * 2);
        image.setPosition(-this.m_radius, -this.m_radius);
        this.addActor(image);
    }

    public float getRadius(){
        return this.m_radius;
    }

    public boolean getAlive(){
        // check state hp
        return this.m_entityState.getHpCurrent() > 0;
    }

    public void damage(long amount){
        // damage the state
        this.m_entityState.damage(amount);

        // check if dead
        if (this.getAlive() == false){
            this.kill();
        }
    }

    public void kill(){
        // ensure killed
        this.m_entityState.kill();

        // inform listener
        this.m_listener.onEntityDeath(this);
    }

    public long getLevel(){
        return this.m_entityState.getLevel();
    }

    public long getHpCurrent(){
        return this.m_entityState.getHpCurrent();
    }

    public long getHpMax(){
        return this.m_entityState.getHpMax();
    }

    public long getExpNext(){
        return this.m_entityState.getExpNext();
    }

    public long getExpCurrent(){
        return this.m_entityState.getExpCurrent();
    }
}
