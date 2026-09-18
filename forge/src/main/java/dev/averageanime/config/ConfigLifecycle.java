package dev.averageanime.config;

public final class ConfigLifecycle {

    private static final Object LOCK = new Object();

    private ConfigLifecycle() {}

    public static void onServerConfigLoaded() {
        synchronized (LOCK) {
            RemainderPatcher.apply();
            ForeignFoodPatcher.apply();
        }
    }

    public static void onServerConfigUnloaded() {
        synchronized (LOCK) {
            ForeignFoodPatcher.restore();
            RemainderPatcher.restore();
        }
    }
}
