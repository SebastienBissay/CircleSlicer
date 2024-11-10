package parameters;

public record Color(float red, float green, float blue, float alpha) {
    public Color(float red, float green, float blue) {
        this(red, green, blue, 255);
    }

    public Color(float grayscale, float alpha) {
        this(grayscale, grayscale, grayscale, alpha);
    }

    public Color(float grayscale) {
        this(grayscale, 255);
    }

    public Color(String hexCode) {
        this(decode(hexCode));
    }

    public Color(Color color) {
        this(color.red, color.green, color.blue, color.alpha);
    }

    public static Color decode(String hexCode) {
        return switch (hexCode.length()) {
            case 2 -> new Color(Integer.valueOf(hexCode, 16));
            case 4 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                    Integer.valueOf(hexCode.substring(2, 4), 16));
            case 6 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                    Integer.valueOf(hexCode.substring(2, 4), 16),
                    Integer.valueOf(hexCode.substring(4, 6), 16));
            case 8 -> new Color(Integer.valueOf(hexCode.substring(0, 2), 16),
                    Integer.valueOf(hexCode.substring(2, 4), 16),
                    Integer.valueOf(hexCode.substring(4, 6), 16),
                    Integer.valueOf(hexCode.substring(6, 8), 16));
            default -> throw new IllegalArgumentException();
        };
    }
}