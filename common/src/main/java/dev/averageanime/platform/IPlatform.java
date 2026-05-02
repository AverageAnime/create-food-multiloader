package dev.averageanime.platform;

public interface IPlatform {

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    boolean isClient();

    boolean isServer();


    boolean isFabric();

    boolean isNeoforge();

    String getCategoryEffectOverride(String categoryName);
}