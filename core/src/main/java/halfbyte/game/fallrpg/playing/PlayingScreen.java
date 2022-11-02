package halfbyte.game.fallrpg.playing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import java.util.HashSet;
import java.util.Set;
import halfbyte.game.fallrpg.AbstractScreen;
import halfbyte.game.fallrpg.Config;
import halfbyte.game.fallrpg.GameAssetManager;
import halfbyte.game.fallrpg.GameState;
import halfbyte.game.fallrpg.IGameServices;
import halfbyte.game.fallrpg.UiTextProgressBar;
import halfbyte.game.fallrpg.Utils;
import halfbyte.game.fallrpg.gameover.GameOverScreen;
import halfbyte.game.fallrpg.playing.entity.Entity;
import halfbyte.game.fallrpg.playing.entity.Mob;
import halfbyte.game.fallrpg.playing.entity.Player;

public class PlayingScreen extends AbstractScreen {
    // variables
    private EntityStage m_entityStage;
    private Player m_player;
    private Set<Mob> m_mobs;
    private long m_depthResets;
    private UiTextProgressBar m_hpBar;
    private Label m_levelLabel;
    private UiTextProgressBar m_expBar;
    private Label m_depthLabel;
    private Entity.IListener m_entityListener;
    private long m_currencyEarned;

    // methods
    public PlayingScreen(IGameServices gs) {
        // super
        super(gs);

        // entity stage
        this.m_entityStage = new EntityStage();

        // entity listener
        this.m_entityListener = new Entity.IListener() {
            @Override
            public void onEntityDeath(Entity entity) {
                PlayingScreen.this.onEntityDeath(entity);
            }
        };

        // player
        this.m_gameServices.getGameState().getPlayerState().resetHp();
        this.m_player = new Player(
                Config.SCREEN_WIDTH / 2.0f,
                Config.SCREEN_HEIGHT - 48,
                this.m_gameServices.getGameState().getPlayerState(),
                this.m_entityListener
        );
        this.m_entityStage.addActor(this.m_player);

        // bunch of random mobs
        this.m_mobs = new HashSet<>();
        for (int i = 0; i < 100; ++i) {
            Mob mob = new Mob(
                    Utils.randomFloat(8, Config.SCREEN_WIDTH - 8),
                    Utils.randomFloat(Config.DEPTH_WRAP * 2, 0),
                    new GameState.EntityState(1, 6),
                    this.m_entityListener
            );
            this.m_mobs.add(mob);
            this.m_entityStage.addActor(mob);
        }

        // ui
        this.buildUi();

        // depth
        this.m_depthResets = 0;

        // currency
        this.m_currencyEarned = 0;
    }

    @Override
    public void render(float delta) {
        // act entity stage
        this.m_entityStage.act(delta);

        // update the player
        this.m_player.update(delta * 1.0f, this.m_mobs);

        // remove mobs
        this.m_mobs.removeIf((mob)->{
            // check if dead
            if (mob.getAlive() == false){
                return true;
            }

            // check offscreen above
            if (mob.getY() > this.m_player.getY() + 2000 && this.m_player.getAlive()){
                mob.remove();
                return true;
            }

            // alive
            return false;
        });

        // check depth wrap
        if (this.m_player.getY() < Config.DEPTH_WRAP && this.m_player.getAlive()){
            // another wrap
            this.m_depthResets += 1;

            // move player
            this.m_player.moveBy(0, -Config.DEPTH_WRAP);

            // move mobs
            for (Mob mob : this.m_mobs){
                mob.moveBy(0, -Config.DEPTH_WRAP);
            }

            // add more mobs
            for (int i = 0; i < 50; ++i) {
                Mob mob = new Mob(
                        Utils.randomFloat(8, Config.SCREEN_WIDTH - 8),
                        Utils.randomFloat(Config.DEPTH_WRAP * 2, Config.DEPTH_WRAP),
                        new GameState.EntityState(1, 6),
                        this.m_entityListener
                );
                this.m_mobs.add(mob);
                this.m_entityStage.addActor(mob);
            }
        }

        // update depth record
        if (this.m_player.getAlive()){
            this.m_gameServices.getGameState().getPlayerState().updateRecordDepth(this.computeTrueDepth());
        }

        // ui update
        this.updateUi();

        // move the camera to center the player
        if (this.m_player.getAlive()) {
            this.m_entityStage.getCamera().position.set(Config.SCREEN_WIDTH / 2.0f, this.m_player.getY(), 0);
        }

        // draw the entities
        this.m_entityStage.draw();

        // super render
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        this.m_entityStage.getViewport().update(width, height);
    }

    private void buildUi(){
        // hp bar
        this.m_hpBar = new UiTextProgressBar(75, 100, "", GameAssetManager.ETextSize.LARGER, true);
        this.m_hpBar.setColors(Color.WHITE, Color.BLACK, Color.RED);
        this.m_hpBar.setSize(Config.SCREEN_WIDTH * 0.75f, 48);
        this.m_hpBar.setPosition(
                (Config.SCREEN_WIDTH - this.m_hpBar.getWidth()) / 2,
                Config.SCREEN_HEIGHT - this.m_header.getHeight() - 4 - this.m_hpBar.getHeight()
        );
        this.m_stage.addActor(this.m_hpBar);

        // level
        this.m_levelLabel = new Label("Lvl 1", GameAssetManager.getInstance().getLabelStyle(GameAssetManager.ETextSize.LARGER));
        this.m_levelLabel.setSize(128, this.m_hpBar.getHeight());
        this.m_levelLabel.setAlignment(Align.left);
        this.m_levelLabel.setPosition(this.m_hpBar.getX(), this.m_hpBar.getY(Align.bottom) - 4 - this.m_levelLabel.getHeight());
        this.m_stage.addActor(this.m_levelLabel);

        // exp bar
        this.m_expBar = new UiTextProgressBar(75, 100, "", GameAssetManager.ETextSize.LARGER, true);
        this.m_expBar.setColors(Color.WHITE, Color.BLACK, Color.TAN);
        this.m_expBar.setSize(this.m_hpBar.getWidth() - this.m_levelLabel.getWidth(), this.m_hpBar.getHeight());
        this.m_expBar.setPosition(this.m_levelLabel.getX(Align.right), this.m_hpBar.getY(Align.bottom) - 4 - this.m_expBar.getHeight());
        this.m_stage.addActor(this.m_expBar);

        // depth
        this.m_depthLabel = new Label("Depth: 0", GameAssetManager.getInstance().getLabelStyle(GameAssetManager.ETextSize.LARGER));
        this.m_depthLabel.setAlignment(Align.center);
        this.m_depthLabel.setSize(this.m_hpBar.getWidth(), this.m_hpBar.getHeight());
        this.m_depthLabel.setPosition(this.m_hpBar.getX(), this.m_expBar.getY(Align.bottom) - 4 - this.m_depthLabel.getHeight());
        this.m_stage.addActor(this.m_depthLabel);
    }

    private void updateUi(){
        this.m_hpBar.setValue(this.m_player.getHpCurrent(), this.m_player.getHpMax());
        this.m_levelLabel.setText("Lvl " + this.m_player.getLevel());
        this.m_expBar.setValue(this.m_player.getExpCurrent(), this.m_player.getExpNext());
        this.m_depthLabel.setText("Depth: " + this.computeTrueDepth() + "m");
    }

    private long computeTrueDepth(){
        return (long) (((this.m_depthResets * Config.DEPTH_WRAP) + this.m_player.getY()) * Config.PIXEL_TO_METER_FACTOR);
    }

    private void onEntityDeath(Entity entity){
        if (entity == this.m_player){
            this.m_stage.addAction(Actions.sequence(
                    Actions.delay(2.0f),
                    new Action() {
                        @Override
                        public boolean act(float delta) {
                            PlayingScreen.this.m_gameServices.setNextScreen(new GameOverScreen(PlayingScreen.this.m_gameServices, PlayingScreen.this.computeCurrencyEarned()));
                            return true;
                        }
                    }
            ));
        }
    }

    private long computeCurrencyEarned(){
        long depth = this.computeTrueDepth();
        return -depth;
    }
}
