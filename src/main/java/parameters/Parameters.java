package parameters;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Parameters {
    public static final long SEED = 11;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    public static final int CIRCLE_POINTS = 100;
    public static final int NUMBER_OF_SLICES = 100;
    public static final float CIRCLE_RADIUS = 300;
    public static final float GAUSSIAN_MULTIPLIER = .5f;
    public static final float OFFSET_LOW = .5f;
    public static final float OFFSET_HIGH = 2;
    public static final Color BACKGROUND_COLOR = new Color("f5f1e3");
    public static final Color POLYGON_COLOR = new Color("463f3a");
    public static final float POLYGON_STROKEWEIGHT = 1;
    public static final boolean POLYGON_NO_FILL = true;
    public static final boolean POLYGON_NO_STROKE = false;

    /**
     * Helper method to extract the constants in order to save them to a json file
     *
     * @return a Map of the constants (name -> value)
     */
    public static Map<String, ?> toJsonMap() throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = Parameters.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(Parameters.class));
        }

        return Collections.singletonMap(Parameters.class.getSimpleName(), map);
    }
}
