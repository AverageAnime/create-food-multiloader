package dev.averageanime.util;

public final class Tooltips {

    public static final String TIP_PREFIX    = "tooltip.createfood.";
    public static final String COMPAT_PREFIX = "tooltip.compat.";

    private Tooltips() {}

    public static TooltipSpec tips(String compat, String... shortKeys) {
        String[] full = new String[shortKeys.length];
        for (int i = 0; i < shortKeys.length; i++) {
            full[i] = TIP_PREFIX + shortKeys[i];
        }
        return new TooltipSpec(compat != null ? COMPAT_PREFIX + compat : null, full);
    }

    public record TooltipSpec(String compat, String[] keys) {}
}
