package halfbyte.game.fallrpg.playing.entity;

import com.badlogic.gdx.math.Vector2;
import java.util.Set;
import halfbyte.game.fallrpg.Config;
import halfbyte.game.fallrpg.GameState;
import halfbyte.game.fallrpg.Utils;

public class Player extends Entity {
    // variables
    private Vector2 m_velocity;
    private float m_angularVelocity;


    // methods
    public Player(float positionX, float positionY, GameState.EntityState playerState, IListener listener) {
        // super
        super(positionX, positionY, 48, playerState, listener);

        // set initial velocity
        this.m_velocity = new Vector2(0, 0);
        this.m_angularVelocity = 0.0f;
    }

    public void update(float delta, Set<Mob> mobs){
        // check player to mob collision
        if (this.getAlive()) {
            for (Mob mob : mobs) {
                // get direction from player to mob
                Vector2 dir = Utils.direction(this.getX(), this.getY(), mob.getX(), mob.getY(), false);

                // get the distance
                float distance = dir.len();

                // check if collision
                if (distance <= this.getRadius() + mob.getRadius()) {
                    // instance velocity change on the player
                    this.m_velocity = dir.nor().scl(-Config.COLLISION_VELOCITY);

                    // angular
                    this.m_angularVelocity = Utils.randomFloat(-180, 180);

                    // damage me
                    this.damage(1);

                    // damage the mob
                    mob.damage(5);

                    // check if mob died
                    if (mob.getAlive() == false){
                        // gain some exp
                        this.m_entityState.gainExp(1);
                    }
                }
            }
        }

        // acceleration downwards, scaled by the delta
        Vector2 acceleration = new Vector2(0, Config.GRAVITY).scl(delta);

        // update the velocity by the acceleration
        this.m_velocity = this.m_velocity.add(acceleration);

        // cap the velocity
        if (this.m_velocity.len() > Config.VELOCITY_CAP){
            this.m_velocity = this.m_velocity.nor().scl(Config.VELOCITY_CAP);
        }

        // update position
        this.moveBy(this.m_velocity.x * delta, this.m_velocity.y * delta);

        // update rotation
        this.rotateBy(this.m_angularVelocity * delta);

        // check walls
        if (this.getX() - this.m_radius < 0){
            this.m_velocity.x = -this.m_velocity.x;
            this.setX(this.m_radius);
        }
        else if (this.getX() + this.m_radius >= Config.SCREEN_WIDTH){
            this.m_velocity.x = -this.m_velocity.x;
            this.setX(Config.SCREEN_WIDTH - this.m_radius);
        }
    }

    @Override
    public void kill() {
        super.kill();
    }
}
