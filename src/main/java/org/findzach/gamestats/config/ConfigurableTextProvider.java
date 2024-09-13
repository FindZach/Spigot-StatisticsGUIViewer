package org.findzach.gamestats.config;

public interface ConfigurableTextProvider {
    String getMessage(String path);
    String getMessage(String path, String placeholder, String replacement);
}
