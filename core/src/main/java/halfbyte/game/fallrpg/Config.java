package halfbyte.game.fallrpg;

public class Config {
    // generic
    public static final int SCREEN_WIDTH = 720;
    public static final int SCREEN_HEIGHT = 1280;
    public static final float DEPTH_WRAP = -5000;

    public static final float GRAVITY = -256;
    public static final float VELOCITY_CAP = 1024;
    public static final float COLLISION_VELOCITY = 512;

    public static final float PIXEL_TO_METER_FACTOR = 2.0f / 48;

    public static final float SCREEN_TRANSITION_FADE_DURATION = 0.25f;

    public static long computeNextExp(long level){
        return (long) (10 * Math.pow(1.15, level - 1));
    }
}
