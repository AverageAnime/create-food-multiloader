package dev.averageanime.tools;

import net.minecraft.SharedConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Checks that every vanilla fallback names an effect that exists -- constant names are not registry ids ({@code DIG_SPEED} registers as {@code haste}). */
public final class VerifyFallbacks {

    private VerifyFallbacks() {}

    public static void main(String[] args) throws IOException {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        Path source = Path.of("common/src/main/java/dev/averageanime/item/effect/EffectCategories.java");
        String text = Files.readString(source, StandardCharsets.UTF_8);
        Matcher matcher = Pattern.compile("\\.or\\(\"minecraft\", \"(minecraft:[a-z_]+)\"\\)").matcher(text);

        List<String> bad = new ArrayList<>();
        int total = 0;
        while (matcher.find()) {
            total++;
            String id = matcher.group(1);
            if (BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(id)).isEmpty()) {
                bad.add(id);
            }
        }
        System.out.println("vanilla fallbacks declared: " + total);
        if (bad.isEmpty()) {
            System.out.println("all resolve against the mob effect registry");
        } else {
            System.out.println("UNRESOLVABLE: " + bad);
            System.exit(1);
        }
    }
}
