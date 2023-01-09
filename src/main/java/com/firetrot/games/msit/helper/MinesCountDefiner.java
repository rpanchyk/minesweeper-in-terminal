package com.firetrot.games.msit.helper;

import com.firetrot.games.msit.model.PromptDifficulty;

import java.util.Random;

public class MinesCountDefiner {

    public static int define(int boardSize, PromptDifficulty difficulty) {
        final int min = minMinesCount(boardSize, difficulty);
        final int max = maxMinesCount(boardSize, difficulty);
        return new Random().nextInt(min, max + 1);
    }

    private static int minMinesCount(int boardSize, PromptDifficulty difficulty) {
        final int ordinal = difficulty.ordinal();
        return ordinal > 0
            ? proportionOf(boardSize, PromptDifficulty.values()[ordinal - 1])
            : 1;
    }

    private static int maxMinesCount(int boardSize, PromptDifficulty difficulty) {
        return proportionOf(boardSize, difficulty);
    }

    private static int proportionOf(int boardSize, PromptDifficulty difficulty) {
        return boardSize * boardSize * percentageOf(difficulty) / 100;
    }

    private static int percentageOf(PromptDifficulty difficulty) {
        return switch (difficulty) {
            case EASY -> 10;
            case NORMAL -> 20;
            case HARD -> 30;
        };
    }
}
