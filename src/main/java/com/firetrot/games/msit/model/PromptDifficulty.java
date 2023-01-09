package com.firetrot.games.msit.model;

import lombok.Getter;

/**
 * Available difficulties.
 */
public enum PromptDifficulty {

    EASY(1),
    NORMAL(2),
    HARD(3);

    @Getter
    private final int value;

    PromptDifficulty(int value) {
        this.value = value;
    }

    public static PromptDifficulty of(int candidate) {
        for (PromptDifficulty value : values()) {
            if (value.value == candidate) {
                return value;
            }
        }
        return null;
    }
}
