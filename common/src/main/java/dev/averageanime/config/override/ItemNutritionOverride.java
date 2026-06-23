package dev.averageanime.config.override;

public record ItemNutritionOverride(int nutrition, float saturation) {
    public static final int   KEEP_INT   = -1;
    public static final float KEEP_FLOAT = -1f;

    public boolean hasNutritionOverride()  { return nutrition  != KEEP_INT;   }
    public boolean hasSaturationOverride() { return saturation != KEEP_FLOAT; }
}