package com.firetrot.games.msit.model;

/**
 * Available actions.
 */
public enum PromptAction {

    OPEN("o"),
    FLAG("f"),
    QUIT("q");

    private final String value;

    PromptAction(String value) {
        this.value = value;
    }

    public static PromptAction of(String candidate) {
        for (PromptAction value : values()) {
            if (value.value.equals(candidate)) {
                return value;
            }
        }
        return null;
    }
}
