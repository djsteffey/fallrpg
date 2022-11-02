package halfbyte.game.fallrpg;

public interface IGameServices {
    IPlatformServices getPlatformServices();
    void setNextScreen(AbstractScreen screen);
    GameState getGameState();
}
