package halfbyte.game.fallrpg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    // random
    private static final Random s_random = new Random();
    public static int randomInt(int min, int max){
        return Utils.s_random.nextInt(max - min) + min;
    }
    public static long randomLong(long min, long max){
        return (long)(Math.random() * (max - min)) + min;
    }
    public static float randomFloat(float min, float max){
        return Utils.s_random.nextFloat() * (max - min) + min;
    }
    public static double randomDouble(){
        return Utils.s_random.nextDouble();
    }
    public static boolean randomBool(double chance){
        return randomDouble() <= chance;
    }
    public static boolean randomBool(float chance){
        return randomFloat(0.0f, 1.0f) <= chance;
    }
    public static boolean randomBool(){
        return randomBool(0.50f);
    }
    public static Color randomColor(){
        return new Color(Utils.randomFloat(0, 1), Utils.randomFloat(0, 1), Utils.randomFloat(0, 1), 1);
    }
    public static Vector2 randomVector2(float min, float max){
        float angle = Utils.randomFloat(0, (float)(2 * Math.PI));
        float mag = Utils.randomFloat(min, max);
        return new Vector2((float)(Math.sin(angle) * mag), (float)(Math.cos(angle) * mag));
    }

    // directions
    public static final List<GridPoint2> CARDINAL_DIRECTIONS = new ArrayList<GridPoint2>(){{
        add(new GridPoint2(0, -1));     // up
        add(new GridPoint2(1, 0));      // right
        add(new GridPoint2(0, 1));      // down
        add(new GridPoint2(-1, 0));     // left
    }};
    public static final List<GridPoint2> DIAGONAL_DIRECTIONS = new ArrayList<GridPoint2>(){{
        add(new GridPoint2(1, -1));     // up/right
        add(new GridPoint2(1, 1));      // down/right
        add(new GridPoint2(-1, 1));     // down/left
        add(new GridPoint2(-1, -1));    // up/eft
    }};
    public static final List<GridPoint2> ALL_DIRECTIONS = new ArrayList<GridPoint2>(){{
        addAll(CARDINAL_DIRECTIONS);
        addAll(DIAGONAL_DIRECTIONS);
    }};
    public enum EDirection{
        NONE, UP, RIGHT, DOWN, LEFT;
        public GridPoint2 toGridPoint2(){
            switch (this){
                case UP: return new GridPoint2(0, -1);
                case DOWN: return new GridPoint2(0, 1);
                case LEFT: return new GridPoint2(-1, 0);
                case RIGHT: return new GridPoint2(1, 0);
            }
            return new GridPoint2(0, 0);
        }
        public EDirection next(){
            // next ordinal not using 0 (NONE)
            int ordinal = Math.max(1, (this.ordinal() + 1) % EDirection.values().length);
            return EDirection.values()[ordinal];
        }
    }

    // ids
    private static long s_nextId = 0;
    public static long generateId(){
        return Utils.s_nextId++;
    }

    // color
    public static Color colorMultiply(Color color, float amount){
        color.r *= amount;
        color.g *= amount;
        color.b *= amount;
        return color;
    }
    public static Color colorAlpha(Color color, float alpha){
        color.a = alpha;
        return color;
    }
    public static boolean colorCompare(Color a, Color b){
        if (a.r == b.r && a.g == b.g && a.b == b.b && a.a == b.a){
            return true;
        }
        return false;
    }

    // formatting
    private static NumberFormat s_format = new DecimalFormat("0.00E0");
    public static String numberToString(long number){
        if (number < 1000){
            return "" + number;
        }
        else {
            return Utils.s_format.format(number);
        }
    }

    // math
    public static Vector2 direction(float x1, float y1, float x2, float y2, boolean normalize){
        Vector2 dir = new Vector2(x2 - x1, y2 - y1);
        if (normalize){
            dir = dir.nor();
        }
        return dir;
    }

    // misc
    public static void enableButton(Button button, boolean enable){
        button.setDisabled(!enable);
        button.setTouchable(enable ? Touchable.enabled : Touchable.disabled);
    }
}
