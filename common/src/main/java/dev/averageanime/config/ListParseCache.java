package dev.averageanime.config;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

final class ListParseCache<V> {

    private final Function<List<? extends String>, V> parser;
    private final ConcurrentHashMap<List<String>, V> cache = new ConcurrentHashMap<>();

    ListParseCache(Function<List<? extends String>, V> parser) {
        this.parser = parser;
    }

    V get(List<? extends String> source) {
        if (cache.size() > 64) cache.clear();
        List<String> snapshot = List.copyOf(source);
        return cache.computeIfAbsent(snapshot, key -> parser.apply(source));
    }
}
